package lk.ijse.aad_project.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        /*
         * ---------------------------------------------------------
         * CORS PRE-FLIGHT REQUEST
         * ---------------------------------------------------------
         *
         * Browser sends an OPTIONS request before PUT/POST/DELETE
         * requests when CORS preflight is required.
         *
         * We don't need JWT authentication for OPTIONS requests.
         */
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {

            filterChain.doFilter(request, response);
            return;
        }

        /*
         * ---------------------------------------------------------
         * GET AUTHORIZATION HEADER
         * ---------------------------------------------------------
         */

        String authHeader = request.getHeader("Authorization");

        /*
         * If there is no Authorization header,
         * simply continue the filter chain.
         */
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        try {

            /*
             * -----------------------------------------------------
             * EXTRACT JWT TOKEN
             * -----------------------------------------------------
             */

            String token = authHeader.substring(7);

            /*
             * -----------------------------------------------------
             * EXTRACT USERNAME FROM TOKEN
             * -----------------------------------------------------
             */

            String username = jwtUtil.extractUsername(token);

            /*
             * -----------------------------------------------------
             * CHECK USERNAME AND SECURITY CONTEXT
             * -----------------------------------------------------
             */

            if (username != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {

                /*
                 * Load user details from database
                 */
                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(username);

                /*
                 * -------------------------------------------------
                 * VALIDATE TOKEN
                 * -------------------------------------------------
                 */

                if (jwtUtil.validateToken(token, userDetails)) {

                    /*
                     * Create authenticated user
                     */
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    /*
                     * Add request details
                     */
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    /*
                     * Set authentication in Security Context
                     */
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authToken);
                }
            }

            /*
             * Continue request
             */
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException ex) {

            handleJwtException(
                    response,
                    HttpStatus.UNAUTHORIZED.value(),
                    "Token has expired"
            );

        } catch (SignatureException ex) {

            handleJwtException(
                    response,
                    HttpStatus.UNAUTHORIZED.value(),
                    "Invalid token signature"
            );

        } catch (MalformedJwtException ex) {

            handleJwtException(
                    response,
                    HttpStatus.UNAUTHORIZED.value(),
                    "Invalid token format"
            );

        } catch (Exception ex) {

            handleJwtException(
                    response,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Authentication error: " + ex.getMessage()
            );
        }
    }

    /*
     * -------------------------------------------------------------
     * JWT ERROR RESPONSE
     * -------------------------------------------------------------
     */

    private void handleJwtException(
            HttpServletResponse response,
            int code,
            String message
    ) throws IOException {

        /*
         * Return the actual HTTP status.
         *
         * Previously this was always 200 OK.
         */
        response.setStatus(code);

        response.setContentType(
                MediaType.APPLICATION_JSON_VALUE
        );

        Map<String, Object> errorResponse = new HashMap<>();

        errorResponse.put("code", code);
        errorResponse.put("message", message);
        errorResponse.put("data", null);

        response.getWriter().write(
                objectMapper.writeValueAsString(errorResponse)
        );
    }
}
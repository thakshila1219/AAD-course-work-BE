package lk.ijse.aad_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {

    // Helper method to create standard response map
    private ResponseEntity<Map<String, Object>> buildResponse(int code, String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("message", message);
        response.put("data", null);
        return new ResponseEntity<>(response, status);
    }

    // 1. Custom Exception (400)
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException ex) {
        return buildResponse(400, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 2. Resource Not Found (404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return buildResponse(404, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 3. Duplicate Record (409)
    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateRecordException(DuplicateRecordException ex) {
        return buildResponse(409, ex.getMessage(), HttpStatus.CONFLICT);
    }

    // 4. Unauthorized Exception (401)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedException(UnauthorizedException ex) {
        return buildResponse(401, ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // 5. Access Denied Exception (403)
    @ExceptionHandler(AccessDeniedCustomException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedCustomException ex) {
        return buildResponse(403, ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    // 6. Bad Request Exception (400)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequestException(BadRequestException ex) {
        return buildResponse(400, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 7. Generic Server Exception (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return buildResponse(500, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
package lk.ijse.aad_project.controller;

import lk.ijse.aad_project.dto.UserDTO;
import lk.ijse.aad_project.service.UserService;
import lk.ijse.aad_project.contant.CommonResponse;
import lk.ijse.aad_project.security.JwtUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static lk.ijse.aad_project.contant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.aad_project.contant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping(value = "v1/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(
            UserService userService,
            JwtUtil jwtUtil
    ) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }


    // =========================================================
    // 1. Customer Signup / Self Registration
    // =========================================================

    @PostMapping(
            value = "/register",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse> registerUser(
            @RequestBody UserDTO userDTO
    ) {

        // Default role = CUSTOMER
        if (
                userDTO.getRole() == null ||
                        userDTO.getRole().isEmpty()
        ) {

            userDTO.setRole("CUSTOMER");
        }

        userService.saveUser(userDTO);

        CommonResponse response =
                new CommonResponse(
                        OPERATION_SUCCESS,
                        SUCCESS_MESSAGE
                );

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // 2. Login
    // =========================================================

    @PostMapping(
            value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse> loginUser(
            @RequestBody UserDTO userDTO
    ) {

        UserDTO authenticatedUser =
                userService.authenticate(
                        userDTO.getEmail(),
                        userDTO.getPassword()
                );


        System.out.println(
                "LOGGED USER EMAIL: "
                        + authenticatedUser.getEmail()
        );

        System.out.println(
                "LOGGED USER ROLE: "
                        + authenticatedUser.getRole()
        );


        // Generate JWT
        String token =
                jwtUtil.generateToken(
                        authenticatedUser
                );


        System.out.println(
                "JWT TOKEN GENERATED SUCCESSFULLY"
        );


        // Login response data
        Map<String, Object> loginData =
                new HashMap<>();

        loginData.put(
                "token",
                token
        );

        loginData.put(
                "user",
                authenticatedUser
        );


        // Common response
        CommonResponse response =
                new CommonResponse(
                        OPERATION_SUCCESS,
                        SUCCESS_MESSAGE
                );


        response.setData(
                loginData
        );


        return ResponseEntity.ok(response);
    }


    // =========================================================
    // 3. GET ALL USERS
    // =========================================================

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<UserDTO> getAllUsers() {

        return userService.getAllUsers();
    }


    // =========================================================
    // 4. Admin - Save User
    // =========================================================

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse saveUser(
            @RequestBody UserDTO userDTO
    ) {

        userService.saveUser(userDTO);

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }


    // =========================================================
    // 5. Update User
    // =========================================================

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse updateUser(
            @RequestBody UserDTO userDTO
    ) {

        userService.updateUser(userDTO);

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }


    // =========================================================
    // 6. Delete User
    // =========================================================

    @DeleteMapping(
            value = "/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse removeUser(
            @PathVariable long userId
    ) {

        userService.removeUser(userId);

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }
}
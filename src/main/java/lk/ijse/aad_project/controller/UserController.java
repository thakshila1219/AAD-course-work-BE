package lk.ijse.aad_project.controller;

import lk.ijse.aad_project.dto.UserDTO;
import lk.ijse.aad_project.service.UserService; 
import lk.ijse.aad_project.contant.CommonResponse;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> registerUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getRole() == null || userDTO.getRole().isEmpty()) {
            userDTO.setRole("CUSTOMER");
        }
        userService.saveUser(userDTO);

        CommonResponse response = new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> loginUser(@RequestBody UserDTO userDTO) {
        UserDTO authenticatedUser = userService.authenticate(userDTO.getEmail(), userDTO.getPassword());

        System.out.println("LOGGED USER EMAIL: " + authenticatedUser.getEmail());
        System.out.println("LOGGED USER ROLE: " + authenticatedUser.getRole());

        CommonResponse response = new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);

        response.setData(authenticatedUser);

        return ResponseEntity.ok(response);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveUser(@RequestBody UserDTO userDTO){
        userService.saveUser(userDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateUser(@RequestBody UserDTO userDTO){
        userService.updateUser(userDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @DeleteMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse removeUser(@PathVariable long userId){
        userService.removeUser(userId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}

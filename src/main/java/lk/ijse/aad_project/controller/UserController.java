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
@CrossOrigin(origins = "*", allowedHeaders = "*") // CORS preflight issue එක විසඳීමට allowedHeaders එකතු කළා
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. Customer Signup / Self-Registration Endpoint (අලුතෙන් එකතු කළ කොටස)
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> registerUser(@RequestBody UserDTO userDTO) {
        // Customer කෙනෙක් Register වෙද්දී Role එකක් ඇවිත් නැත්නම් Auto "CUSTOMER" හෝ "USER" ලෙස set කිරීම
        if (userDTO.getRole() == null || userDTO.getRole().isEmpty()) {
            userDTO.setRole("CUSTOMER");
        }
        userService.saveUser(userDTO);

        CommonResponse response = new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
        return ResponseEntity.ok(response);
    }

    // 2. Login Endpoint එක (ඔයා සාර්ථකව හදාගත්ත Original Code එක)
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> loginUser(@RequestBody UserDTO userDTO) {
        // Service layer එකෙන් User Authenticate කර Authenticated UserDTO එක ලබා ගැනීම
        UserDTO authenticatedUser = userService.authenticate(userDTO.getEmail(), userDTO.getPassword());

        // IntelliJ Console එකේ Debug කරගැනීමට Print කිරීම
        System.out.println("LOGGED USER EMAIL: " + authenticatedUser.getEmail());
        System.out.println("LOGGED USER ROLE: " + authenticatedUser.getRole());

        // CommonResponse එක සෑදීම
        CommonResponse response = new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);

        // Response එකේ data field එකට authenticatedUser set කිරීම
        response.setData(authenticatedUser);

        return ResponseEntity.ok(response);
    }

    // 3. Admin පැත්තෙන් direct Save කරන Endpoint එක (Original)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveUser(@RequestBody UserDTO userDTO){
        userService.saveUser(userDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    // 4. Update Endpoint එක (Original)
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateUser(@RequestBody UserDTO userDTO){
        userService.updateUser(userDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    // 5. Delete Endpoint එක (Original)
    @DeleteMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse removeUser(@PathVariable long userId){
        userService.removeUser(userId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}
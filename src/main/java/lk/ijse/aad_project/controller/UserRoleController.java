package lk.ijse.aad_project.controller; 

import lk.ijse.aad_project.dto.UserRoleDTO;
import lk.ijse.aad_project.service.UserRoleService;
import lk.ijse.aad_project.contant.CommonResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static lk.ijse.aad_project.contant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.aad_project.contant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping(value = "v1/user-roles")
public class UserRoleController {

    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveUserRole(@RequestBody UserRoleDTO userRoleDTO){
        userRoleService.saveUserRole(userRoleDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateUserRole(@RequestBody UserRoleDTO userRoleDTO){
        userRoleService.updateUserRole(userRoleDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @DeleteMapping(value = "/{userRoleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse removeUserRole(@PathVariable long userRoleId){
        userRoleService.removeUserRole(userRoleId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}

package lk.ijse.aad_project.controller;

import lk.ijse.aad_project.dto.StaffAssignmentDTO; 
import lk.ijse.aad_project.service.StaffAssignmentService;
import lk.ijse.aad_project.contant.CommonResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static lk.ijse.aad_project.contant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.aad_project.contant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping(value = "v1/staff-assignments")
public class StaffAssignmentController {

    private final StaffAssignmentService staffAssignmentService;

    public StaffAssignmentController(StaffAssignmentService staffAssignmentService) {
        this.staffAssignmentService = staffAssignmentService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveStaffAssignment(@RequestBody StaffAssignmentDTO staffAssignmentDTO){
        staffAssignmentService.saveStaffAssignment(staffAssignmentDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateStaffAssignment(@RequestBody StaffAssignmentDTO staffAssignmentDTO){
        staffAssignmentService.updateStaffAssignment(staffAssignmentDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @DeleteMapping(value = "/{assignmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse removeStaffAssignment(@PathVariable long assignmentId){
        staffAssignmentService.removeStaffAssignment(assignmentId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}

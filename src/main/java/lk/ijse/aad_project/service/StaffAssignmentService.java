package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.StaffAssignmentDTO;

public interface StaffAssignmentService {
    void saveStaffAssignment(StaffAssignmentDTO staffAssignmentDTO);
    void updateStaffAssignment(StaffAssignmentDTO staffAssignmentDTO);
    void removeStaffAssignment(long assignmentId);
}
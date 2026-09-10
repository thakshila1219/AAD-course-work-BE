package lk.ijse.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate; 

@Data 
@AllArgsConstructor
@NoArgsConstructor
public class StaffAssignmentDTO {
    private long assignmentId;
    private LocalDate assignedDate;
    private String shift;
    private long userId;
    private long tableId;

    public StaffAssignmentDTO(LocalDate assignedDate, String shift, long userId, long tableId) {
        this.assignedDate = assignedDate;
        this.shift = shift;
        this.userId = userId;
        this.tableId = tableId;
    }
}

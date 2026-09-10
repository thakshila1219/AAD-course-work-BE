package lk.ijse.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiningTableDTO {
    private long tableId;
    private String tableNumber;
    private int capacity;
    private String status;

    public DiningTableDTO(String tableNumber, int capacity, String status) {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = status;
    }
}
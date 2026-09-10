package lk.ijse.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private long reservationId;
    private LocalDateTime reservationTime;
    private String status;
    private long userId;
    private long tableId;

    public ReservationDTO(LocalDateTime reservationTime, String status, long userId, long tableId) {
        this.reservationTime = reservationTime;
        this.status = status;
        this.userId = userId;
        this.tableId = tableId;
    }
}
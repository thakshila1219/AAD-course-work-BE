package lk.ijse.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReservationDTO {

    private long reservationId;
    private LocalDateTime reservationTime;
    private String status;
    private long userId;
    private long tableId;

    private String username;
    private String tableNumber;


    // ============================================================
    // Constructor for creating a new reservation
    // ============================================================

    public ReservationDTO(
            LocalDateTime reservationTime,
            String status,
            long userId,
            long tableId
    ) {
        this.reservationTime = reservationTime;
        this.status = status;
        this.userId = userId;
        this.tableId = tableId;
    }


    // ============================================================
    // Constructor for GET all reservations
    // ============================================================

    public ReservationDTO(
            long reservationId,
            LocalDateTime reservationTime,
            String status,
            long userId,
            long tableId,
            String username,
            String tableNumber
    ) {
        this.reservationId = reservationId;
        this.reservationTime = reservationTime;
        this.status = status;
        this.userId = userId;
        this.tableId = tableId;
        this.username = username;
        this.tableNumber = tableNumber;
    }
}
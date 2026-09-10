package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.ReservationDTO;

public interface ReservationService {
    void saveReservation(ReservationDTO reservationDTO); 
    void updateReservation(ReservationDTO reservationDTO);
    void removeReservation(long reservationId);

    void cancelReservation(long reservationId);
}

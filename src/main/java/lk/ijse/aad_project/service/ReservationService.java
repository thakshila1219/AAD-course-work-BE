package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.ReservationDTO;

import java.util.List;

public interface ReservationService {

    void saveReservation(ReservationDTO reservationDTO);

    void updateReservation(ReservationDTO reservationDTO);

    void removeReservation(long reservationId);

    void cancelReservation(long reservationId);

    List<ReservationDTO> getAllReservations();
}
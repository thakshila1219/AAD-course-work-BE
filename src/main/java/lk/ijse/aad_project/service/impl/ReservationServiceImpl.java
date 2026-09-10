package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.ReservationDTO;
import lk.ijse.aad_project.entity.DiningTable;
import lk.ijse.aad_project.entity.Reservation;
import lk.ijse.aad_project.entity.User; 
import lk.ijse.aad_project.repository.DiningTableRepository;
import lk.ijse.aad_project.repository.ReservationRepository;
import lk.ijse.aad_project.repository.UserRepository;
import lk.ijse.aad_project.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final DiningTableRepository diningTableRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository, DiningTableRepository diningTableRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.diningTableRepository = diningTableRepository;
    }

    @Override
    public void saveReservation(ReservationDTO reservationDTO) {
        log.info("Execute method saveReservation");
        try {
            Reservation reservation = new Reservation();
            reservation.setReservationTime(reservationDTO.getReservationTime());
            reservation.setStatus(reservationDTO.getStatus());

            Optional<User> optionalUser = userRepository.findById(reservationDTO.getUserId());
            if (optionalUser.isEmpty())
                throw new RuntimeException("Sorry, related user is not found.");
            reservation.setUser(optionalUser.get());

            Optional<DiningTable> optionalTable = diningTableRepository.findById(reservationDTO.getTableId());
            if (optionalTable.isEmpty())
                throw new RuntimeException("Sorry, related dining table is not found.");
            reservation.setDiningTable(optionalTable.get());

            reservationRepository.save(reservation);
        } catch (Exception e) {
            log.error("Error in saveReservation : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateReservation(ReservationDTO reservationDTO) {
        log.info("Execute method updateReservation");
        try {
            Optional<Reservation> optionalReservation = reservationRepository.findById(reservationDTO.getReservationId());
            if (optionalReservation.isEmpty())
                throw new RuntimeException("Sorry, related reservation is not found.");

            Reservation reservation = optionalReservation.get();
            reservation.setReservationTime(reservationDTO.getReservationTime());
            reservation.setStatus(reservationDTO.getStatus());

            Optional<User> optionalUser = userRepository.findById(reservationDTO.getUserId());
            if (optionalUser.isEmpty())
                throw new RuntimeException("Sorry, related user is not found.");
            reservation.setUser(optionalUser.get());

            Optional<DiningTable> optionalTable = diningTableRepository.findById(reservationDTO.getTableId());
            if (optionalTable.isEmpty())
                throw new RuntimeException("Sorry, related dining table is not found.");
            reservation.setDiningTable(optionalTable.get());

            reservationRepository.save(reservation);
        } catch (Exception e) {
            log.error("Error in updateReservation : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void removeReservation(long reservationId) {
        log.info("Execute method removeReservation");
        try {
            Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
            if (optionalReservation.isEmpty())
                throw new RuntimeException("Sorry, related reservation is not found.");

            reservationRepository.deleteById(reservationId);
        } catch (Exception e) {
            log.error("Error in removeReservation : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void cancelReservation(long reservationId) {
        log.info("Execute method cancelReservation");
        try {
            Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
            if (optionalReservation.isEmpty()) {
                throw new RuntimeException("Sorry, related reservation is not found.");
            }

            Reservation reservation = optionalReservation.get();
            reservation.setStatus("CANCELLED");

            reservationRepository.save(reservation);
        } catch (Exception e) {
            log.error("Error in cancelReservation : " + e.getMessage());
            throw e;
        }
    }
}

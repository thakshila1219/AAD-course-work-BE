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

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final DiningTableRepository diningTableRepository;


    public ReservationServiceImpl(
            ReservationRepository reservationRepository,
            UserRepository userRepository,
            DiningTableRepository diningTableRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.diningTableRepository = diningTableRepository;
    }


    // ============================================================
    // SAVE RESERVATION
    // ============================================================

    @Override
    public void saveReservation(
            ReservationDTO reservationDTO
    ) {

        log.info("Execute method saveReservation");

        try {

            // ----------------------------------------------------
            // Find User
            // ----------------------------------------------------

            Optional<User> optionalUser =
                    userRepository.findById(
                            reservationDTO.getUserId()
                    );

            if (optionalUser.isEmpty()) {

                throw new RuntimeException(
                        "Sorry, related user is not found."
                );
            }

            User user = optionalUser.get();


            // ----------------------------------------------------
            // Check CUSTOMER role
            // ----------------------------------------------------

            boolean isCustomer =
                    user.getUserRoleList() != null
                            && user.getUserRoleList()
                            .stream()
                            .anyMatch(userRole ->
                                    userRole.getRole() != null
                                            && "CUSTOMER".equalsIgnoreCase(
                                            userRole
                                                    .getRole()
                                                    .getRoleName()
                                    )
                            );


            if (!isCustomer) {

                throw new RuntimeException(
                        "Only customers can create reservations."
                );
            }


            // ----------------------------------------------------
            // Find Dining Table
            // ----------------------------------------------------

            Optional<DiningTable> optionalTable =
                    diningTableRepository.findById(
                            reservationDTO.getTableId()
                    );

            if (optionalTable.isEmpty()) {

                throw new RuntimeException(
                        "Sorry, related dining table is not found."
                );
            }

            DiningTable diningTable =
                    optionalTable.get();


            // ----------------------------------------------------
            // Create Reservation
            // ----------------------------------------------------

            Reservation reservation =
                    new Reservation();

            reservation.setReservationTime(
                    reservationDTO.getReservationTime()
            );

            reservation.setStatus(
                    reservationDTO.getStatus()
            );

            reservation.setUser(user);

            reservation.setDiningTable(
                    diningTable
            );


            // ----------------------------------------------------
            // Save Reservation
            // ----------------------------------------------------

            reservationRepository.save(
                    reservation
            );


            log.info(
                    "Reservation saved successfully. ID: {}",
                    reservation.getReservationId()
            );

        } catch (Exception e) {

            log.error(
                    "Error in saveReservation : {}",
                    e.getMessage()
            );

            throw e;
        }
    }


    // ============================================================
    // UPDATE RESERVATION
    // ============================================================

    @Override
    public void updateReservation(
            ReservationDTO reservationDTO
    ) {

        log.info("Execute method updateReservation");

        try {

            // ----------------------------------------------------
            // Find Reservation
            // ----------------------------------------------------

            Optional<Reservation> optionalReservation =
                    reservationRepository.findById(
                            reservationDTO.getReservationId()
                    );

            if (optionalReservation.isEmpty()) {

                throw new RuntimeException(
                        "Sorry, related reservation is not found."
                );
            }

            Reservation reservation =
                    optionalReservation.get();


            // ----------------------------------------------------
            // Update Reservation Time
            // ----------------------------------------------------

            reservation.setReservationTime(
                    reservationDTO.getReservationTime()
            );


            // ----------------------------------------------------
            // Update Status
            // ----------------------------------------------------

            reservation.setStatus(
                    reservationDTO.getStatus()
            );


            // ----------------------------------------------------
            // Find User
            // ----------------------------------------------------

            Optional<User> optionalUser =
                    userRepository.findById(
                            reservationDTO.getUserId()
                    );

            if (optionalUser.isEmpty()) {

                throw new RuntimeException(
                        "Sorry, related user is not found."
                );
            }

            User user =
                    optionalUser.get();


            // ----------------------------------------------------
            // Update User
            // ----------------------------------------------------

            reservation.setUser(user);


            // ----------------------------------------------------
            // Find Dining Table
            // ----------------------------------------------------

            Optional<DiningTable> optionalTable =
                    diningTableRepository.findById(
                            reservationDTO.getTableId()
                    );

            if (optionalTable.isEmpty()) {

                throw new RuntimeException(
                        "Sorry, related dining table is not found."
                );
            }

            DiningTable diningTable =
                    optionalTable.get();


            // ----------------------------------------------------
            // Update Dining Table
            // ----------------------------------------------------

            reservation.setDiningTable(
                    diningTable
            );


            // ----------------------------------------------------
            // Save Updated Reservation
            // ----------------------------------------------------

            reservationRepository.save(
                    reservation
            );


            log.info(
                    "Reservation updated successfully. ID: {}",
                    reservation.getReservationId()
            );


        } catch (Exception e) {

            log.error(
                    "Error in updateReservation : {}",
                    e.getMessage()
            );

            throw e;
        }
    }


    // ============================================================
    // DELETE RESERVATION
    // ============================================================

    @Override
    public void removeReservation(
            long reservationId
    ) {

        log.info("Execute method removeReservation");

        try {

            Optional<Reservation> optionalReservation =
                    reservationRepository.findById(
                            reservationId
                    );

            if (optionalReservation.isEmpty()) {

                throw new RuntimeException(
                        "Sorry, related reservation is not found."
                );
            }


            reservationRepository.deleteById(
                    reservationId
            );


            log.info(
                    "Reservation deleted successfully. ID: {}",
                    reservationId
            );

        } catch (Exception e) {

            log.error(
                    "Error in removeReservation : {}",
                    e.getMessage()
            );

            throw e;
        }
    }


    // ============================================================
    // CANCEL RESERVATION
    // ============================================================

    @Override
    public void cancelReservation(
            long reservationId
    ) {

        log.info("Execute method cancelReservation");

        try {

            Optional<Reservation> optionalReservation =
                    reservationRepository.findById(
                            reservationId
                    );

            if (optionalReservation.isEmpty()) {

                throw new RuntimeException(
                        "Sorry, related reservation is not found."
                );
            }


            Reservation reservation =
                    optionalReservation.get();


            reservation.setStatus(
                    "CANCELLED"
            );


            reservationRepository.save(
                    reservation
            );


            log.info(
                    "Reservation cancelled successfully. ID: {}",
                    reservationId
            );

        } catch (Exception e) {

            log.error(
                    "Error in cancelReservation : {}",
                    e.getMessage()
            );

            throw e;
        }
    }


    // ============================================================
    // GET ALL RESERVATIONS
    // ============================================================

    @Override
    public List<ReservationDTO> getAllReservations() {

        log.info("Execute method getAllReservations");

        try {

            List<Reservation> reservations =
                    reservationRepository.findAll();


            return reservations.stream()
                    .map(reservation -> {

                        String username = "-";

                        String tableNumber = "-";


                        // ------------------------------------------------
                        // Get Username
                        // ------------------------------------------------

                        if (reservation.getUser() != null) {

                            username =
                                    reservation
                                            .getUser()
                                            .getUsername();
                        }


                        // ------------------------------------------------
                        // Get Table Number
                        // ------------------------------------------------

                        if (
                                reservation.getDiningTable()
                                        != null
                        ) {

                            tableNumber =
                                    reservation
                                            .getDiningTable()
                                            .getTableNumber();
                        }


                        // ------------------------------------------------
                        // Create DTO
                        // ------------------------------------------------

                        return new ReservationDTO(

                                reservation.getReservationId(),

                                reservation.getReservationTime(),

                                reservation.getStatus(),

                                reservation.getUser() != null
                                        ? reservation
                                          .getUser()
                                          .getUserId()
                                        : 0,

                                reservation.getDiningTable() != null
                                        ? reservation
                                          .getDiningTable()
                                          .getTableId()
                                        : 0,

                                username,

                                tableNumber
                        );

                    })
                    .toList();

        } catch (Exception e) {

            log.error(
                    "Error in getAllReservations : {}",
                    e.getMessage()
            );

            throw e;
        }
    }
}


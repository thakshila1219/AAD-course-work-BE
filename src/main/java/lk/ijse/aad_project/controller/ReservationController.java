package lk.ijse.aad_project.controller;

import lk.ijse.aad_project.contant.CommonResponse;
import lk.ijse.aad_project.dto.ReservationDTO;
import lk.ijse.aad_project.service.ReservationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.aad_project.contant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.aad_project.contant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping("v1/reservations")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReservationController {

    private final ReservationService reservationService;


    public ReservationController(
            ReservationService reservationService
    ) {
        this.reservationService = reservationService;
    }


    // ============================================================
    // CREATE RESERVATION
    // ============================================================

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse saveReservation(
            @RequestBody ReservationDTO reservationDTO
    ) {

        reservationService.saveReservation(
                reservationDTO
        );

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }


    // ============================================================
    // GET ALL RESERVATIONS
    // ============================================================

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<ReservationDTO> getAllReservations() {

        return reservationService.getAllReservations();
    }


    // ============================================================
    // UPDATE RESERVATION
    // ============================================================

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse updateReservation(
            @RequestBody ReservationDTO reservationDTO
    ) {

        reservationService.updateReservation(
                reservationDTO
        );

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }


    // ============================================================
    // CANCEL RESERVATION
    // ============================================================

    @PatchMapping(
            value = "/{reservationId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse cancelReservation(
            @PathVariable long reservationId
    ) {

        reservationService.cancelReservation(
                reservationId
        );

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }


    // ============================================================
    // DELETE RESERVATION
    // ============================================================

    @DeleteMapping(
            value = "/{reservationId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse removeReservation(
            @PathVariable long reservationId
    ) {

        reservationService.removeReservation(
                reservationId
        );

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }
}
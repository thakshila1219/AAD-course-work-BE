package lk.ijse.aad_project.controller;

import lk.ijse.aad_project.dto.ReservationDTO;
import lk.ijse.aad_project.service.ReservationService; 
import lk.ijse.aad_project.contant.CommonResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static lk.ijse.aad_project.contant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.aad_project.contant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping(value = "v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveReservation(@RequestBody ReservationDTO reservationDTO){
        reservationService.saveReservation(reservationDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateReservation(@RequestBody ReservationDTO reservationDTO){
        reservationService.updateReservation(reservationDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @PatchMapping(value = "/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse cancelReservation(@PathVariable long reservationId){
        reservationService.cancelReservation(reservationId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}

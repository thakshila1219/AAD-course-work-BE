package lk.ijse.aad_project.controller;

import lk.ijse.aad_project.dto.DiningTableDTO;
import lk.ijse.aad_project.service.DiningTableService;
import lk.ijse.aad_project.contant.CommonResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.aad_project.contant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.aad_project.contant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping(value = "v1/dining-tables")
@CrossOrigin
public class DiningTableController {

    private final DiningTableService diningTableService;

    public DiningTableController(DiningTableService diningTableService) {
        this.diningTableService = diningTableService;
    }

    // GET ALL DINING TABLES
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DiningTableDTO> getAllDiningTables() {
        return diningTableService.getAllDiningTables();
    }

    // SAVE DINING TABLE
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveDiningTable(
            @RequestBody DiningTableDTO diningTableDTO) {

        diningTableService.saveDiningTable(diningTableDTO);

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }

    // UPDATE DINING TABLE
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateDiningTable(
            @RequestBody DiningTableDTO diningTableDTO) {

        diningTableService.updateDiningTable(diningTableDTO);

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }

    // DELETE DINING TABLE
    @DeleteMapping(
            value = "/{tableId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse removeDiningTable(
            @PathVariable long tableId) {

        diningTableService.removeDiningTable(tableId);

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }
}
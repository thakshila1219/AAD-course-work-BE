package lk.ijse.aad_project.controller;

import lk.ijse.aad_project.dto.DiningTableDTO;
import lk.ijse.aad_project.service.DiningTableService; 
import lk.ijse.aad_project.contant.CommonResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static lk.ijse.aad_project.contant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.aad_project.contant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping(value = "v1/dining-tables")
public class DiningTableController {

    private final DiningTableService diningTableService;

    public DiningTableController(DiningTableService diningTableService) {
        this.diningTableService = diningTableService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveDiningTable(@RequestBody DiningTableDTO diningTableDTO){
        diningTableService.saveDiningTable(diningTableDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateDiningTable(@RequestBody DiningTableDTO diningTableDTO){
        diningTableService.updateDiningTable(diningTableDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @DeleteMapping(value = "/{tableId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse removeDiningTable(@PathVariable long tableId){
        diningTableService.removeDiningTable(tableId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}

package lk.ijse.aad_project.controller;

import lk.ijse.aad_project.dto.SupplierDTO;
import lk.ijse.aad_project.service.SupplierService;
import lk.ijse.aad_project.contant.CommonResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.aad_project.contant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.aad_project.contant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping(value = "v1/suppliers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SupplierController {


    private final SupplierService supplierService;


    public SupplierController(
            SupplierService supplierService
    ) {

        this.supplierService =
                supplierService;
    }


    // =========================================================
    // 1. GET ALL SUPPLIERS
    // =========================================================

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<SupplierDTO> getAllSuppliers() {

        return supplierService
                .getAllSuppliers();
    }


    // =========================================================
    // 2. SAVE SUPPLIER
    // =========================================================

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse saveSupplier(
            @RequestBody SupplierDTO supplierDTO
    ) {

        supplierService.saveSupplier(
                supplierDTO
        );

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }


    // =========================================================
    // 3. UPDATE SUPPLIER
    // =========================================================

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse updateSupplier(
            @RequestBody SupplierDTO supplierDTO
    ) {

        supplierService.updateSupplier(
                supplierDTO
        );

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }


    // =========================================================
    // 4. DELETE SUPPLIER
    // =========================================================

    @DeleteMapping(
            value = "/{supplierId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse removeSupplier(
            @PathVariable long supplierId
    ) {

        supplierService.removeSupplier(
                supplierId
        );

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }
}
package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.SupplierDTO;
import lk.ijse.aad_project.entity.Supplier;
import lk.ijse.aad_project.repository.SupplierRepository;
import lk.ijse.aad_project.service.SupplierService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SupplierServiceImpl
        implements SupplierService {


    private final SupplierRepository supplierRepository;


    public SupplierServiceImpl(
            SupplierRepository supplierRepository
    ) {

        this.supplierRepository =
                supplierRepository;
    }


    // =========================================================
    // 1. SAVE SUPPLIER
    // =========================================================

    @Override
    public void saveSupplier(
            SupplierDTO supplierDTO
    ) {

        log.info(
                "Execute method saveSupplier"
        );

        try {

            Supplier supplier =
                    new Supplier();


            supplier.setName(
                    supplierDTO.getName()
            );

            supplier.setContactNumber(
                    supplierDTO.getContactNumber()
            );

            supplier.setEmail(
                    supplierDTO.getEmail()
            );


            supplierRepository.save(
                    supplier
            );

        } catch (Exception e) {

            log.error(
                    "Error in saveSupplier : {}",
                    e.getMessage()
            );

            throw e;
        }
    }


    // =========================================================
    // 2. GET ALL SUPPLIERS
    // =========================================================

    @Override
    public List<SupplierDTO> getAllSuppliers() {

        log.info(
                "Execute method getAllSuppliers"
        );

        try {

            List<Supplier> suppliers =
                    supplierRepository.findAll();


            List<SupplierDTO> dtoList =
                    new ArrayList<>();


            for (
                    Supplier supplier :
                    suppliers
            ) {

                SupplierDTO dto =
                        new SupplierDTO();


                dto.setSupplierId(
                        supplier.getSupplierId()
                );

                dto.setName(
                        supplier.getName()
                );

                dto.setContactNumber(
                        supplier.getContactNumber()
                );

                dto.setEmail(
                        supplier.getEmail()
                );


                dtoList.add(dto);
            }


            return dtoList;

        } catch (Exception e) {

            log.error(
                    "Error in getAllSuppliers : {}",
                    e.getMessage()
            );

            throw e;
        }
    }


    // =========================================================
    // 3. UPDATE SUPPLIER
    // =========================================================

    @Override
    public void updateSupplier(
            SupplierDTO supplierDTO
    ) {

        log.info(
                "Execute method updateSupplier"
        );

        try {

            Optional<Supplier> optionalSupplier =
                    supplierRepository.findById(
                            supplierDTO.getSupplierId()
                    );


            if (
                    optionalSupplier.isEmpty()
            ) {

                throw new RuntimeException(
                        "Sorry, related supplier is not found."
                );
            }


            Supplier supplier =
                    optionalSupplier.get();


            supplier.setName(
                    supplierDTO.getName()
            );

            supplier.setContactNumber(
                    supplierDTO.getContactNumber()
            );

            supplier.setEmail(
                    supplierDTO.getEmail()
            );


            supplierRepository.save(
                    supplier
            );

        } catch (Exception e) {

            log.error(
                    "Error in updateSupplier : {}",
                    e.getMessage()
            );

            throw e;
        }
    }


    // =========================================================
    // 4. DELETE SUPPLIER
    // =========================================================

    @Override
    public void removeSupplier(
            long supplierId
    ) {

        log.info(
                "Execute method removeSupplier"
        );

        try {

            Optional<Supplier> optionalSupplier =
                    supplierRepository.findById(
                            supplierId
                    );


            if (
                    optionalSupplier.isEmpty()
            ) {

                throw new RuntimeException(
                        "Sorry, related supplier is not found."
                );
            }


            supplierRepository.deleteById(
                    supplierId
            );

        } catch (Exception e) {

            log.error(
                    "Error in removeSupplier : {}",
                    e.getMessage()
            );

            throw e;
        }
    }
}
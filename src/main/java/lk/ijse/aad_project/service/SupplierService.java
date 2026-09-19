package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.SupplierDTO;

import java.util.List;

public interface SupplierService {

    // Save Supplier
    void saveSupplier(SupplierDTO supplierDTO);

    // Update Supplier
    void updateSupplier(SupplierDTO supplierDTO);

    // Delete Supplier
    void removeSupplier(long supplierId);

    // Get All Suppliers
    List<SupplierDTO> getAllSuppliers();
}
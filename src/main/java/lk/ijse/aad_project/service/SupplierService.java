package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.SupplierDTO;

public interface SupplierService {
    void saveSupplier(SupplierDTO supplierDTO);
    void updateSupplier(SupplierDTO supplierDTO);
    void removeSupplier(long supplierId);
}
package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.DiningTableDTO;

public interface DiningTableService {
    void saveDiningTable(DiningTableDTO diningTableDTO);
    void updateDiningTable(DiningTableDTO diningTableDTO);
    void removeDiningTable(long tableId);
} 

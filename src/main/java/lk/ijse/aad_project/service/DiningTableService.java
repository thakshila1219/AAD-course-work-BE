package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.DiningTableDTO;

import java.util.List;

public interface DiningTableService {

    List<DiningTableDTO> getAllDiningTables();

    void saveDiningTable(DiningTableDTO diningTableDTO);

    void updateDiningTable(DiningTableDTO diningTableDTO);

    void removeDiningTable(long tableId);
}
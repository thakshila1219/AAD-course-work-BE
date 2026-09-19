package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.DiningTableDTO;
import lk.ijse.aad_project.entity.DiningTable;
import lk.ijse.aad_project.repository.DiningTableRepository;
import lk.ijse.aad_project.service.DiningTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DiningTableServiceImpl implements DiningTableService {

    private final DiningTableRepository diningTableRepository;

    public DiningTableServiceImpl(
            DiningTableRepository diningTableRepository) {

        this.diningTableRepository = diningTableRepository;
    }

    // =========================
    // GET ALL DINING TABLES
    // =========================
    @Override
    public List<DiningTableDTO> getAllDiningTables() {

        log.info("Execute method getAllDiningTables");

        List<DiningTable> tables =
                diningTableRepository.findAll();

        return tables.stream()
                .map(table -> new DiningTableDTO(
                        table.getTableId(),
                        table.getTableNumber(),
                        table.getCapacity(),
                        table.getStatus()
                ))
                .toList();
    }


    // =========================
    // SAVE DINING TABLE
    // =========================
    @Override
    public void saveDiningTable(DiningTableDTO diningTableDTO) {

        log.info("Execute method saveDiningTable");

        try {

            DiningTable diningTable = new DiningTable();

            diningTable.setTableNumber(
                    diningTableDTO.getTableNumber()
            );

            diningTable.setCapacity(
                    diningTableDTO.getCapacity()
            );

            diningTable.setStatus(
                    diningTableDTO.getStatus()
            );

            diningTableRepository.save(diningTable);

        } catch (Exception e) {

            log.error(
                    "Error in saveDiningTable : "
                            + e.getMessage()
            );

            throw e;
        }
    }


    // =========================
    // UPDATE DINING TABLE
    // =========================
    @Override
    public void updateDiningTable(
            DiningTableDTO diningTableDTO) {

        log.info("Execute method updateDiningTable");

        try {

            Optional<DiningTable> optionalDiningTable =
                    diningTableRepository.findById(
                            diningTableDTO.getTableId()
                    );

            if (optionalDiningTable.isEmpty()) {

                throw new RuntimeException(
                        "Sorry, related dining table is not found."
                );
            }

            DiningTable diningTable =
                    optionalDiningTable.get();

            diningTable.setTableNumber(
                    diningTableDTO.getTableNumber()
            );

            diningTable.setCapacity(
                    diningTableDTO.getCapacity()
            );

            diningTable.setStatus(
                    diningTableDTO.getStatus()
            );

            diningTableRepository.save(diningTable);

        } catch (Exception e) {

            log.error(
                    "Error in updateDiningTable : "
                            + e.getMessage()
            );

            throw e;
        }
    }


    // =========================
    // DELETE DINING TABLE
    // =========================
    @Override
    public void removeDiningTable(long tableId) {

        log.info("Execute method removeDiningTable");

        try {

            Optional<DiningTable> optionalDiningTable =
                    diningTableRepository.findById(tableId);

            if (optionalDiningTable.isEmpty()) {

                throw new RuntimeException(
                        "Sorry, related dining table is not found."
                );
            }

            diningTableRepository.deleteById(tableId);

        } catch (Exception e) {

            log.error(
                    "Error in removeDiningTable : "
                            + e.getMessage()
            );

            throw e;
        }
    }
}
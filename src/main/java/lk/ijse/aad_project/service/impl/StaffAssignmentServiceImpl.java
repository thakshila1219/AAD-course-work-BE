package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.StaffAssignmentDTO;
import lk.ijse.aad_project.entity.DiningTable;
import lk.ijse.aad_project.entity.StaffAssignment;
import lk.ijse.aad_project.entity.User;
import lk.ijse.aad_project.repository.DiningTableRepository;
import lk.ijse.aad_project.repository.StaffAssignmentRepository;
import lk.ijse.aad_project.repository.UserRepository;
import lk.ijse.aad_project.service.StaffAssignmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class StaffAssignmentServiceImpl implements StaffAssignmentService {

    private final StaffAssignmentRepository staffAssignmentRepository;
    private final UserRepository userRepository;
    private final DiningTableRepository diningTableRepository;

    public StaffAssignmentServiceImpl(StaffAssignmentRepository staffAssignmentRepository, UserRepository userRepository, DiningTableRepository diningTableRepository) {
        this.staffAssignmentRepository = staffAssignmentRepository;
        this.userRepository = userRepository;
        this.diningTableRepository = diningTableRepository;
    }

    @Override
    public void saveStaffAssignment(StaffAssignmentDTO staffAssignmentDTO) {
        log.info("Execute method saveStaffAssignment");
        try {
            StaffAssignment staffAssignment = new StaffAssignment();
            staffAssignment.setAssignedDate(staffAssignmentDTO.getAssignedDate());
            staffAssignment.setShift(staffAssignmentDTO.getShift());

            Optional<User> optionalUser = userRepository.findById(staffAssignmentDTO.getUserId());
            if (optionalUser.isEmpty())
                throw new RuntimeException("Sorry, related user is not found.");
            staffAssignment.setUser(optionalUser.get());

            Optional<DiningTable> optionalTable = diningTableRepository.findById(staffAssignmentDTO.getTableId());
            if (optionalTable.isEmpty())
                throw new RuntimeException("Sorry, related dining table is not found.");
            staffAssignment.setDiningTable(optionalTable.get());

            staffAssignmentRepository.save(staffAssignment);
        } catch (Exception e) {
            log.error("Error in saveStaffAssignment : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateStaffAssignment(StaffAssignmentDTO staffAssignmentDTO) {
        log.info("Execute method updateStaffAssignment");
        try {
            Optional<StaffAssignment> optionalStaffAssignment = staffAssignmentRepository.findById(staffAssignmentDTO.getAssignmentId());
            if (optionalStaffAssignment.isEmpty())
                throw new RuntimeException("Sorry, related staff assignment is not found.");

            StaffAssignment staffAssignment = optionalStaffAssignment.get();
            staffAssignment.setAssignedDate(staffAssignmentDTO.getAssignedDate());
            staffAssignment.setShift(staffAssignmentDTO.getShift());

            Optional<User> optionalUser = userRepository.findById(staffAssignmentDTO.getUserId());
            if (optionalUser.isEmpty())
                throw new RuntimeException("Sorry, related user is not found.");
            staffAssignment.setUser(optionalUser.get());

            Optional<DiningTable> optionalTable = diningTableRepository.findById(staffAssignmentDTO.getTableId());
            if (optionalTable.isEmpty())
                throw new RuntimeException("Sorry, related dining table is not found.");
            staffAssignment.setDiningTable(optionalTable.get());

            staffAssignmentRepository.save(staffAssignment);
        } catch (Exception e) {
            log.error("Error in updateStaffAssignment : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void removeStaffAssignment(long assignmentId) {
        log.info("Execute method removeStaffAssignment");
        try {
            Optional<StaffAssignment> optionalStaffAssignment = staffAssignmentRepository.findById(assignmentId);
            if (optionalStaffAssignment.isEmpty())
                throw new RuntimeException("Sorry, related staff assignment is not found.");

            staffAssignmentRepository.deleteById(assignmentId);
        } catch (Exception e) {
            log.error("Error in removeStaffAssignment : " + e.getMessage());
            throw e;
        }
    }
}
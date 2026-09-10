package lk.ijse.aad_project.repository;

import lk.ijse.aad_project.entity.StaffAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffAssignmentRepository extends JpaRepository<StaffAssignment, Long> {
}
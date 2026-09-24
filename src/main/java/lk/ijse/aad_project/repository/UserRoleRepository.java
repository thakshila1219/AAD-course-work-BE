package lk.ijse.aad_project.repository;

import lk.ijse.aad_project.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository
        extends JpaRepository<UserRole, Long> {
}
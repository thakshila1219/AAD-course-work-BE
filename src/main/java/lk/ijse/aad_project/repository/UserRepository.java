package lk.ijse.aad_project.repository;

import lk.ijse.aad_project.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {


    // Find User by Username
    Optional<User> findByUsername(
            String username
    );


    // Find User by Email
    Optional<User> findByEmail(
            String email
    );
}
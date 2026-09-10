package lk.ijse.aad_project.repository;

import lk.ijse.aad_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // මේ line එක එකතු කරන්න (Username එකෙන් User ව සෙවීමට)
    Optional<User> findByUsername(String username);

    // Email එකෙන් හොයන method එකත් තියෙන්න ඕන නම්:
    Optional<User> findByEmail(String email);
}
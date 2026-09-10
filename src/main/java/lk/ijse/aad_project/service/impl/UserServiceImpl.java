package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.UserDTO;
import lk.ijse.aad_project.entity.User;
import lk.ijse.aad_project.repository.UserRepository;
import lk.ijse.aad_project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        log.info("Execute method saveUser");
        try {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setEmail(userDTO.getEmail());

            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error in saveUser : {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        log.info("Execute method updateUser");
        try {
            Optional<User> optionalUser = userRepository.findById(userDTO.getUserId());
            if (optionalUser.isEmpty())
                throw new RuntimeException("Sorry, related user is not found.");

            User user = optionalUser.get();
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setEmail(userDTO.getEmail());

            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error in updateUser : {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void removeUser(long userId) {
        log.info("Execute method removeUser");
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty())
                throw new RuntimeException("Sorry, related user is not found.");

            userRepository.deleteById(userId);
        } catch (Exception e) {
            log.error("Error in removeUser : {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public UserDTO authenticate(String email, String password) {
        log.info("Execute method authenticate for email: {}", email);
        try {
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isEmpty()) {
                throw new RuntimeException("User not found with email: " + email);
            }

            User user = optionalUser.get();

            if (!user.getPassword().equals(password)) {
                throw new RuntimeException("Invalid Password");
            }

            UserDTO dto = new UserDTO();
            dto.setUserId(user.getUserId());
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setPassword(user.getPassword());
            dto.setRole(user.getRole()); 

            return dto;
        } catch (Exception e) {
            log.error("Error in authenticate : {}", e.getMessage());
            throw e;
        }
    }
}

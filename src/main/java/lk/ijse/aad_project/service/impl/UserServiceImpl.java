package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.UserDTO;
import lk.ijse.aad_project.entity.User;
import lk.ijse.aad_project.repository.UserRepository;
import lk.ijse.aad_project.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(UserDTO userDTO) {

        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());

        userRepository.save(user);
    }


    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();

        List<UserDTO> dtoList = new ArrayList<>();

        for (User user : users) {

            UserDTO dto = new UserDTO();

            dto.setUserId(user.getUserId());
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());

            // Send role
            dto.setRole(user.getRole());

            // Never send password
            dto.setPassword(null);

            dtoList.add(dto);
        }

        return dtoList;
    }


    @Override
    public void updateUser(UserDTO userDTO) {

        if (userDTO.getUserId() == null) {
            throw new RuntimeException("User ID is required");
        }

        User user = userRepository
                .findById(userDTO.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        if (userDTO.getPassword() != null &&
                !userDTO.getPassword().isBlank()) {

            user.setPassword(userDTO.getPassword());
        }

        userRepository.save(user);
    }


    @Override
    public void removeUser(long userId) {

        if (!userRepository.existsById(userId)) {

            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(userId);
    }


    @Override
    public UserDTO authenticate(
            String email,
            String password
    ) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid email or password"
                        )
                );

        if (!user.getPassword().equals(password)) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }


        UserDTO dto = new UserDTO();

        dto.setUserId(user.getUserId());

        dto.setUsername(user.getUsername());

        dto.setEmail(user.getEmail());

        // IMPORTANT
        dto.setRole(user.getRole());

        // Don't return password
        dto.setPassword(null);

        return dto;
    }
}
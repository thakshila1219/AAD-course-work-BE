package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.UserDTO;

import java.util.List;

public interface UserService {

    // Save User
    void saveUser(UserDTO userDTO);

    // Update User
    void updateUser(UserDTO userDTO);

    // Delete User
    void removeUser(long userId);

    // Get All Users
    List<UserDTO> getAllUsers();

    // Authenticate User
    UserDTO authenticate(String email, String password);
}
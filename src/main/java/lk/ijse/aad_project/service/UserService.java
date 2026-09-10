package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.UserDTO;

public interface UserService {
    void saveUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
    void removeUser(long userId);

    UserDTO authenticate(String email, String password);
}

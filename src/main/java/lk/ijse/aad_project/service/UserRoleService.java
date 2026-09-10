package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.UserRoleDTO; 

public interface UserRoleService {
    void saveUserRole(UserRoleDTO userRoleDTO);
    void updateUserRole(UserRoleDTO userRoleDTO);
    void removeUserRole(long userRoleId);
}

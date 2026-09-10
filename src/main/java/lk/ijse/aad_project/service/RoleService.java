package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.RoleDTO;

public interface RoleService {
    void saveRole(RoleDTO roleDTO);  
    void updateRole(RoleDTO roleDTO);
    void removeRole(long roleId);
}

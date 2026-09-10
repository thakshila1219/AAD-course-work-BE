package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.RoleDTO;
import lk.ijse.aad_project.entity.Role;
import lk.ijse.aad_project.repository.RoleRepository;
import lk.ijse.aad_project.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
 
import java.util.Optional;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveRole(RoleDTO roleDTO) {
        log.info("Execute method saveRole");
        try {
            Role role = new Role();
            role.setRoleName(roleDTO.getRoleName());

            roleRepository.save(role);
        } catch (Exception e) {
            log.error("Error in saveRole : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateRole(RoleDTO roleDTO) {
        log.info("Execute method updateRole");
        try {
            Optional<Role> optionalRole = roleRepository.findById(roleDTO.getRoleId());
            if (optionalRole.isEmpty())
                throw new RuntimeException("Sorry, related role is not found.");

            Role role = optionalRole.get();
            role.setRoleName(roleDTO.getRoleName());

            roleRepository.save(role);
        } catch (Exception e) {
            log.error("Error in updateRole : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void removeRole(long roleId) {
        log.info("Execute method removeRole");
        try {
            Optional<Role> optionalRole = roleRepository.findById(roleId);
            if (optionalRole.isEmpty())
                throw new RuntimeException("Sorry, related role is not found.");

            roleRepository.deleteById(roleId);
        } catch (Exception e) {
            log.error("Error in removeRole : " + e.getMessage());
            throw e;
        }
    }
}

package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.UserRoleDTO; 
import lk.ijse.aad_project.entity.Role;
import lk.ijse.aad_project.entity.User;
import lk.ijse.aad_project.entity.UserRole;
import lk.ijse.aad_project.repository.RoleRepository;
import lk.ijse.aad_project.repository.UserRepository;
import lk.ijse.aad_project.repository.UserRoleRepository;
import lk.ijse.aad_project.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveUserRole(UserRoleDTO userRoleDTO) {
        log.info("Execute method saveUserRole");
        try {
            UserRole userRole = new UserRole();

            Optional<User> optionalUser = userRepository.findById(userRoleDTO.getUserId());
            if (optionalUser.isEmpty())
                throw new RuntimeException("Sorry, related user is not found.");
            userRole.setUser(optionalUser.get());

            Optional<Role> optionalRole = roleRepository.findById(userRoleDTO.getRoleId());
            if (optionalRole.isEmpty())
                throw new RuntimeException("Sorry, related role is not found.");
            userRole.setRole(optionalRole.get());

            userRoleRepository.save(userRole);
        } catch (Exception e) {
            log.error("Error in saveUserRole : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateUserRole(UserRoleDTO userRoleDTO) {
        log.info("Execute method updateUserRole");
        try {
            Optional<UserRole> optionalUserRole = userRoleRepository.findById(userRoleDTO.getUserRoleId());
            if (optionalUserRole.isEmpty())
                throw new RuntimeException("Sorry, related user role is not found.");

            UserRole userRole = optionalUserRole.get();

            Optional<User> optionalUser = userRepository.findById(userRoleDTO.getUserId());
            if (optionalUser.isEmpty())
                throw new RuntimeException("Sorry, related user is not found.");
            userRole.setUser(optionalUser.get());

            Optional<Role> optionalRole = roleRepository.findById(userRoleDTO.getRoleId());
            if (optionalRole.isEmpty())
                throw new RuntimeException("Sorry, related role is not found.");
            userRole.setRole(optionalRole.get());

            userRoleRepository.save(userRole);
        } catch (Exception e) {
            log.error("Error in updateUserRole : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void removeUserRole(long userRoleId) {
        log.info("Execute method removeUserRole");
        try {
            Optional<UserRole> optionalUserRole = userRoleRepository.findById(userRoleId);
            if (optionalUserRole.isEmpty())
                throw new RuntimeException("Sorry, related user role is not found.");

            userRoleRepository.deleteById(userRoleId);
        } catch (Exception e) {
            log.error("Error in removeUserRole : " + e.getMessage());
            throw e;
        }
    }
}

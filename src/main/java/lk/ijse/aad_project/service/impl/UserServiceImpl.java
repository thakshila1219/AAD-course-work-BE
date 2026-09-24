
package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.UserDTO;
import lk.ijse.aad_project.entity.Role;
import lk.ijse.aad_project.entity.User;
import lk.ijse.aad_project.entity.UserRole;
import lk.ijse.aad_project.repository.RoleRepository;
import lk.ijse.aad_project.repository.UserRepository;
import lk.ijse.aad_project.repository.UserRoleRepository;
import lk.ijse.aad_project.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserRoleRepository userRoleRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }


    // =========================================================
    // SAVE USER / REGISTER CUSTOMER
    // =========================================================

    @Override
    public void saveUser(UserDTO userDTO) {

        System.out.println(
                "========== REGISTER DEBUG =========="
        );

        System.out.println(
                "USERNAME: [" + userDTO.getUsername() + "]"
        );

        System.out.println(
                "EMAIL: [" + userDTO.getEmail() + "]"
        );

        System.out.println(
                "PHONE: [" + userDTO.getPhoneNumber() + "]"
        );

        System.out.println(
                "PASSWORD: [" + userDTO.getPassword() + "]"
        );

        System.out.println(
                "ROLE: [" + userDTO.getRole() + "]"
        );


        // ==========================================
        // CREATE USER
        // ==========================================

        User user = new User();

        user.setUsername(
                userDTO.getUsername()
        );

        user.setPassword(
                userDTO.getPassword()
        );

        user.setEmail(
                userDTO.getEmail()
        );

        user.setPhoneNumber(
                userDTO.getPhoneNumber()
        );


        System.out.println(
                "USER OBJECT CREATED"
        );


        // ==========================================
        // SAVE USER
        // ==========================================

        User savedUser =
                userRepository.save(user);


        System.out.println(
                "USER SAVED SUCCESSFULLY"
        );

        System.out.println(
                "SAVED USER ID: " +
                        savedUser.getUserId()
        );


        // ==========================================
        // FIND CUSTOMER ROLE
        // ==========================================

        Role customerRole =
                roleRepository
                        .findByRoleName("CUSTOMER")
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "CUSTOMER role is not found."
                                )
                        );


        System.out.println(
                "CUSTOMER ROLE FOUND"
        );


        // ==========================================
        // CREATE USER ROLE
        // ==========================================

        UserRole userRole =
                new UserRole();

        userRole.setUser(
                savedUser
        );

        userRole.setRole(
                customerRole
        );


        // ==========================================
        // SAVE USER ROLE
        // ==========================================

        userRoleRepository.save(
                userRole
        );


        System.out.println(
                "USER ROLE SAVED SUCCESSFULLY"
        );

        System.out.println(
                "========== REGISTER SUCCESS =========="
        );
    }


    // =========================================================
    // GET ALL USERS
    // =========================================================

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users =
                userRepository.findAll();

        List<UserDTO> dtoList =
                new ArrayList<>();


        for (User user : users) {

            UserDTO dto =
                    new UserDTO();

            dto.setUserId(
                    user.getUserId()
            );

            dto.setUsername(
                    user.getUsername()
            );

            dto.setEmail(
                    user.getEmail()
            );

            dto.setPhoneNumber(
                    user.getPhoneNumber()
            );

            dto.setRole(
                    user.getRole()
            );

            // Password should never be returned
            dto.setPassword(null);

            dtoList.add(dto);
        }


        return dtoList;
    }


    // =========================================================
    // UPDATE USER
    // =========================================================

    @Override
    public void updateUser(UserDTO userDTO) {

        if (userDTO.getUserId() == null) {

            throw new RuntimeException(
                    "User ID is required"
            );
        }


        User user =
                userRepository
                        .findById(
                                userDTO.getUserId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );


        user.setUsername(
                userDTO.getUsername()
        );

        user.setEmail(
                userDTO.getEmail()
        );

        user.setPhoneNumber(
                userDTO.getPhoneNumber()
        );


        // Update password only when provided
        if (
                userDTO.getPassword() != null &&
                        !userDTO.getPassword().isBlank()
        ) {

            user.setPassword(
                    userDTO.getPassword()
            );
        }


        userRepository.save(user);
    }


    // =========================================================
    // DELETE USER
    // =========================================================

    @Override
    public void removeUser(long userId) {

        if (!userRepository.existsById(userId)) {

            throw new RuntimeException(
                    "User not found"
            );
        }


        userRepository.deleteById(userId);
    }


    // =========================================================
    // AUTHENTICATE / LOGIN
    // =========================================================

    @Override
    public UserDTO authenticate(
            String email,
            String password
    ) {

        System.out.println(
                "========== LOGIN DEBUG =========="
        );

        System.out.println(
                "LOGIN EMAIL: [" + email + "]"
        );

        System.out.println(
                "LOGIN PASSWORD: [" + password + "]"
        );


        // ==========================================
        // FIND USER BY EMAIL
        // ==========================================

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> {

                            System.out.println(
                                    "USER NOT FOUND"
                            );

                            return new RuntimeException(
                                    "Invalid email or password"
                            );
                        });


        // ==========================================
        // USER FOUND
        // ==========================================

        System.out.println(
                "USER FOUND: [" +
                        user.getEmail() +
                        "]"
        );

        System.out.println(
                "DB PASSWORD: [" +
                        user.getPassword() +
                        "]"
        );


        // ==========================================
        // CHECK PASSWORD
        // ==========================================

        boolean passwordMatch =
                user.getPassword() != null &&
                        user.getPassword().equals(password);


        System.out.println(
                "PASSWORD MATCH: " +
                        passwordMatch
        );


        if (!passwordMatch) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }


        // ==========================================
        // CREATE USER DTO
        // ==========================================

        UserDTO dto =
                new UserDTO();


        dto.setUserId(
                user.getUserId()
        );

        dto.setUsername(
                user.getUsername()
        );

        dto.setEmail(
                user.getEmail()
        );

        dto.setPhoneNumber(
                user.getPhoneNumber()
        );

        dto.setRole(
                user.getRole()
        );


        // Never return password
        dto.setPassword(null);


        // ==========================================
        // LOGIN SUCCESS
        // ==========================================

        System.out.println(
                "LOGIN SUCCESS"
        );

        System.out.println(
                "LOGGED USER ID: " +
                        dto.getUserId()
        );

        System.out.println(
                "LOGGED USERNAME: " +
                        dto.getUsername()
        );

        System.out.println(
                "LOGGED USER EMAIL: " +
                        dto.getEmail()
        );

        System.out.println(
                "LOGGED USER PHONE: " +
                        dto.getPhoneNumber()
        );

        System.out.println(
                "LOGGED USER ROLE: " +
                        dto.getRole()
        );

        System.out.println(
                "================================"
        );


        return dto;
    }
}

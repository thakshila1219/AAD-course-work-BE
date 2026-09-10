package lk.ijse.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userId; // long වෙනුවට Long (Wrapper Class) ලෙස වෙනස් කරන්න
    private String username;
    private String password;
    private String email;
    private String role;
} 
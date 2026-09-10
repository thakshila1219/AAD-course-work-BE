package lk.ijse.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO {
    private long userRoleId;
    private long userId;
    private long roleId; 

    public UserRoleDTO(long userId, long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}

package lk.ijse.aad_project.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String username;

    private String password;

    private String email;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<UserRole> userRoleList;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Order> orderList;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Reservation> reservationList;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<StaffAssignment> staffAssignmentList;


    /**
     * Get user's role.
     *
     * This method adds a direct "role" property
     * to the JSON response.
     */
    @JsonProperty("role")
    public String getRole() {

        if (userRoleList != null && !userRoleList.isEmpty()) {

            UserRole userRole = userRoleList.get(0);

            if (userRole != null && userRole.getRole() != null) {

                String roleName = userRole.getRole().getRoleName();

                if (roleName != null && !roleName.isBlank()) {
                    return roleName;
                }
            }
        }

        // Default role
        return "CUSTOMER";
    }
}
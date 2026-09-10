package lk.ijse.aad_project.entity;

import com.fasterxml.jackson.annotation.JsonProperty; // <-- මේ import එක එකතු කරන්න
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserRole> userRoleList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orderList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservationList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StaffAssignment> staffAssignmentList;

    // JSON Response එකට direct "role" field එකක් විදියට එකතු වීමට:
    @JsonProperty("role")
    public String getRole() {
        if (userRoleList != null && !userRoleList.isEmpty()) {
            // UserRole ඇතුළේ Role object එක සහ එහි roleName field එක ඇති බව තහවුරු කරගන්න
            if (userRoleList.get(0).getRole() != null) {
                return userRoleList.get(0).getRole().getRoleName();
            }
        }
        return "CUSTOMER"; // Default fallback role
    }
}
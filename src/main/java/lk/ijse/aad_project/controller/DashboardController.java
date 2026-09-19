package lk.ijse.aad_project.controller;
import lk.ijse.aad_project.repository.IngredientRepository;
import lk.ijse.aad_project.repository.OrderRepository;
import lk.ijse.aad_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard")
@CrossOrigin
public class DashboardController {

    @Autowired private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private IngredientRepository ingredientRepository;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        Double totalRevenue = orderRepository.getTotalRevenue();
        Long totalOrders = orderRepository.count();
        Long activeUsers = userRepository.count();
        Long lowStockCount = ingredientRepository.countByQuantityLessThan(10);

        stats.put("totalRevenue", totalRevenue != null ? totalRevenue : 0.0);
        stats.put("totalOrders", totalOrders);
        stats.put("activeUsers", activeUsers);
        stats.put("lowStockItems", lowStockCount);

        return ResponseEntity.ok(stats);
    }
}
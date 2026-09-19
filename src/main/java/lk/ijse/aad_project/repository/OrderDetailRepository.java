package lk.ijse.aad_project.repository;

import lk.ijse.aad_project.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository
        extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByOrderOrderId(long orderId);
}
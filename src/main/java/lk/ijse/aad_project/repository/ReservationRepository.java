package lk.ijse.aad_project.repository;

import lk.ijse.aad_project.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}

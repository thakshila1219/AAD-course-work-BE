package lk.ijse.aad_project.repository;

import lk.ijse.aad_project.entity.Ingredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository
        extends JpaRepository<Ingredient, Long> {


    // =========================================================
    // COUNT LOW STOCK INGREDIENTS
    // =========================================================

    @Query(
            "SELECT COUNT(i) " +
                    "FROM Ingredient i " +
                    "WHERE i.quantityOnHand < :threshold"
    )
    long countByQuantityLessThan(
            @Param("threshold")
            double threshold
    );
}
package lk.ijse.aad_project.repository;

import lk.ijse.aad_project.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; 

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}

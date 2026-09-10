package lk.ijse.aad_project.repository;

import lk.ijse.aad_project.entity.RecipeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeItemRepository extends JpaRepository<RecipeItem, Long> {
}
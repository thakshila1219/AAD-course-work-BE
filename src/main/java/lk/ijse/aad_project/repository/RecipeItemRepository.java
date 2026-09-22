package lk.ijse.aad_project.repository;

import lk.ijse.aad_project.entity.RecipeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeItemRepository
        extends JpaRepository<RecipeItem, Long> {

    @Query("""
            SELECT DISTINCT c.categoryName
            FROM RecipeItem r
            JOIN r.menuItem m
            JOIN m.category c
            WHERE r.ingredient.ingredientId = :ingredientId
            """)
    List<String> findCategoryNamesByIngredientId(
            @Param("ingredientId") long ingredientId
    );
}
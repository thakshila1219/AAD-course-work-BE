package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.IngredientDTO;
import lk.ijse.aad_project.entity.Ingredient;
import lk.ijse.aad_project.entity.Supplier;
import lk.ijse.aad_project.repository.IngredientRepository; 
import lk.ijse.aad_project.repository.SupplierRepository;
import lk.ijse.aad_project.service.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final SupplierRepository supplierRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, SupplierRepository supplierRepository) {
        this.ingredientRepository = ingredientRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public void saveIngredient(IngredientDTO ingredientDTO) {
        log.info("Execute method saveIngredient");
        try {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(ingredientDTO.getName());
            ingredient.setQuantityOnHand(ingredientDTO.getQuantityOnHand());
            ingredient.setUnit(ingredientDTO.getUnit());

            Optional<Supplier> optionalSupplier = supplierRepository.findById(ingredientDTO.getSupplierId());
            if (optionalSupplier.isEmpty())
                throw new RuntimeException("Sorry, related supplier is not found.");

            ingredient.setSupplier(optionalSupplier.get());
            ingredientRepository.save(ingredient);
        } catch (Exception e) {
            log.error("Error in saveIngredient : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateIngredient(IngredientDTO ingredientDTO) {
        log.info("Execute method updateIngredient");
        try {
            Optional<Ingredient> optionalIngredient = ingredientRepository.findById(ingredientDTO.getIngredientId());
            if (optionalIngredient.isEmpty())
                throw new RuntimeException("Sorry, related ingredient is not found.");

            Ingredient ingredient = optionalIngredient.get();
            ingredient.setName(ingredientDTO.getName());
            ingredient.setQuantityOnHand(ingredientDTO.getQuantityOnHand());
            ingredient.setUnit(ingredientDTO.getUnit());

            Optional<Supplier> optionalSupplier = supplierRepository.findById(ingredientDTO.getSupplierId());
            if (optionalSupplier.isEmpty())
                throw new RuntimeException("Sorry, related supplier is not found.");

            ingredient.setSupplier(optionalSupplier.get());
            ingredientRepository.save(ingredient);
        } catch (Exception e) {
            log.error("Error in updateIngredient : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void removeIngredient(long ingredientId) {
        log.info("Execute method removeIngredient");
        try {
            Optional<Ingredient> optionalIngredient = ingredientRepository.findById(ingredientId);
            if (optionalIngredient.isEmpty())
                throw new RuntimeException("Sorry, related ingredient is not found.");

            ingredientRepository.deleteById(ingredientId);
        } catch (Exception e) {
            log.error("Error in removeIngredient : " + e.getMessage());
            throw e;
        }
    }
}

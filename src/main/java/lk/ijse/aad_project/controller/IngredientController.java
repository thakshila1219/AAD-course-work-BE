package lk.ijse.aad_project.controller;

import lk.ijse.aad_project.dto.IngredientDTO;
import lk.ijse.aad_project.service.IngredientService;
import lk.ijse.aad_project.contant.CommonResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static lk.ijse.aad_project.contant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.aad_project.contant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping(value = "v1/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveIngredient(@RequestBody IngredientDTO ingredientDTO){
        ingredientService.saveIngredient(ingredientDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateIngredient(@RequestBody IngredientDTO ingredientDTO){
        ingredientService.updateIngredient(ingredientDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @DeleteMapping(value = "/{ingredientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse removeIngredient(@PathVariable long ingredientId){
        ingredientService.removeIngredient(ingredientId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}
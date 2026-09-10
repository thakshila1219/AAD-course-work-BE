package lk.ijse.aad_project.controller;

import lk.ijse.aad_project.dto.RecipeItemDTO;
import lk.ijse.aad_project.service.RecipeItemService; 
import lk.ijse.aad_project.contant.CommonResponse; 
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static lk.ijse.aad_project.contant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.aad_project.contant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping(value = "v1/recipe-items")
public class RecipeItemController {

    private final RecipeItemService recipeItemService;

    public RecipeItemController(RecipeItemService recipeItemService) {
        this.recipeItemService = recipeItemService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveRecipeItem(@RequestBody RecipeItemDTO recipeItemDTO){
        recipeItemService.saveRecipeItem(recipeItemDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateRecipeItem(@RequestBody RecipeItemDTO recipeItemDTO){
        recipeItemService.updateRecipeItem(recipeItemDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @DeleteMapping(value = "/{recipeItemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse removeRecipeItem(@PathVariable long recipeItemId){
        recipeItemService.removeRecipeItem(recipeItemId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}

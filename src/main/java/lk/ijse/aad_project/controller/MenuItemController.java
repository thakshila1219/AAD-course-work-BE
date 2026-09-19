package lk.ijse.aad_project.controller;

import lk.ijse.aad_project.dto.MenuItemDTO;
import lk.ijse.aad_project.service.MenuItemService;
import lk.ijse.aad_project.contant.CommonResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.aad_project.contant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.aad_project.contant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping(value = "v1/menu-item")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    // ============================
    // GET ALL MENU ITEMS
    // ============================
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MenuItemDTO> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }


    // ============================
    // SAVE MENU ITEM
    // ============================
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveMenuItem(
            @RequestBody MenuItemDTO menuItemDTO
    ) {

        menuItemService.saveMenuItem(menuItemDTO);

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }


    // ============================
    // UPDATE MENU ITEM
    // ============================
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateMenuItem(
            @RequestBody MenuItemDTO menuItemDTO
    ) {

        menuItemService.updateMenuItem(menuItemDTO);

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }


    // ============================
    // DELETE MENU ITEM
    // ============================
    @DeleteMapping(
            value = "/{menuItemId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse removeMenuItem(
            @PathVariable long menuItemId
    ) {

        menuItemService.removeMenuItem(menuItemId);

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }
}
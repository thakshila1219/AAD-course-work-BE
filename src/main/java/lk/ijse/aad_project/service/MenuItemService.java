package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.MenuItemDTO;

import java.util.List;

public interface MenuItemService {

    void saveMenuItem(MenuItemDTO menuItemDTO);

    void updateMenuItem(MenuItemDTO menuItemDTO);

    void removeMenuItem(long itemId);

    List<MenuItemDTO> getAllMenuItems();
}
package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.MenuItemDTO;
import lk.ijse.aad_project.entity.Category;
import lk.ijse.aad_project.entity.MenuItem;
import lk.ijse.aad_project.repository.CategoryRepository;
import lk.ijse.aad_project.repository.MenuItemRepository;
import lk.ijse.aad_project.service.MenuItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service; 

import java.util.Optional;

@Service
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, CategoryRepository categoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void saveMenuItem(MenuItemDTO menuItemDTO) {
        log.info("Execute method saveMenuItem");
        try {
            MenuItem menuItem = new MenuItem();
            menuItem.setName(menuItemDTO.getName());
            menuItem.setPrice(menuItemDTO.getPrice());
            menuItem.setDescription(menuItemDTO.getDescription());

            Optional<Category> optionalCategory = categoryRepository.findById(menuItemDTO.getCategoryId());
            if (optionalCategory.isEmpty())
                throw new RuntimeException("Sorry, related category is not found.");

            menuItem.setCategory(optionalCategory.get());
            menuItemRepository.save(menuItem);
        } catch (Exception e) {
            log.error("Error in saveMenuItem : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateMenuItem(MenuItemDTO menuItemDTO) {
        log.info("Execute method updateMenuItem");
        try {
            Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(menuItemDTO.getItemId());
            if (optionalMenuItem.isEmpty())
                throw new RuntimeException("Sorry, related menu item is not found.");

            MenuItem menuItem = optionalMenuItem.get();
            menuItem.setName(menuItemDTO.getName());
            menuItem.setPrice(menuItemDTO.getPrice());
            menuItem.setDescription(menuItemDTO.getDescription());

            Optional<Category> optionalCategory = categoryRepository.findById(menuItemDTO.getCategoryId());
            if (optionalCategory.isEmpty())
                throw new RuntimeException("Sorry, related category is not found.");

            menuItem.setCategory(optionalCategory.get());
            menuItemRepository.save(menuItem);
        } catch (Exception e) {
            log.error("Error in updateMenuItem : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void removeMenuItem(long itemId) {
        log.info("Execute method removeMenuItem");
        try {
            Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(itemId);
            if (optionalMenuItem.isEmpty())
                throw new RuntimeException("Sorry, related menu item is not found.");

            menuItemRepository.deleteById(itemId);
        } catch (Exception e) {
            log.error("Error in removeMenuItem : " + e.getMessage());
            throw e;
        }
    }
}

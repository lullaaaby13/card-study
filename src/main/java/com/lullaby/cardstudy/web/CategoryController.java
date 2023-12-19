package com.lullaby.cardstudy.web;

import com.lullaby.cardstudy.appliation.AddCategoryCommand;
import com.lullaby.cardstudy.appliation.CategoryService;
import com.lullaby.cardstudy.appliation.UpdateCategoryCommand;
import com.lullaby.cardstudy.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/categories")
@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping
    public void addCategory(@RequestBody AddCategoryCommand command) {
        categoryService.addCategory(command);
    }

    @DeleteMapping
    public void deleteCategory(Long id) {
        categoryService.deleteCategory(id);
    }


    @PutMapping("{id}")
    public void updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryCommand command) {
        categoryService.updateCategory(id, command);
    }
}

package com.lullaby.cardstudy.appliation;

import com.lullaby.cardstudy.domain.Category;
import com.lullaby.cardstudy.domain.CategoryRepository;
import com.lullaby.cardstudy.dto.CategoryResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll()
                .stream().map(CategoryResponse::new)
                .toList();
    }
    public void addCategory(AddCategoryCommand command) {
        Category category = new Category(command.name(), command.description());
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public void updateCategory(Long id, UpdateCategoryCommand command) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(command.name());
        category.setDescription(command.description());
        categoryRepository.save(category);
    }

    public Optional<Category> findCategory(Long id) {
        return categoryRepository.findById(id);
    }

}

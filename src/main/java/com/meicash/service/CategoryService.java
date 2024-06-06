package com.meicash.service;

import com.meicash.domain.category.Category;
import com.meicash.domain.category.CategoryRepository;
import com.meicash.domain.category.RequestCategoryDTO;
import com.meicash.domain.category.ResponseCategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    private ResponseCategoryDTO categoryToResponseCategoryDTO(Category category){
        return new ResponseCategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public ResponseCategoryDTO createCategory(RequestCategoryDTO requestCategoryDTO) {
        Category newCategory = new Category(requestCategoryDTO);
        return categoryToResponseCategoryDTO(categoryRepository.save(newCategory));
    }

    public List<ResponseCategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::categoryToResponseCategoryDTO)
                .collect(Collectors.toList());
    }

    public Optional<ResponseCategoryDTO> getCategoryById(String categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            return Optional.of(categoryToResponseCategoryDTO(category.get()));
        } else {
            return Optional.empty();
        }
    }

}

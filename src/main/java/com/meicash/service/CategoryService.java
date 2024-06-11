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
    private final AuthorizationService authorizationService;

    public CategoryService(CategoryRepository categoryRepository, AuthorizationService authorizationService) {
        this.categoryRepository = categoryRepository;
        this.authorizationService = authorizationService;
    }

    private ResponseCategoryDTO categoryToResponseCategoryDTO(Category category){
        return new ResponseCategoryDTO(
                category.getId(),
                category.getName(),
                category.getColor()
        );
    }

    public ResponseCategoryDTO createCategory(RequestCategoryDTO requestCategoryDTO) {
        Category newCategory = new Category(requestCategoryDTO);
        newCategory.setUser(authorizationService.getAuthenticatedUser());
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

    public Optional<Category> getEntityCategoryById(String categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            return Optional.of(category.get());
        } else {
            return Optional.empty();
        }
    }

    public Optional<ResponseCategoryDTO> updateCategory(String categoryId, RequestCategoryDTO requestCategoryDTO) {
        return categoryRepository.findById(categoryId).map(
                category -> {
                    category.setName(requestCategoryDTO.name());
                    category.setColor(requestCategoryDTO.color());
                    return categoryToResponseCategoryDTO(categoryRepository.save(category));
                }
        );
    }

    public boolean deleteCategory(String categoryId) {
        return categoryRepository.findById(categoryId)
                .map(category -> {
                    categoryRepository.delete(category);
                    return true;
                })
                .orElse(false);
    }


}

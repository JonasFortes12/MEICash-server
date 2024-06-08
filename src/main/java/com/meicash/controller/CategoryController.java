package com.meicash.controller;

import com.meicash.domain.category.RequestCategoryDTO;
import com.meicash.domain.category.ResponseCategoryDTO;
import com.meicash.service.CategoryService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ResponseCategoryDTO> createCategory(@Validated @RequestBody RequestCategoryDTO requestCategoryDTO) {
        ResponseCategoryDTO responseCategoryDTO = categoryService.createCategory(requestCategoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseCategoryDTO);
    }

    @GetMapping
    public ResponseEntity<List<ResponseCategoryDTO>> getAllCategories() {
        List<ResponseCategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ResponseCategoryDTO> getCategoryById(@PathVariable String categoryId) {
        Optional<ResponseCategoryDTO> category = categoryService.getCategoryById(categoryId);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ResponseCategoryDTO> updateCategory(@PathVariable String categoryId, @RequestBody @Valid RequestCategoryDTO requestCategoryDTO) {
        Optional<ResponseCategoryDTO> updatedCategory = categoryService.updateCategory(categoryId, requestCategoryDTO);
        if (updatedCategory.isPresent()) {
            return ResponseEntity.ok(updatedCategory.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String categoryId) {
        return categoryService.deleteCategory(categoryId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}

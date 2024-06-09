package com.meicash.controller;

import com.meicash.domain.category.RequestCategoryDTO;
import com.meicash.domain.category.ResponseCategoryDTO;
import com.meicash.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Categorias", description = "Operações relacionadas a categorias das transações")
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Cria uma nova categoria", description = "Cria uma nova categoria no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada"),
            @ApiResponse(responseCode = "403", description = "Erro na requisição")
    })
    @PostMapping
    public ResponseEntity<ResponseCategoryDTO> createCategory(@Validated @RequestBody RequestCategoryDTO requestCategoryDTO) {
        ResponseCategoryDTO responseCategoryDTO = categoryService.createCategory(requestCategoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseCategoryDTO);
    }

    @Operation(summary = "Lista todas as categorias", description = "Retorna todas as categorias cadastradas no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias listadas"),
    })
    @GetMapping
    public ResponseEntity<List<ResponseCategoryDTO>> getAllCategories() {
        List<ResponseCategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Busca uma categoria por ID", description = "Retorna uma categoria específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @GetMapping("/{categoryId}")
    public ResponseEntity<ResponseCategoryDTO> getCategoryById(@PathVariable String categoryId) {
        Optional<ResponseCategoryDTO> category = categoryService.getCategoryById(categoryId);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualiza uma categoria", description = "Atualiza uma categoria existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")

    })
    @PutMapping("/{categoryId}")
    public ResponseEntity<ResponseCategoryDTO> updateCategory(@PathVariable String categoryId, @RequestBody @Valid RequestCategoryDTO requestCategoryDTO) {
        Optional<ResponseCategoryDTO> updatedCategory = categoryService.updateCategory(categoryId, requestCategoryDTO);
        if (updatedCategory.isPresent()) {
            return ResponseEntity.ok(updatedCategory.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Deleta uma categoria", description = "Deleta uma categoria existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria deletada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String categoryId) {
        return categoryService.deleteCategory(categoryId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}

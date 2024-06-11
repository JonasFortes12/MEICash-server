package com.meicash.controller;

import com.meicash.domain.category.Category;
import com.meicash.domain.category.RequestCategoryDTO;
import com.meicash.domain.category.ResponseCategoryDTO;
import com.meicash.domain.transaction.RequestTransactionDTO;
import com.meicash.domain.transaction.ResponseTransactionDTO;
import com.meicash.service.CategoryService;
import com.meicash.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Perfil de Usuário" , description = "Operações relacionadas às ações do usuário no sistema")
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final CategoryService categoryService;

    public ProfileController(final ProfileService profileService, final CategoryService categoryService) {
        this.profileService = profileService;
        this.categoryService = categoryService;
    }


    @Operation(summary = "O usuário registra uma nova transação", description = "Cria uma nova transação no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criou transação com sucesso"),
            @ApiResponse(responseCode = "400", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado ou Erro na requisição")
    })
    @PostMapping("/transactions/{categoryId}")
    public ResponseEntity<ResponseTransactionDTO> userCreateTransaction(
            @Valid @RequestBody final RequestTransactionDTO requestTransactionDTO,
            @PathVariable final String categoryId
    ) {
        Optional<Category> transactionCategory = categoryService.getEntityCategoryById(categoryId);

        if(transactionCategory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ResponseTransactionDTO newTransaction =  profileService.userCreateTransaction(
                requestTransactionDTO,
                transactionCategory.get()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newTransaction);
    }

    @Operation(summary = "O usuário recupera todas as suas transações", description = "Recupera todas as transações do usuário no usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transações do usuário recuperadas com sucesso"),
    })
    @GetMapping("/transactions")
    public List<ResponseTransactionDTO> getUserTransactions() {
        return profileService.getUserTransactions();
    }

    @Operation(summary = "O usuário registra uma nova categoria", description = "Cria uma nova categoria no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criou categoria com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    @PostMapping("/categories")
    public ResponseEntity<ResponseCategoryDTO> userCreateCategory(
            @Valid @RequestBody final RequestCategoryDTO requestCategoryDTO
    ){
        ResponseCategoryDTO newCategory = profileService.userCreateCategory(requestCategoryDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @Operation(summary = "O usuário recupera todas as suas categorias", description = "Recupera todas as categorias do usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias do usuário recuperadas com sucesso"),
    })
    @GetMapping("/categories")
    public List<ResponseCategoryDTO> getUserCategories() {
        return profileService.getUserCategories();
    }

}

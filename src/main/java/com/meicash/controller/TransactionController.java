package com.meicash.controller;

import com.meicash.domain.category.Category;
import com.meicash.domain.transaction.RequestTransactionDTO;
import com.meicash.domain.transaction.ResponseTransactionDTO;
import com.meicash.service.CategoryService;
import com.meicash.service.TransactionService;
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

@Tag(name = "Transações", description = "Operações relacionadas a transações")
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final CategoryService categoryService;

    public TransactionController(TransactionService transactionService, CategoryService categoryService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
    }

    @Operation(summary = "Cria uma transação", description = "Cria uma nova transação no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação criada"),
            @ApiResponse(responseCode = "403", description = "Erro na requisição ou Usuário não autorizado"),
            @ApiResponse(responseCode = "400", description = "Categoria não encontrada ou erro na requisição")
    })
    @PostMapping("/{categoryId}")
    public ResponseEntity<ResponseTransactionDTO> createTransaction(
            @RequestBody @Valid RequestTransactionDTO requestTransactionDTO,
            @PathVariable final String categoryId
    ) {
        Optional<Category> transactionCategory = categoryService.getEntityCategoryById(categoryId);

        if(transactionCategory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ResponseTransactionDTO newTransaction =  transactionService.createTransaction(
                requestTransactionDTO,
                transactionCategory.get()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newTransaction);
    }

    @Operation(summary = "Lista todas as transações", description = "Retorna todas as transações cadastradas no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transações listadas"),
            @ApiResponse(responseCode = "403", description = "Erro na requisição ou Usuário não autorizado"),
    })
    @GetMapping()
    public List<ResponseTransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @Operation(summary = "Busca uma transação por ID", description = "Retorna uma transação específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação encontrada"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Erro na requisição ou Usuário não autorizado")
    })
    @GetMapping("/{transactionId}")
    public ResponseEntity<ResponseTransactionDTO> getTransactionById(@PathVariable String transactionId) {
        Optional<ResponseTransactionDTO> transaction = transactionService.getTransactionById(transactionId);
        return transaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualiza uma transação", description = "Atualiza uma transação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação atualizada"),
            @ApiResponse(responseCode = "404 ", description = "Transação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Erro na requisição ou Usuário não autorizado")
    })
    @PutMapping("/{transactionId}")
    public ResponseEntity<ResponseTransactionDTO> updateTransaction(@PathVariable String transactionId, @RequestBody @Valid RequestTransactionDTO requestTransactionDTO) {
        Optional<ResponseTransactionDTO> updatedTransaction = transactionService.updateTransaction(transactionId, requestTransactionDTO);
        if (updatedTransaction.isPresent()) {
            return ResponseEntity.ok(updatedTransaction.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Deleta uma transação", description = "Deleta uma transação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transação deletada"),
            @ApiResponse(responseCode = "404 ", description = "Transação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Erro na requisição ou Usuário não autorizado")
    })
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable String transactionId) {
        return transactionService.deleteTransaction(transactionId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }


}

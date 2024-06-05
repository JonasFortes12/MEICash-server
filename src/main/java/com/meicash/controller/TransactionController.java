package com.meicash.controller;

import com.meicash.domain.transaction.RequestTransactionDTO;
import com.meicash.domain.transaction.ResponseTransactionDTO;
import com.meicash.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping()
    public ResponseEntity<ResponseTransactionDTO> createTransaction(@RequestBody @Valid RequestTransactionDTO requestTransactionDTO) {
        ResponseTransactionDTO createdTransaction = transactionService.createTransaction(requestTransactionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }

    @GetMapping()
    public List<ResponseTransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<ResponseTransactionDTO> getTransactionById(@PathVariable String transactionId) {
        Optional<ResponseTransactionDTO> transaction = transactionService.getTransactionById(transactionId);
        return transaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<ResponseTransactionDTO> updateTransaction(@PathVariable String transactionId, @RequestBody @Valid RequestTransactionDTO requestTransactionDTO) {
        Optional<ResponseTransactionDTO> updatedTransaction = transactionService.updateTransaction(transactionId, requestTransactionDTO);
        if (updatedTransaction.isPresent()) {
            return ResponseEntity.ok(updatedTransaction.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable String transactionId) {
        return transactionService.deleteTransaction(transactionId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }


}

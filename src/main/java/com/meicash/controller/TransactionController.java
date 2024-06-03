package com.meicash.controller;

import com.meicash.domain.transaction.RequestTransactionDTO;
import com.meicash.domain.transaction.ResponseTransactionDTO;
import com.meicash.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}

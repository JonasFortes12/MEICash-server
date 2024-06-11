package com.meicash.service;

import com.meicash.domain.category.Category;
import com.meicash.domain.transaction.RequestTransactionDTO;
import com.meicash.domain.transaction.ResponseTransactionDTO;
import com.meicash.domain.transaction.Transaction;
import com.meicash.domain.transaction.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private final TransactionRepository transactionRepository;
    private final AuthorizationService authorizationService;

    public  ProfileService(TransactionRepository transactionRepository , AuthorizationService authorizationService) {
        this.transactionRepository = transactionRepository;
        this.authorizationService = authorizationService;
    }

    private ResponseTransactionDTO transactionToResponseTransactionDTO(Transaction transaction) {
        return new ResponseTransactionDTO(
                transaction.getId(),
                transaction.getTimestamp(),
                transaction.getType(),
                transaction.getCategory().getName(),
                transaction.getCategory().getColor(),
                transaction.getValue(),
                transaction.getDescription()
        );
    }

    public ResponseTransactionDTO userCreateTransaction(RequestTransactionDTO requestTransactionDTO, Category category) {
        Transaction newTransaction = new Transaction(requestTransactionDTO, category);

        newTransaction.setUser(authorizationService.getAuthenticatedUser());

        return transactionToResponseTransactionDTO(transactionRepository.save(newTransaction));
    }

    public List<ResponseTransactionDTO> getUserTransactions() {
        return transactionRepository.findAllByUser(authorizationService.getAuthenticatedUser())
                .stream()
                .map(this::transactionToResponseTransactionDTO)
                .collect(Collectors.toList());
    }
}

package com.meicash.service;

import com.meicash.domain.transaction.RequestTransactionDTO;
import com.meicash.domain.transaction.ResponseTransactionDTO;
import com.meicash.domain.transaction.Transaction;
import com.meicash.domain.transaction.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    private ResponseTransactionDTO transactionToResponseTransactionDTO(Transaction transaction) {
        return new ResponseTransactionDTO(
                transaction.getId(),
                transaction.getTimestamp(),
                transaction.getType(),
                transaction.getCategory(),
                transaction.getValue(),
                transaction.getDescription()
        );
    }

    public ResponseTransactionDTO createTransaction(RequestTransactionDTO requestTransactionDTO) {
        Transaction newTransaction = new Transaction(requestTransactionDTO);
        return transactionToResponseTransactionDTO(transactionRepository.save(newTransaction));
    }

    public List<ResponseTransactionDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream()
                .map(transaction -> transactionToResponseTransactionDTO(transaction))
                .collect(Collectors.toList());
    }

    public Optional<ResponseTransactionDTO> getTransactionById(String transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if (transaction.isPresent()) {
            return Optional.of(transactionToResponseTransactionDTO(transaction.get()));
        } else {
            return Optional.empty();
        }
    }
}

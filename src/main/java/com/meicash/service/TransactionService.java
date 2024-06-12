package com.meicash.service;

import com.meicash.domain.category.Category;
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
                transaction.getTitle(),
                transaction.getTimestamp(),
                transaction.getType(),
                transaction.getCategory().getName(),
                transaction.getCategory().getColor(),
                transaction.getValue(),
                transaction.getDescription()
        );
    }

    public ResponseTransactionDTO createTransaction(RequestTransactionDTO requestTransactionDTO, Category category) {
        Transaction newTransaction = new Transaction(requestTransactionDTO, category);
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

    public Optional<ResponseTransactionDTO> updateTransaction(String transactionId, RequestTransactionDTO requestTransactionDTO) {
        return transactionRepository.findById(transactionId).map(
                transaction -> {
                    transaction.setTitle(requestTransactionDTO.title());
                    transaction.setTimestamp(requestTransactionDTO.timestamp());
                    transaction.setType(requestTransactionDTO.type());
                    transaction.setValue(requestTransactionDTO.value());
                    transaction.setDescription(requestTransactionDTO.description());
                    return transactionToResponseTransactionDTO(transactionRepository.save(transaction));
                }
        );
    }

    public boolean deleteTransaction(String transactionId) {
        return transactionRepository.findById(transactionId)
                .map(transaction -> {
                    transactionRepository.delete(transaction);
                    return true;
                })
                .orElse(false);
    }
}

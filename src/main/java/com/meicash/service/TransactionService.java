package com.meicash.service;

import com.meicash.domain.transaction.RequestTransactionDTO;
import com.meicash.domain.transaction.ResponseTransactionDTO;
import com.meicash.domain.transaction.Transaction;
import com.meicash.domain.transaction.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public ResponseTransactionDTO createTransaction(RequestTransactionDTO requestTransactionDTO) {
        Transaction newTransaction = new Transaction(requestTransactionDTO);

        transactionRepository.save(newTransaction);

        return new ResponseTransactionDTO(
                newTransaction.getTimestamp(),
                newTransaction.getType(),
                newTransaction.getCategory(),
                newTransaction.getValue(),
                newTransaction.getDescription()
        );
    }
}

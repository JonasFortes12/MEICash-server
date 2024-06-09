package com.meicash.domain.transaction;

import com.meicash.domain.category.Category;

import java.time.LocalDateTime;

public record ResponseTransactionDTO(

    String id,
    LocalDateTime timestamp,
    TransactionType type,
    String category,
    double value,
    String description
) {
    public static ResponseTransactionDTO fromTransaction(final Transaction transaction) {
        return new ResponseTransactionDTO(
            transaction.getId(),
            transaction.getTimestamp(),
            transaction.getType(),
            transaction.getCategory(),
            transaction.getValue(),
            transaction.getDescription()
        );
    }
}

package com.meicash.domain.transaction;

import java.time.LocalDateTime;

public record ResponseTransactionDTO(
    LocalDateTime timestamp,
    TransactionType type,
    String category,
    double value,
    String description
) {
    public static ResponseTransactionDTO fromTransaction(final Transaction transaction) {
        return new ResponseTransactionDTO(
            transaction.getTimestamp(),
            transaction.getType(),
            transaction.getCategory(),
            transaction.getValue(),
            transaction.getDescription()
        );
    }
}

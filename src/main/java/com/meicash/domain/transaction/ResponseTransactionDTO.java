package com.meicash.domain.transaction;

import java.time.LocalDateTime;

public record ResponseTransactionDTO(
    String id,
    String title,
    LocalDateTime timestamp,
    TransactionType type,
    String categoryName,
    String categoryColor,
    double value,
    String description
) {
    public static ResponseTransactionDTO fromTransaction(final Transaction transaction) {
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
}

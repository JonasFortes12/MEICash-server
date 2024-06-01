package com.meicash.domain.transaction;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public record RequestTransactionDTO(
        @NotBlank(message = "Timestamp is required")
        LocalDateTime timestamp,
        @NotBlank(message = "Transaction type is required")
        TransactionType type,
        @NotBlank(message = "Category is required")
        String category,
        @NotBlank(message = "Value is required")
        @PositiveOrZero(message = "Value must be positive or zero")
        double value,
        @NotBlank(message = "Description is required")
        String description
) {

}

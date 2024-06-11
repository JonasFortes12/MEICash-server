package com.meicash.domain.transaction;



import com.meicash.domain.category.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

public record RequestTransactionDTO(
        @NotNull(message = "Title is required")
        String title,
        @NotNull(message = "Timestamp is required")
        LocalDateTime timestamp,
        @NotNull(message = "Transaction type is required")
        TransactionType type,
        @NotNull(message = "Value is required")
        @PositiveOrZero(message = "Value must be positive or zero")
        double value,
        @NotBlank(message = "Description is required")
        String description
) {

}

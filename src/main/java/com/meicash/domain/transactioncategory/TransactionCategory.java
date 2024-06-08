package com.meicash.domain.transactioncategory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "transaction_category")
@Entity(name = "transaction_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TransactionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String transactionId;
    private String categoryId;
}

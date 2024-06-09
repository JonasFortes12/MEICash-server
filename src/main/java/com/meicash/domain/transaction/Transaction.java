package com.meicash.domain.transaction;

import com.meicash.domain.category.Category;
import com.meicash.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "transactions")
@Entity(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private LocalDateTime timestamp;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private double value;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;



    public Transaction(RequestTransactionDTO requestTransactionDTO) {
        this.timestamp = requestTransactionDTO.timestamp();
        this.type = requestTransactionDTO.type();
        this.category = requestTransactionDTO.category();
        this.value = requestTransactionDTO.value();
        this.description = requestTransactionDTO.description();
    }
}

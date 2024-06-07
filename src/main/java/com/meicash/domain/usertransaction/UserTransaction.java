package com.meicash.domain.usertransaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "user_transaction")
@Entity(name = "user_transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String id_transaction;
    private String id_user;
}

package com.meicash.domain.user;

import com.meicash.domain.transaction.Transaction;
import com.meicash.domain.usertransaction.UserTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "users")
@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String companyName;
    @OneToMany
    private List<Transaction> transactions;

    public User(final RequestUserDTO requestUserDTO) {
        this.email = requestUserDTO.email();
        this.username = requestUserDTO.username();
        this.password = requestUserDTO.password();
        this.firstName = requestUserDTO.firstName();
        this.lastName = requestUserDTO.lastName();
        this.companyName = requestUserDTO.companyName();
    }
}

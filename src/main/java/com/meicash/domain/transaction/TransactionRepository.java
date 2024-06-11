package com.meicash.domain.transaction;

import com.meicash.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    Collection<Transaction> findAllByUser(User authenticatedUser);
}

package com.meicash.domain.transactioncategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, String> {
}

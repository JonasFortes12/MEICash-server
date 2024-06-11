package com.meicash.domain.category;

import com.meicash.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Collection<Category> findAllByUser(User user);
}

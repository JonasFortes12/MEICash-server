package com.meicash.service;

import com.meicash.domain.category.Category;
import com.meicash.domain.category.CategoryRepository;
import com.meicash.domain.category.RequestCategoryDTO;
import com.meicash.domain.category.ResponseCategoryDTO;
import com.meicash.domain.transaction.RequestTransactionDTO;
import com.meicash.domain.transaction.ResponseTransactionDTO;
import com.meicash.domain.transaction.Transaction;
import com.meicash.domain.transaction.TransactionRepository;
import com.meicash.domain.user.ResponseUserDTO;
import com.meicash.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private final TransactionRepository transactionRepository;
    private  final CategoryRepository categoryRepository;
    private final AuthorizationService authorizationService;

    public  ProfileService(
            TransactionRepository transactionRepository,
            AuthorizationService authorizationService,
            CategoryRepository categoryRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.authorizationService = authorizationService;
        this.categoryRepository = categoryRepository;
    }

    private ResponseCategoryDTO categoryToResponseCategoryDTO(Category category){
        return new ResponseCategoryDTO(
                category.getId(),
                category.getName(),
                category.getColor()
        );
    }

    private ResponseTransactionDTO transactionToResponseTransactionDTO(Transaction transaction) {
        return new ResponseTransactionDTO(
                transaction.getId(),
                transaction.getTimestamp(),
                transaction.getType(),
                transaction.getCategory().getName(),
                transaction.getCategory().getColor(),
                transaction.getValue(),
                transaction.getDescription()
        );
    }

    private ResponseUserDTO userToResponseUserDTO(User user) {
        return new ResponseUserDTO(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getCompanyName()
        );
    }

    public ResponseTransactionDTO userCreateTransaction(RequestTransactionDTO requestTransactionDTO, Category category) {
        Transaction newTransaction = new Transaction(requestTransactionDTO, category);

        newTransaction.setUser(authorizationService.getAuthenticatedUser());

        return transactionToResponseTransactionDTO(transactionRepository.save(newTransaction));
    }

    public List<ResponseTransactionDTO> getUserTransactions() {
        return transactionRepository.findAllByUser(authorizationService.getAuthenticatedUser())
                .stream()
                .map(this::transactionToResponseTransactionDTO)
                .collect(Collectors.toList());
    }

    public ResponseCategoryDTO userCreateCategory(RequestCategoryDTO requestCategoryDTO) {
        Category newCategory = new Category(requestCategoryDTO);

        newCategory.setUser(authorizationService.getAuthenticatedUser());

        return categoryToResponseCategoryDTO(categoryRepository.save(newCategory));
    }

    public List<ResponseCategoryDTO> getUserCategories() {
        return categoryRepository.findAllByUser(authorizationService.getAuthenticatedUser())
                .stream()
                .map(this::categoryToResponseCategoryDTO)
                .collect(Collectors.toList());
    }

    public ResponseUserDTO getUserProfile() {
        return userToResponseUserDTO(authorizationService.getAuthenticatedUser());
    }

}

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
import java.util.Optional;
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
                transaction.getTitle(),
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

    public Optional<ResponseTransactionDTO> updateUserTransaction(String transactionId, RequestTransactionDTO requestTransactionDTO) {
        return transactionRepository.findById(transactionId).map(
                transaction -> {
                    transaction.setTitle(requestTransactionDTO.title());
                    transaction.setTimestamp(requestTransactionDTO.timestamp());
                    transaction.setType(requestTransactionDTO.type());
                    transaction.setValue(requestTransactionDTO.value());
                    transaction.setDescription(requestTransactionDTO.description());
                    return transactionToResponseTransactionDTO(transactionRepository.save(transaction));
                }
        );
    }

    public  boolean deleteUserTransaction(String transactionId) {
        return transactionRepository.findById(transactionId)
                .map(transaction -> {
                    transactionRepository.delete(transaction);
                    return true;
                })
                .orElse(false);
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

    public ResponseCategoryDTO updateUserCategory(Category category, RequestCategoryDTO requestCategoryDTO) {
        category.setName(requestCategoryDTO.name());
        category.setColor(requestCategoryDTO.color());
        return categoryToResponseCategoryDTO(categoryRepository.save(category));
    }

    public boolean deleteUserCategory(Category categoryToDelete) {
        try {
            categoryRepository.delete(categoryToDelete);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ResponseUserDTO getUserProfile() {
        return userToResponseUserDTO(authorizationService.getAuthenticatedUser());
    }

}

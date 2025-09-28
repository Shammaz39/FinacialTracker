package com.example.fintrack.service;

import com.example.fintrack.entity.Category;
import com.example.fintrack.entity.Transaction;
import com.example.fintrack.repository.CategoryRepository;
import com.example.fintrack.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // ✅ No need for UserRepository anymore
    // private UserRepository userRepository;

    public Transaction addTransaction(Transaction transaction, Long userId, String categoryName) {
        // ✅ category lookup
        Category category = categoryRepository.findByName(categoryName) // name is PK
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // ✅ Security check: make sure category belongs to this user
        if (!category.getUserId().equals(userId)) {
            throw new RuntimeException("Access denied: category does not belong to this user");
        }

        // ✅ Directly set userId instead of user entity
        transaction.setUserId(userId);
        transaction.setCategory(category);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    public List<Transaction> getTransactionCategory(Long userId, String categoryName) {
        Category category = categoryRepository.findByName(categoryName) // name is PK
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // ✅ Security check: make sure category belongs to this user
        if (!category.getUserId().equals(userId)) {
            throw new RuntimeException("Access denied: category does not belong to this user");
        }

        return transactionRepository.findByUserIdAndCategory_Name(userId, categoryName);
    }
}

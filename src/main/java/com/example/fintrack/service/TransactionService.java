package com.example.fintrack.service;

import com.example.fintrack.entity.Category;
import com.example.fintrack.entity.Transaction;
import com.example.fintrack.entity.User;
import com.example.fintrack.repository.CategoryRepository;
import com.example.fintrack.repository.TransactionRepository;
import com.example.fintrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Transaction addTransaction(Transaction transaction, Long userId, Long categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        transaction.setUser(user);
        transaction.setCategory(category);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(Long userId) {
        return transactionRepository.findByUserId(userId);
    }
}

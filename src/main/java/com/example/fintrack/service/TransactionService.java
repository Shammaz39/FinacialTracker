package com.example.fintrack.service;

import com.example.fintrack.entity.Category;
import com.example.fintrack.entity.Transaction;
import com.example.fintrack.entity.TransactionType;
import com.example.fintrack.repository.CategoryRepository;
import com.example.fintrack.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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


    // ✅ New service: filter by date range
    public List<Transaction> getTransactionsByDateRange(Long userId, String start, String end) {
        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(start);
            endDate = LocalDate.parse(end);
        } catch(Exception e) {
            System.out.println("Exception : " + e);
            throw new RuntimeException("Wrong Format of Date");
        }
        return transactionRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }

    // ✅ New service: filter by type (INCOME / EXPENSE)
    public List<Transaction> getTransactionsByType(Long userId, TransactionType type) {
        return transactionRepository.findByUserIdAndType(userId, type);
    }


    public List<Transaction> getTransactionsWithFilters(
            Long userId,
            String categoryName,
            TransactionType type,
            String startDate,
            String endDate
    ) {
        // Start with all user transactions
        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        // Apply filters
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            transactions = transactions.stream()
                    .filter(t -> {
                        String transactionCategory = (t.getCategory() != null) ? t.getCategory().getName() : null;
                        return categoryName.equals(transactionCategory);
                    })
                    .collect(Collectors.toList());
        }

        if (type != null) {
            transactions = transactions.stream()
                    .filter(t -> t.getType() == type)
                    .collect(Collectors.toList());
        }

        if (startDate != null && endDate != null) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            transactions = transactions.stream()
                    .filter(t -> {
                        LocalDate transactionDate = t.getDate();
                        return !transactionDate.isBefore(start) && !transactionDate.isAfter(end);
                    })
                    .collect(Collectors.toList());
        }
        return transactions;
    }

}
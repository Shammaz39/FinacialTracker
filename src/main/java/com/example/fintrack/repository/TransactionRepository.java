package com.example.fintrack.repository;

import com.example.fintrack.entity.Transaction;
import com.example.fintrack.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);

    List<Transaction> findByUserIdAndType(Long userId, TransactionType type);

    Transaction save(Transaction transaction);

    // ✅ Get all transactions for a user in a specific category
    List<Transaction> findByUserIdAndCategory_Name(Long userId, String categoryName);

}

package com.example.fintrack.controller;

import com.example.fintrack.entity.Transaction;
import com.example.fintrack.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/add")
    public ResponseEntity<Transaction> addTransaction(
            @RequestBody Transaction transaction,
            @RequestParam Long categoryId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        Transaction saved = transactionService.addTransaction(transaction, userId, categoryId);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getTransactions(HttpServletRequest request)  {
        List<Transaction> list = transactionService.getTransactions((Long) request.getAttribute("id"));
        return ResponseEntity.ok(list);
    }
}

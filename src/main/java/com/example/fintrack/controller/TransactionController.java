package com.example.fintrack.controller;

import com.example.fintrack.entity.Transaction;
import com.example.fintrack.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@SecurityRequirement(name = "bearerAuth") // ✅ tells Swagger this endpoint requires JWT
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/add")
    @Operation(summary = "Add Transaction", description = "Add Transaction")
    public ResponseEntity<Transaction> addTransaction(
            @RequestBody Transaction transaction,
            @RequestParam String categoryName,   // use name instead of id
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        Transaction saved = transactionService.addTransaction(transaction, userId, categoryName);
        return ResponseEntity.ok(saved);
    }


    @GetMapping("/all")
    @Operation(summary = "Get Transaction", description = "Fetch All Transaction")
    public ResponseEntity<List<Transaction>> getTransactions(HttpServletRequest request) {
        List<Transaction> list = transactionService.getTransactions((Long) request.getAttribute("id"));
        return ResponseEntity.ok(list);
    }

    @GetMapping("/category")
    @Operation(summary = "Get Transaction as per Category", description = "Fetch All Transaction")
    public ResponseEntity<List<Transaction>> addTransaction(
            @RequestParam String categoryName,   // use name instead of id
            HttpServletRequest request
    ) {
        List<Transaction> list = transactionService.getTransactionCategory((Long) request.getAttribute("id"), categoryName);
        return ResponseEntity.ok(list);
    }
}

package com.example.fintrack.controller;

import com.example.fintrack.entity.Transaction;
import com.example.fintrack.entity.TransactionType;
import com.example.fintrack.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    // ✅ 2. Transactions by Date Range
    @GetMapping("/byDate")
    @Operation(summary = "Get Transactions by Date Range", description = "Fetch transactions within a date range")
    public ResponseEntity<List<Transaction>> getTransactionsByDate(
            @RequestParam String start,
            @RequestParam String end,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        List<Transaction> list = transactionService.getTransactionsByDateRange(userId, start, end);
        return ResponseEntity.ok(list);
    }

    // ✅ 3. Transactions by Type (INCOME / EXPENSE)
    @GetMapping("/byType")
    @Operation(summary = "Get Transactions by Type", description = "Fetch transactions by type (INCOME or EXPENSE)")
    public ResponseEntity<List<Transaction>> getTransactionsByType(
            @RequestParam TransactionType type,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        List<Transaction> list = transactionService.getTransactionsByType(userId, type);
        return ResponseEntity.ok(list);
    }


    @GetMapping("/filter")
    @Operation(summary = "Get Transactions with Combined Filters", description = "Fetch transactions with multiple filters applied")
    public ResponseEntity<Map<String, Object>> getTransactionsWithFilters(
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        Map<String, Object> response = transactionService.getTransactionsWithFiltersAndTotals(
                userId, categoryName, type, startDate, endDate
        );
        return ResponseEntity.ok(response);
    }

}

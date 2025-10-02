package com.example.fintrack.controller;

import com.example.fintrack.entity.Transaction;
import com.example.fintrack.entity.TransactionType;
import com.example.fintrack.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final TransactionService transactionService;

    // Endpoint 1: Get total income, expenses, balance (all time)
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Double>> getSummary(
            @RequestParam(required = false) String month,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");

        List<Transaction> transactions;

        if (month != null) {
            // Parse month like "2025-09"
            YearMonth ym = YearMonth.parse(month);
            LocalDate start = ym.atDay(1);
            LocalDate end = ym.atEndOfMonth();

            String startS = start.toString();
            String endS = end.toString();
            transactions = transactionService.getTransactionsByDateRange(userId, startS, endS);
        } else {
            transactions = transactionService.getTransactions(userId);
        }

        double totalIncome = transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpense = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double balance = totalIncome - totalExpense;

        return ResponseEntity.ok(Map.of(
                "totalIncome", totalIncome,
                "totalExpense", totalExpense,
                "balance", balance
        ));
    }
}





package com.example.fintrack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String title;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type; // INCOME or EXPENSE

    private LocalDate date;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;
}
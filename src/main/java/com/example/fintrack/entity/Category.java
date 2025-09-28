package com.example.fintrack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @Column(nullable = false, unique = true)
    private String name; // primary key instead of id

    private String description;

    // Just store userId instead of full User entity
    private Long userId;
}

package com.example.fintrack.repository;

import com.example.fintrack.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findByUserId(Long userId);

    Optional<Category> findByName(String categoryName);

    void deleteByName(String name);

    Optional<Category> findByNameAndUserId(String name, Long userId);

}

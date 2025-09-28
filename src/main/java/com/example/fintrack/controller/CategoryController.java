package com.example.fintrack.controller;

import com.example.fintrack.entity.Category;
import com.example.fintrack.repository.CategoryRepository;
import com.example.fintrack.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category", description = "APIs for user defined Category")
@SecurityRequirement(name = "bearerAuth") // ✅ tells Swagger this endpoint requires JWT
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Get all categories for current user
    @GetMapping("/all")
    @Operation(summary = "All Categories", description = "Get All Categories")
    public ResponseEntity<List<Category>> getAllCategories(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(categoryRepository.findByUserId(userId));
    }

    // ✅ Add new category for current user
    @PostMapping("/add")
    @Operation(summary = "Add Categories", description = "Add Categories")
    public ResponseEntity<Category> addCategory(@RequestBody Category category,
                                                HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        category.setUserId(userId); // bind category to logged-in user
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @GetMapping("/{name}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String name, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");

        Category category = categoryRepository.findByNameAndUserId(name, userId)
                .orElseThrow(() -> new RuntimeException("Access denied or category not found"));

        return ResponseEntity.ok(category);
    }


    @PutMapping("/{name}")
    public ResponseEntity<Category> updateCategory(@PathVariable String name,
                                                   @RequestBody Category updatedCategory,
                                                   HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");

        Category existing = categoryRepository.findByNameAndUserId(name, userId)
                .orElseThrow(() -> new RuntimeException("Access denied or category not found"));

        existing.setDescription(updatedCategory.getDescription());
        Category saved = categoryRepository.save(existing);
        return ResponseEntity.ok(saved);
    }


    // ✅ Delete category (only if owned by user)
    @DeleteMapping("/{name}")
    @Operation(summary = "Delete Category", description = "Delete Category by name")
    public ResponseEntity<?> deleteCategory(@PathVariable String name, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");

        return categoryRepository.findByNameAndUserId(name, userId)
                .map(category -> {
                    categoryRepository.delete(category); // delete entity directly
                    return ResponseEntity.ok("Category deleted successfully");
                })
                .orElse(ResponseEntity.status(403).body("Access denied or category not found"));
    }
}

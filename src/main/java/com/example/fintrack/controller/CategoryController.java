package com.example.fintrack.controller;

import com.example.fintrack.entity.Category;
import com.example.fintrack.entity.User;
import com.example.fintrack.repository.CategoryRepository;
import com.example.fintrack.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Get all categories for current user
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(categoryRepository.findByUserId(userId));
    }

    // ✅ Add new category for current user
    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category,
                                                HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        User user = userRepository.findById(userId).orElseThrow();
        category.setUser(user); // bind category to logged-in user
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    // ✅ Get single category by id (only if owned by user)
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent() && category.get().getUser().getId().equals(userId)) {
            return ResponseEntity.ok(category.get());
        }
        return ResponseEntity.status(403).body("Access denied");
    }

    // ✅ Update category (only if owned by user)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id,
                                            @RequestBody Category updatedCategory,
                                            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");

        return categoryRepository.findById(id)
                .map(existing -> {
                    if (!existing.getUser().getId().equals(userId)) {
                        return ResponseEntity.status(403).body("Access denied");
                    }
                    existing.setName(updatedCategory.getName());
                    existing.setDescription(updatedCategory.getDescription());
                    return ResponseEntity.ok(categoryRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete category (only if owned by user)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");

        return categoryRepository.findById(id)
                .map(category -> {
                    if (!category.getUser().getId().equals(userId)) {
                        return ResponseEntity.status(403).body("Access denied");
                    }
                    categoryRepository.deleteById(id);
                    return ResponseEntity.ok("Category deleted successfully");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

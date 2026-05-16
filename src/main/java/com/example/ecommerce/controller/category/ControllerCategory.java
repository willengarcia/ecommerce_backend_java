package com.example.ecommerce.controller.category;

import com.example.ecommerce.model.category.Category;
import com.example.ecommerce.service.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class ControllerCategory {
    private CategoryService categoryService;
    public ControllerCategory(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> criarCategoria(@RequestBody Category category) {

        Category novaCategoria = categoryService.criarCategoria(category);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novaCategoria);
    }
}

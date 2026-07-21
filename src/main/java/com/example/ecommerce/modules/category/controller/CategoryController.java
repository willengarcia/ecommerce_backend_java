package com.example.ecommerce.modules.category.controller;

import com.example.ecommerce.modules.category.dto.CategoryCreateDTO;
import com.example.ecommerce.modules.category.dto.CategoryListDTO;
import com.example.ecommerce.modules.category.model.Category;
import com.example.ecommerce.modules.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> criarCategoria(@Valid @RequestBody CategoryCreateDTO category) {
        CategoryCreateDTO novaCategoria = categoryService.criarCategoria(category);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(novaCategoria);
    }

    @GetMapping
    public List<CategoryListDTO> listarCategorias() {
        return categoryService.listarCategorias();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarCategoria(@PathVariable Long id, @Valid @RequestBody CategoryCreateDTO category) {
        CategoryCreateDTO novaCategoria = categoryService.atualizarCategoria(id, category);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(novaCategoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCategoria(@PathVariable Long id) {
        categoryService.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}

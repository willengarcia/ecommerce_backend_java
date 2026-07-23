package com.example.ecommerce.modules.category.controller;

import com.example.ecommerce.modules.category.dto.CategoryCreateDTO;
import com.example.ecommerce.modules.category.dto.CategoryListDTO;
import com.example.ecommerce.modules.category.model.Category;
import com.example.ecommerce.modules.category.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
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

    @PatchMapping("/{idCategory}")
    public ResponseEntity<?> atualizarCategoria(@Positive(message = "O ID do Category tem que ser maior que 0") @PathVariable Long idCategory, @Valid @RequestBody CategoryCreateDTO category) {
        CategoryCreateDTO novaCategoria = categoryService.atualizarCategoria(idCategory, category);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(novaCategoria);
    }

    @DeleteMapping("/{idCategory}")
    public ResponseEntity<?> deletarCategoria(@Positive(message = "O ID do Category tem que ser maior que 0") @PathVariable Long idCategory) {
        categoryService.deletarCategoria(idCategory);
        return ResponseEntity.noContent().build();
    }
}

package com.example.ecommerce.modules.category.controller;

import com.example.ecommerce.modules.category.dto.CategoryDTO;
import com.example.ecommerce.modules.category.model.Category;
import com.example.ecommerce.modules.category.service.CategoryService;
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
    public ResponseEntity<?> criarCategoria(@RequestBody Category category) {
            Category novaCategoria = categoryService.criarCategoria(category);
            CategoryDTO dto = new CategoryDTO(
                    novaCategoria.getId(),
                    novaCategoria.getName(),
                    novaCategoria.getDescription(),
                    novaCategoria.isAtivo(),
                    novaCategoria.getDataAtualizacao()
            );
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(dto);
    }

    @GetMapping
    public List<CategoryDTO> listarCategorias() {
        return categoryService.listarCategorias();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarCategoria(@PathVariable Long id, @RequestBody CategoryDTO category) {
        Category novaCategoria = categoryService.atualizarCategoria(id, category);
        CategoryDTO dto = new CategoryDTO(
                novaCategoria.getId(),
                novaCategoria.getName(),
                novaCategoria.getDescription(),
                novaCategoria.isAtivo(),
                novaCategoria.getDataAtualizacao()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCategoria(@PathVariable Long id) {
        categoryService.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}

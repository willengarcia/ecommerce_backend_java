package com.example.ecommerce.controller.category;

import com.example.ecommerce.dto.category.CategoryDTO;
import com.example.ecommerce.model.category.Category;
import com.example.ecommerce.service.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class ControllerCategory {
    private final CategoryService categoryService;
    public ControllerCategory(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> criarCategoria(@RequestBody Category category) {
        try {
            Category novaCategoria = categoryService.criarCategoria(category);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(novaCategoria);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public List<CategoryDTO> listarCategorias() {
        return categoryService.listarCategorias();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> atualizarCategoria(@PathVariable Long id, @RequestBody Category category) {
        Category novaCategoria = categoryService.atualizarCategoria(id, category);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(novaCategoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deletarCategoria(@PathVariable Long id) {
        categoryService.deletarCategoria(id);
        return ResponseEntity
                .status(HttpStatus.OK).body(null);
    }
}

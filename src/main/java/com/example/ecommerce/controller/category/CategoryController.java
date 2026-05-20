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
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> criarCategoria(@RequestBody Category category) {
        try {
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
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public List<CategoryDTO> listarCategorias() {
        return categoryService.listarCategorias();
    }

    @PutMapping("/{id}")
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
    public ResponseEntity<Category> deletarCategoria(@PathVariable Long id) {
        categoryService.deletarCategoria(id);
        return ResponseEntity
                .status(HttpStatus.OK).body(null);
    }
}

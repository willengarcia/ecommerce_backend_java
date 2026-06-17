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
            return ResponseEntity.badRequest().body("É obrigatório informar todas as Tag no Body via JSON, no modelo:\n" +
                    "{\n" +
                    "  \"name\": \"joalheria\",\n" +
                    "  \"description\": \"Produtos de bijuterias\",\n" +
                    "  \"ativo\": true\n" +
                    "}");
        }
    }

    @GetMapping
    public List<CategoryDTO> listarCategorias() {
        return categoryService.listarCategorias();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCategoria(@PathVariable Long id, @RequestBody CategoryDTO category) {
        try {
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
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCategoria(@PathVariable Long id) {
        try {
            categoryService.deletarCategoria(id);
            return ResponseEntity
                    .status(HttpStatus.OK).body(null);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}

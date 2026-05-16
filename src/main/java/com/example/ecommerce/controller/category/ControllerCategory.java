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
    private CategoryService categoryService;
    public ControllerCategory(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> criarCategoria(@RequestBody Category category) {
        try {
            Category novaCategoria = categoryService.criarCategoria(category);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(novaCategoria);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping
    public List<CategoryDTO> listarCategorias() {
        return categoryService.listarCategorias();
    }
}

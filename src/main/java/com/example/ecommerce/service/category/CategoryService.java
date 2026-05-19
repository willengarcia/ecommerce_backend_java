package com.example.ecommerce.service.category;

import com.example.ecommerce.dto.category.CategoryDTO;
import com.example.ecommerce.model.category.Category;
import com.example.ecommerce.repository.category.RepositoryCategory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final RepositoryCategory categoryRepository;

    public CategoryService(RepositoryCategory categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category criarCategoria(Category category) {
        if (category.getName().isEmpty()) {
            throw new RuntimeException("É obrigatório informar o Nome do Categoria");
        } else if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new RuntimeException("Nome de categoria existente");
        }else {
            return categoryRepository.save(category);
        }
    }

    public List<CategoryDTO> listarCategorias() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryDTO(category.getId(), category.getName(), category.getDescription(), category.isAtivo(), category.getDataAtualizacao()))
                .collect(Collectors.toList());
    }

    public Category buscarPorId(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    public Category atualizarCategoria(Long id, Category novaCategoria) {
        Category category = buscarPorId(id);

        category.setName(novaCategoria.getName());
        category.setDescription(novaCategoria.getDescription());
        category.setAtivo(novaCategoria.isAtivo());

        return categoryRepository.save(category);
    }

    public void deletarCategoria(Long id) {
        categoryRepository.deleteById(id);
    }
}
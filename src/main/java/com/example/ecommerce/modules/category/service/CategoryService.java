package com.example.ecommerce.modules.category.service;

import com.example.ecommerce.modules.category.dto.CategoryDTO;
import com.example.ecommerce.modules.category.model.Category;
import com.example.ecommerce.modules.category.repository.RepositoryCategory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Category atualizarCategoria(Long id, CategoryDTO novaCategoria) {
        Category category = buscarPorId(id);

        category.setName(novaCategoria.name());
        category.setDescription(novaCategoria.description());
        if (novaCategoria.ativo() == false || !categoryRepository.hasProducts(category.getId())) {
            throw new RuntimeException(
                    "Categoria não pode ficar inativa por ter produtos vinculados à ela."
            );
        }
        category.setAtivo(true);
        category.setDataAtualizacao(LocalDate.now());
        return categoryRepository.save(category);
    }

    public void deletarCategoria(Long id) {
        if (categoryRepository.hasProducts(id)){
            throw new RuntimeException(
                    "Categoria vinculada a um produto"
            );
        }
        categoryRepository.deleteById(id);
    }

}
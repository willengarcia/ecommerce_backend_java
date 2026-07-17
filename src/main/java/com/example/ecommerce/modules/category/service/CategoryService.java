package com.example.ecommerce.modules.category.service;

import com.example.ecommerce.modules.category.dto.CategoryDTO;
import com.example.ecommerce.modules.category.exceptions.CategoryException;
import com.example.ecommerce.modules.category.exceptions.CategoryNotFoundException;
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
        if (category.getName().isEmpty() || category.getName().isBlank()) {
            throw new CategoryException("É obrigatório informar o Nome do Categoria");
        } else if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new CategoryException("Nome de categoria existente");
        }else {
            return categoryRepository.save(category);
        }
    }

    public List<CategoryDTO> listarCategorias() {
        if (categoryRepository.count() == 0) {
            throw new CategoryException("Não há categorias registradas");
        }
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryDTO(category.getId(), category.getName(), category.getDescription(), category.isAtivo(), category.getDataAtualizacao()))
                .collect(Collectors.toList());
    }

    public Category buscarPorId(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada"));
    }

    public Category atualizarCategoria(Long id, CategoryDTO novaCategoria) {
        if (categoryRepository.existsByNameIgnoreCase(novaCategoria.name())) {
            throw new CategoryException("Nome de categoria existente");
        }
        Category category = buscarPorId(id);
        if (novaCategoria.name() != null && !novaCategoria.name().isBlank()) {
            category.setName(novaCategoria.name());
        }
        if (novaCategoria.description() != null && !novaCategoria.description().isBlank()) {
            category.setDescription(novaCategoria.description());
        }
        if (novaCategoria.ativo() == null) {
            category.setAtivo(true);
        } else if (!novaCategoria.ativo()) {
            if (categoryRepository.hasProducts(category.getId())){
                throw new CategoryException(
                        "Categoria não pode ficar inativa por ter produtos vinculados à ela."
                );
            }
            category.setAtivo(false);
        }
        category.setDataAtualizacao(LocalDate.now());
        return categoryRepository.save(category);
    }

    public void deletarCategoria(Long id) {
        if (categoryRepository.hasProducts(id)){
            throw new CategoryException(
                    "Categoria vinculada a um produto"
            );
        }
        categoryRepository.deleteById(id);
    }

}
package com.example.ecommerce.modules.category.service;

import com.example.ecommerce.modules.category.dto.CategoryCreateDTO;
import com.example.ecommerce.modules.category.dto.CategoryListDTO;
import com.example.ecommerce.modules.category.exceptions.*;
import com.example.ecommerce.modules.category.mapper.CategoryMapper;
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

    public CategoryCreateDTO criarCategoria(CategoryCreateDTO category) {
        if (categoryRepository.existsByNameIgnoreCase(category.name())) {
            throw new DuplicateCategoryException("Nome de categoria existente");
        }else {
            Category cate = categoryRepository.save(CategoryMapper.toCategoryCreateDTO(category));
            return CategoryMapper.toCategoryCreateDTO(cate);
        }
    }

    public List<CategoryListDTO> listarCategorias() {
        if (categoryRepository.count() == 0) {
            throw new CategoryNotFoundException("Não há categorias registradas");
        }
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryListDTO(category.getId(), category.getName(), category.getDescription(), category.isAtivo(), category.getDataAtualizacao()))
                .collect(Collectors.toList());
    }

    public Category buscarPorId(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada"));
    }

    public CategoryCreateDTO atualizarCategoria(Long id, CategoryCreateDTO novaCategoria) {
        if (categoryRepository.existsByNameIgnoreCase(novaCategoria.name())) {
            throw new DuplicateCategoryException("Nome de categoria existente");
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
                throw new InactiveCategoryException(
                        "Categoria não pode ficar inativa por ter produtos vinculados à ela."
                );
            }
            category.setAtivo(false);
        }
        category.setDataAtualizacao(LocalDate.now());
        Category cat = categoryRepository.save(category);
        return CategoryMapper.toCategoryCreateDTO(cat);
    }

    public void deletarCategoria(Long id) {
        if (categoryRepository.hasProducts(id)){
            throw new CategoryHasProductsException(
                    "Categoria vinculada a um produto"
            );
        }
        categoryRepository.deleteById(id);
    }

}
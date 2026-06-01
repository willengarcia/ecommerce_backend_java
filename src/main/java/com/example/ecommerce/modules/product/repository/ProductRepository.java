package com.example.ecommerce.modules.product.repository;

import com.example.ecommerce.modules.product.dto.ProductCreateDTO;
import com.example.ecommerce.modules.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<ProductCreateDTO> findByCategoriaId(Long categoriaId);

    List<ProductCreateDTO> findByNomeContainingIgnoreCase(String nome);

    List<ProductCreateDTO> findAllByOrderByPrecoAsc();
}

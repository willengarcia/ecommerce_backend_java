package com.example.ecommerce.repository.product;

import com.example.ecommerce.dto.product.ProductCreateDTO;
import com.example.ecommerce.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<ProductCreateDTO> findByCategoriaId(Long categoriaId);

    List<ProductCreateDTO> findByNomeContainingIgnoreCase(String nome);

    List<ProductCreateDTO> findAllByOrderByPrecoAsc();
}

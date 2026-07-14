package com.example.ecommerce.modules.product.repository;

import com.example.ecommerce.modules.product.dto.ProductCreateDTO;
import com.example.ecommerce.modules.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory_Id(Long categoriaId);

    List<Product> findByNomeContainingIgnoreCase(String nome);

    List<Product> findAllByOrderByPrecoAsc();

    Product findById(Long id);

    Product findBySkuContainingIgnoreCase(String sku);

    boolean existsBySku(String sku);

    boolean existsBySlug(String slug);
}

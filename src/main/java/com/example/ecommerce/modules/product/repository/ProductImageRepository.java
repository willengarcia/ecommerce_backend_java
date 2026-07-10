package com.example.ecommerce.modules.product.repository;

import com.example.ecommerce.modules.product.model.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImages, Long> {
    List<ProductImages> findByProductId(Long productId);

    Optional<ProductImages> findByProductIdAndImagemPrincipalTrue(
            Long productId
    );
}

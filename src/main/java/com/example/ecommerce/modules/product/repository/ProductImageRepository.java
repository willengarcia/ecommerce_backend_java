package com.example.ecommerce.modules.product.repository;

import com.example.ecommerce.modules.product.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long productId);

    Optional<ProductImage> findByProductIdAndImagemPrincipalTrue(
            Long productId
    );
}

package com.example.ecommerce.modules.product.dto;

import java.math.BigDecimal;

public record ProductResponseResumeDTO(
        Long id,
        String nome,
        String slug,
        String descricaoCurta,
        String descricao,
        BigDecimal preco,
        String sku
) {
}

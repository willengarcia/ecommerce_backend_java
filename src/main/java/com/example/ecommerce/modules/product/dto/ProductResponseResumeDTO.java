package com.example.ecommerce.modules.product.dto;

public record ProductResponseResumeDTO(
        Long id,
        String nome,
        String slug,
        String descricaoCurta,
        String descricao,
        float preco,
        String sku
) {
}

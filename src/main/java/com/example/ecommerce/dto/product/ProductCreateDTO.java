package com.example.ecommerce.dto.product;

public record ProductCreateDTO(
        String nome,
        String slug,
        String descricao_curta,
        String descricao,
        float preco,
        float preco_promocional,
        Integer quantidade_estoque,
        Integer estoque_minimo,
        String sku,
        float peso,
        float altura,
        float largura,
        float comprimento,
        boolean status
) {
}
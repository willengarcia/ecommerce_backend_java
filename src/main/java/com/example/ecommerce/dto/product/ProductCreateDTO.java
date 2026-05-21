package com.example.ecommerce.dto.product;

public record ProductCreateDTO(
        String nome,
        String slug,
        String descricaoCurta,
        String descricao,
        float preco,
        float precoPromocional,
        Integer quantidadeEstoque,
        Integer estoqueMinimo,
        String sku,
        float peso,
        float altura,
        float largura,
        float comprimento,
        boolean status,
        Long categoria_id
) {
}
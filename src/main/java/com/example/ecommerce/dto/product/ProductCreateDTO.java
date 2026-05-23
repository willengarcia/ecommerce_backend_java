package com.example.ecommerce.dto.product;

public record ProductCreateDTO(
        String nome,
        String slug,
        String descricaoCurta,
        String descricao,
        Float preco,
        Float precoPromocional,
        Integer quantidadeEstoque,
        Integer estoqueMinimo,
        String sku,
        Float peso,
        Float altura,
        Float largura,
        Float comprimento,
        Boolean status,
        Long categoria_id
) {
}
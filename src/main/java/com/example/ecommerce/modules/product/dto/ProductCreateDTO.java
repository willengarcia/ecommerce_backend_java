package com.example.ecommerce.modules.product.dto;

import com.example.ecommerce.modules.product.model.ProductEnum;

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
        ProductEnum status,
        Long categoria_id
) {
}
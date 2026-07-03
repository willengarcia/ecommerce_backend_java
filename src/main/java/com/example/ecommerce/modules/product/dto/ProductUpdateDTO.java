package com.example.ecommerce.modules.product.dto;

import com.example.ecommerce.modules.product.model.ProductEnum;

public record ProductUpdateDTO(
        String nome,
        String slug,
        String descricaoCurta,
        String descricao,
        Float preco,
        Float precoPromocional,
        Integer estoqueMinimo,
        Float peso,
        Float altura,
        Float largura,
        Float comprimento,
        ProductEnum status,
        Long categoriaId
) {
}
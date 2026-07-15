package com.example.ecommerce.modules.product.dto;

import com.example.ecommerce.modules.product.model.ProductEnum;

import java.math.BigDecimal;

public record ProductUpdateDTO(
        String nome,
        String slug,
        String descricaoCurta,
        String descricao,
        BigDecimal preco,
        BigDecimal precoPromocional,
        Integer estoqueMinimo,
        Float peso,
        Float altura,
        Float largura,
        Float comprimento,
        ProductEnum status,
        Long categoriaId
) {
}
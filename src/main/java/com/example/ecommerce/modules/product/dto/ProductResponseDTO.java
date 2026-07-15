package com.example.ecommerce.modules.product.dto;

import com.example.ecommerce.modules.product.model.ProductEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductResponseDTO(
        Long id,
        String nome,
        String slug,
        String descricaoCurta,
        String descricao,
        BigDecimal preco,
        BigDecimal precoPromocional,
        Integer quantidadeEstoque,
        Integer quantidadeReservada,
        Integer estoqueMinimo,
        String sku,
        Float peso,
        Float altura,
        Float largura,
        Float comprimento,
        Float mediaAvaliacao,
        Integer totalAvaliacoes,
        ProductEnum status,
        LocalDate dataCriacao,
        Long categoriaId
) {
}

package com.example.ecommerce.modules.product.dto;

import com.example.ecommerce.modules.product.model.ProductEnum;

import java.time.LocalDate;

public record ProductDTO(
        Long id,
        String nome,
        String slug,
        String descricaoCurta,
        String descricao,
        float preco,
        float precoPromocional,
        Integer quantidadeEstoque,
        Integer quantidadeReservada,
        Integer estoqueMinimo,
        String sku,
        float peso,
        float altura,
        float largura,
        float comprimento,
        float mediaAvaliacao,
        Integer totalAvaliacoes,
        ProductEnum status,
        LocalDate dataCriacao,
        Long categoria_id
) {
}

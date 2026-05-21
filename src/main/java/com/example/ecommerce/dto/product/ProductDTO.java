package com.example.ecommerce.dto.product;

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
        boolean status,
        LocalDate dataCriacao,
        Long categoria_id
) {
}

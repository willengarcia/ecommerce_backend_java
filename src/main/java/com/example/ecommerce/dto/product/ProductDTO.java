package com.example.ecommerce.dto.product;

import java.time.LocalDate;

public record ProductDTO(
        Long id,
        String nome,
        String slug,
        String descricao_curta,
        String descricao,
        float preco,
        float preco_promocional,
        Integer quantidade_estoque,
        Integer quantidade_reservada,
        Integer estoque_minimo,
        String sku,
        float peso,
        float altura,
        float largura,
        float comprimento,
        float media_avaliacao,
        Integer total_avaliacoes,
        boolean status,
        LocalDate data_criacao
) {
}

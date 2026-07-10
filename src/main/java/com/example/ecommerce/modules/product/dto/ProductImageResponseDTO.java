package com.example.ecommerce.modules.product.dto;

import java.time.LocalDate;

public record ProductImageResponseDTO(
        Long id,
        String nomeArquivo,
        String urlImagem,
        Boolean imagemPrincipal,
        LocalDate dataCriacao,
        Long productId
) {
}

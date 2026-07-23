package com.example.ecommerce.modules.importation.product.dto;

import com.example.ecommerce.modules.product.model.ProductEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ImportProductRowDTO(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Slug é obrigatório")
        String slug,

        String descricaoCurta,

        String descricao,

        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser maior que zero")
        BigDecimal preco,

        @PositiveOrZero(message = "Preço promocional deve ser maior ou igual a zero")
        BigDecimal precoPromocional,

        @NotNull(message = "Quantidade em estoque é obrigatória")
        @PositiveOrZero(message = "Quantidade em estoque não pode ser negativa")
        Integer quantidadeEstoque,

        @PositiveOrZero(message = "Estoque mínimo não pode ser negativo")
        Integer estoqueMinimo,

        @NotBlank(message = "SKU é obrigatório")
        String sku,

        @Positive(message = "Peso deve ser maior que zero")
        Float peso,

        @Positive(message = "Altura deve ser maior que zero")
        Float altura,

        @Positive(message = "Largura deve ser maior que zero")
        Float largura,

        @Positive(message = "Comprimento deve ser maior que zero")
        Float comprimento,

        @NotNull(message = "Status é obrigatório")
        ProductEnum status,

        @NotNull(message = "Categoria é obrigatória")
        Long categoriaId
) {
}

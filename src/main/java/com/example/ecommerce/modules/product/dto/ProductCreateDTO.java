package com.example.ecommerce.modules.product.dto;

import com.example.ecommerce.modules.product.model.ProductEnum;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductCreateDTO(

        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 150, message = "O nome deve ter entre 3 e 150 caracteres")
        String nome,

        @NotBlank(message = "O slug é obrigatório")
        @Size(min = 3, max = 150, message = "O slug deve ter entre 3 e 150 caracteres")
        String slug,

        @NotBlank(message = "A descrição curta é obrigatória")
        @Size(max = 255, message = "A descrição curta deve ter no máximo 255 caracteres")
        String descricaoCurta,

        @NotBlank(message = "A descrição é obrigatória")
        String descricao,

        @NotNull(message = "O preço é obrigatório")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
        @Digits(integer = 10, fraction = 2, message = "Preço inválido")
        BigDecimal preco,

        @DecimalMin(value = "0.00", message = "O preço promocional não pode ser negativo")
        @Digits(integer = 10, fraction = 2, message = "Preço promocional inválido")
        BigDecimal precoPromocional,

        @NotNull(message = "A quantidade em estoque é obrigatória")
        @Min(value = 0, message = "A quantidade em estoque não pode ser negativa")
        Integer quantidadeEstoque,

        @NotNull(message = "O estoque mínimo é obrigatório")
        @Min(value = 0, message = "O estoque mínimo não pode ser negativo")
        Integer estoqueMinimo,

        @NotBlank(message = "O SKU é obrigatório")
        @Size(max = 100, message = "O SKU deve ter no máximo 100 caracteres")
        String sku,

        @NotNull(message = "O peso é obrigatório")
        @Positive(message = "O peso deve ser maior que zero")
        Float peso,

        @NotNull(message = "A altura é obrigatória")
        @Positive(message = "A altura deve ser maior que zero")
        Float altura,

        @NotNull(message = "A largura é obrigatória")
        @Positive(message = "A largura deve ser maior que zero")
        Float largura,

        @NotNull(message = "O comprimento é obrigatório")
        @Positive(message = "O comprimento deve ser maior que zero")
        Float comprimento,

        @NotNull(message = "O status é obrigatório")
        ProductEnum status,

        @NotNull(message = "A categoria é obrigatória")
        @Positive(message = "O ID da categoria deve ser maior que zero")
        Long categoriaId

) {
}
package com.example.ecommerce.modules.product.dto;

import com.example.ecommerce.modules.product.model.ProductEnum;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductUpdateDTO(

        @Size(min = 3, max = 150, message = "O nome deve ter entre 3 e 150 caracteres")
        String nome,

        @Size(min = 3, max = 150, message = "O slug deve ter entre 3 e 150 caracteres")
        String slug,

        @Size(max = 255, message = "A descrição curta deve ter no máximo 255 caracteres")
        String descricaoCurta,

        String descricao,

        @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
        @Digits(integer = 10, fraction = 2, message = "Preço inválido")
        BigDecimal preco,

        @DecimalMin(value = "0.00", message = "O preço promocional não pode ser negativo")
        @Digits(integer = 10, fraction = 2, message = "Preço promocional inválido")
        BigDecimal precoPromocional,

        @Min(value = 0, message = "O estoque mínimo não pode ser negativo")
        Integer estoqueMinimo,

        @Positive(message = "O peso deve ser maior que zero")
        Float peso,

        @Positive(message = "A altura deve ser maior que zero")
        Float altura,

        @Positive(message = "A largura deve ser maior que zero")
        Float largura,

        @Positive(message = "O comprimento deve ser maior que zero")
        Float comprimento,

        ProductEnum status,

        @Positive(message = "O ID da categoria deve ser maior que zero")
        Long categoriaId

) {
}
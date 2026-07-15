package com.example.ecommerce.modules.cart.dto;

import com.example.ecommerce.modules.cart.model.CartEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CartResponseDTO(
        Long id,
        CartEnum status,
        BigDecimal valorTotal,
        LocalDate dataCriacao,
        LocalDate dataAtualizacao,
        Integer customerId
) {}
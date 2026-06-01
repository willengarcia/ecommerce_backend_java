package com.example.ecommerce.modules.cart.dto;

import java.time.LocalDate;

public record CartResponseDTO(
        Long id,
        boolean status,
        LocalDate dataCriacao,
        LocalDate dataAtualizacao,
        Integer customerId
) {}
package com.example.ecommerce.modules.cart.dto;

import java.time.LocalDate;

public record CartResponseDTO(
        Long id,
        boolean status,
        Float valorTotal,
        LocalDate dataCriacao,
        LocalDate dataAtualizacao,
        Integer customerId
) {}
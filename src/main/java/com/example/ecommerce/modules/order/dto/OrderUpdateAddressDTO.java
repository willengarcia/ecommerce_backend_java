package com.example.ecommerce.modules.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderUpdateAddressDTO(
        @NotNull(message = "O ID do Endereço é obrigatório")
        @Positive(message = "O ID deve ser maior que 0")
        Integer addressId,
        @NotNull(message = "O ID do Usuário é obrigatório")
        @Positive(message = "O ID deve ser maior que 0")
        Integer customerId
) {
}

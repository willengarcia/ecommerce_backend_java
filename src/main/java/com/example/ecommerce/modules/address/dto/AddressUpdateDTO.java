package com.example.ecommerce.modules.address.dto;

import java.time.LocalDate;

public record AddressUpdateDTO(
        String nomeDestinatario,
        String numero,
        String complemento,
        String referencia,
        String tipoEndereco,
        String enderecoPrincipal,
        LocalDate dataAtualizacao
) {
}

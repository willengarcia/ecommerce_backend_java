package com.example.ecommerce.modules.address.dto;

import java.time.LocalDate;

public record AddressUpdateDTO(
        String nomeEndereco,
        String nomeDestinatario,
        String cep,
        String rua,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String referencia,
        String tipoEndereco,
        String enderecoPrincipal,
        LocalDate dataAtualizacao
) {
}

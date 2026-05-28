package com.example.ecommerce.modules.address.dto;

import java.util.Date;

public record AddressCreateDTO(
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
        Integer usuario_id,
        Date dataCriacao,
        Date dataAtualizacao
) {
}

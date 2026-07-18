package com.example.ecommerce.modules.address.dto;

import com.example.ecommerce.modules.address.model.AddressEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AddressUpdateDTO(
        @Size(max = 100, message = "O nome do endereço deve ter no máximo 100 caracteres")
        String nomeEndereco,

        @Size(max = 100, message = "O nome do destinatário deve ter no máximo 100 caracteres")
        String nomeDestinatario,

        @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 dígitos")
        String cep,

        @Size(max = 150, message = "A rua deve ter no máximo 150 caracteres")
        String rua,

        @Size(max = 10, message = "O número deve ter no máximo 10 caracteres")
        String numero,

        @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
        String cidade,

        @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres")
        String bairro,

        @Size(min = 2, max = 2, message = "O estado deve conter a sigla com 2 caracteres")
        String estado,

        @Size(max = 255, message = "O complemento deve ter no máximo 255 caracteres")
        String complemento,

        @Size(max = 255, message = "A referência deve ter no máximo 255 caracteres")
        String referencia,

        AddressEnum tipoEndereco,

        Boolean enderecoPrincipal
) {
}

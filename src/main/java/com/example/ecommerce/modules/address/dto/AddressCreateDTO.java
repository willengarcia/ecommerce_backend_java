package com.example.ecommerce.modules.address.dto;

import com.example.ecommerce.modules.address.model.AddressEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressCreateDTO(
        @NotBlank(message = "O nome do endereço é obrigatório")
        @Size(max = 100, message = "O nome do endereço deve ter no máximo 100 caracteres")
        String nomeEndereco,

        @NotBlank(message = "O nome do destinatário é obrigatório")
        @Size(max = 100, message = "O nome do destinatário deve ter no máximo 100 caracteres")
        String nomeDestinatario,

        @NotBlank(message = "O CEP é obrigatório")
        @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 dígitos")
        String cep,

        @NotBlank(message = "A rua é obrigatória")
        @Size(max = 150, message = "A rua deve ter no máximo 150 caracteres")
        String rua,

        @NotBlank(message = "O número é obrigatório")
        @Size(max = 10, message = "O número deve ter no máximo 10 caracteres")
        String numero,

        @NotBlank(message = "A cidade é obrigatória")
        @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
        String cidade,

        @NotBlank(message = "O bairro é obrigatório")
        @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres")
        String bairro,

        @NotBlank(message = "O estado é obrigatório")
        @Size(min = 2, max = 2, message = "O estado deve conter a sigla com 2 caracteres")
        String estado,

        @Size(max = 255, message = "O complemento deve ter no máximo 255 caracteres")
        String complemento,

        @Size(max = 255, message = "A referência deve ter no máximo 255 caracteres")
        String referencia,

        @NotNull(message = "O tipo de endereço é obrigatório")
        AddressEnum tipoEndereco,

        @NotNull(message = "Informe se o endereço é principal")
        Boolean enderecoPrincipal,

        @NotNull(message = "ID do usuário é obrigatório")
        Integer usuarioId
) {
}

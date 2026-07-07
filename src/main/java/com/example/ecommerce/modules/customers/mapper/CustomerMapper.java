package com.example.ecommerce.modules.customers.mapper;

import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.customers.dto.CustomerListDTO;
import com.example.ecommerce.modules.customers.dto.CustomerResponseDTO;
import com.example.ecommerce.modules.customers.model.Customers;

public class CustomerMapper {
    public static CustomerListDTO toCustomerListResponseDTO(Customers customers) {
        CustomerListDTO dto = new CustomerListDTO(
                customers.getId(),
                customers.getNomeCompleto(),
                customers.getCpf(),
                customers.getEmail(),
                customers.getTelefone(),
                customers.isStatus(),
                customers.getEnderecos()
                        .stream()
                        .map(address -> new AddressListDTO(
                                address.getId(),
                                address.getNomeEndereco(),
                                address.getNomeDestinatario(),
                                address.getCep(),
                                address.getRua(),
                                address.getNumero(),
                                address.getComplemento(),
                                address.getBairro(),
                                address.getCidade(),
                                address.getEstado(),
                                address.getReferencia(),
                                address.getTipoEndereco(),
                                address.getEnderecoPrincipal(),
                                address.getDataCriacao(),
                                address.getDataAtualizacao()
                        ))
                        .toList(),
                customers.getDataCriacao(),
                customers.getDataAtualizacao()
        );
        return dto;
    }

    public static CustomerResponseDTO toCustomerResponseDTO(Customers customers) {
        CustomerResponseDTO dto = new CustomerResponseDTO(
                customers.getId(),
                customers.getNomeCompleto(),
                customers.getCpf(),
                customers.getEmail(),
                customers.getTelefone(),
                customers.isStatus()
        );
        return dto;
    }
}

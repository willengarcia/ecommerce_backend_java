package com.example.ecommerce.modules.customer.mapper;

import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.customer.dto.CustomerCreateDTO;
import com.example.ecommerce.modules.customer.dto.CustomerListDTO;
import com.example.ecommerce.modules.customer.dto.CustomerResponseDTO;
import com.example.ecommerce.modules.customer.model.Customer;

import java.time.LocalDate;

public class CustomerMapper {
    public static CustomerListDTO toCustomerListResponseDTO(Customer customer) {
        return new CustomerListDTO(
                customer.getId(),
                customer.getNomeCompleto(),
                customer.getCpf(),
                customer.getEmail(),
                customer.getTelefone(),
                customer.getStatus(),
                customer.getEnderecos()
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
                customer.getDataCriacao(),
                customer.getDataAtualizacao()
        );
    }

    public static CustomerResponseDTO toCustomerResponseDTO(Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getNomeCompleto(),
                customer.getCpf(),
                customer.getEmail(),
                customer.getTelefone(),
                customer.getStatus()
        );
    }

    public static Customer toEntityCustomer(CustomerCreateDTO customerCreateDTO){
        return new Customer(customerCreateDTO.nomeCompleto(), customerCreateDTO.cpf(), customerCreateDTO.email(), customerCreateDTO.telefone(), customerCreateDTO.senhaHash());
    }
}

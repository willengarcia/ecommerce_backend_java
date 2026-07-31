package com.example.ecommerce.modules.address.mapper;

import com.example.ecommerce.modules.address.dto.AddressCreateDTO;
import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.address.dto.AddressUpdateDTO;
import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.customer.model.Customer;

public class AddressMapper {
    public static Address toEntityAddress(AddressCreateDTO dto, Customer customer) {

        Address address = new Address(
                dto.nomeEndereco(),
                dto.nomeDestinatario(),
                dto.cep(),
                dto.rua(),
                dto.numero(),
                dto.complemento(),
                dto.cidade(),
                dto.bairro(),
                dto.estado(),
                dto.referencia(),
                dto.tipoEndereco(),
                dto.enderecoPrincipal()
        );
        address.setCustomer(customer);

        return address;
    }
    public static AddressCreateDTO toAddressCreate(Address address){
        return new AddressCreateDTO(
                address.getNomeEndereco(),
                address.getNomeDestinatario(),
                address.getCep(),
                address.getRua(),
                address.getNumero(),
                address.getCidade(),
                address.getBairro(),
                address.getEstado(),
                address.getComplemento(),
                address.getReferencia(),
                address.getTipoEndereco(),
                address.getEnderecoPrincipal(),
                address.getId()
        );
    }
    public static AddressListDTO toAddressList(Address address){
        return new AddressListDTO(
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
        );
    }
    public static AddressUpdateDTO toAddressUpdate(Address address){
        return new AddressUpdateDTO(
                address.getNomeEndereco(),
                address.getNomeDestinatario(),
                address.getCep(),
                address.getRua(),
                address.getNumero(),
                address.getCidade(),
                address.getBairro(),
                address.getEstado(),
                address.getComplemento(),
                address.getReferencia(),
                address.getTipoEndereco(),
                address.getEnderecoPrincipal()
        );
    }
}

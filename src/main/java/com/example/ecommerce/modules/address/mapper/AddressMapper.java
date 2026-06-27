package com.example.ecommerce.modules.address.mapper;

import com.example.ecommerce.modules.address.dto.AddressCreateDTO;
import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.address.dto.AddressUpdateDTO;
import com.example.ecommerce.modules.address.model.Address;

public class AddressMapper {
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
                address.getUsuario().getId(),
                address.getDataCriacao(),
                address.getDataAtualizacao()
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

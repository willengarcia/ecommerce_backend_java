package com.example.ecommerce.modules.address.mapper;

import com.example.ecommerce.modules.address.dto.AddressCreateDTO;
import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.address.dto.AddressUpdateDTO;
import com.example.ecommerce.modules.address.model.Address;

public class AddressMapper {
    public static Address toEntity(AddressCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        Address address = new Address();

        address.setNomeEndereco(dto.nomeEndereco());
        address.setNomeDestinatario(dto.nomeDestinatario());
        address.setCep(dto.cep());
        address.setRua(dto.rua());
        address.setNumero(dto.numero());
        address.setCidade(dto.cidade());
        address.setBairro(dto.bairro());
        address.setEstado(dto.estado());
        address.setComplemento(dto.complemento());
        address.setReferencia(dto.referencia());
        address.setTipoEndereco(dto.tipoEndereco());
        address.setEnderecoPrincipal(dto.enderecoPrincipal());

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

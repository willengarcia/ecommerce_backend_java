package com.example.ecommerce.modules.address.service;

import com.example.ecommerce.modules.address.dto.AddressCreateDTO;
import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.address.repository.AddressRepository;
import com.example.ecommerce.modules.customers.model.Customers;
import com.example.ecommerce.modules.customers.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    public Address criarAddress(AddressCreateDTO addresCreateDTO) {
        Customers customers = customerRepository.findById(addresCreateDTO.usuario_id()).orElseThrow();
        if(addresCreateDTO.cep().isEmpty() || addresCreateDTO.bairro().isEmpty() || addresCreateDTO.cidade().isEmpty() || addresCreateDTO.numero().isEmpty() || addresCreateDTO.estado().isEmpty()){
            throw new RuntimeException("É necessário informar a Cidade, Bairro, Número, Estado, Rua, CEP");
        } else if (customers.isStatus().equals("INATIVO")) {
            throw new RuntimeException("Usuário Inativo");
        } else {
            Address address = new Address(
                    addresCreateDTO.nomeEndereco(),
                    addresCreateDTO.nomeDestinatario(),
                    addresCreateDTO.cep(),
                    addresCreateDTO.rua(),
                    addresCreateDTO.numero(),
                    addresCreateDTO.complemento(),
                    addresCreateDTO.cidade(),
                    addresCreateDTO.bairro(),
                    addresCreateDTO.estado(),
                    addresCreateDTO.referencia(),
                    addresCreateDTO.tipoEndereco(),
                    addresCreateDTO.enderecoPrincipal()
            );
            address.setUsuario(customers);
            return addressRepository.save(address);
        }
    }
}

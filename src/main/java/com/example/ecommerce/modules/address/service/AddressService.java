package com.example.ecommerce.modules.address.service;

import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.address.dto.AddressUpdateDTO;
import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.address.repository.AddressRepository;
import com.example.ecommerce.modules.customers.model.Customers;
import com.example.ecommerce.modules.customers.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    public Address criarAddress(Address address) {
        if (address.getUsuario() == null || address.getUsuario().getId() == null) {
            throw new RuntimeException("É necessário informar o usuário");
        }

        Customers customers = customerRepository.findById(address.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (address.getCep() == null || address.getCep().isEmpty()
                || address.getBairro() == null || address.getBairro().isEmpty()
                || address.getCidade() == null || address.getCidade().isEmpty()
                || address.getNumero() == null || address.getNumero().isEmpty()
                || address.getEstado() == null || address.getEstado().isEmpty()
                || address.getRua() == null || address.getRua().isEmpty()) {
            throw new RuntimeException("É necessário informar a Cidade, Bairro, Número, Estado, Rua, CEP");
        }

        if (customers.isStatus().equals("INATIVO")) {
            throw new RuntimeException("Usuário Inativo");
        }

        address.setUsuario(customers);
        address.setDataCriacao(LocalDate.now());
        address.setDataAtualizacao(LocalDate.now());

        return addressRepository.save(address);
    }

    public List<AddressListDTO> findAll(){
        List<Address> addresses = addressRepository.findAll();
        return  addresses.stream().map(
                c -> new AddressListDTO(
                        c.getId(),
                        c.getNomeEndereco(),
                        c.getNomeDestinatario(),
                        c.getCep(),
                        c.getRua(),
                        c.getNumero(),
                        c.getComplemento(),
                        c.getBairro(),
                        c.getCidade(),
                        c.getEstado(),
                        c.getReferencia(),
                        c.getTipoEndereco(),
                        c.getEnderecoPrincipal(),
                        c.getDataCriacao(),
                        c.getDataAtualizacao()
                )
        ).toList();
    }

    public AddressListDTO findById(Integer id){
        Address address = addressRepository.findById(id).orElseThrow();
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

    public List<Address> findByUsuarioId(Integer id){
        List<Address> address = addressRepository.findByUsuarioId(id);
        return address;
    }

    public AddressUpdateDTO alterAddressById(Integer idAddress, Integer idCustomer, AddressUpdateDTO addressUpdateDTO){
        Address address = addressRepository.findById(idAddress).orElseThrow();
        Customers customers = customerRepository.findById(idCustomer).orElseThrow();
        if (!address.getUsuario().getId().equals(customers.getId())) {
            throw new RuntimeException("Endereço não pertence ao cliente informado");
        }
        if (addressUpdateDTO.tipoEndereco() != null) {
            address.setTipoEndereco(addressUpdateDTO.tipoEndereco());
        }
        if (addressUpdateDTO.bairro() != null) {
            address.setBairro(addressUpdateDTO.bairro());
        }
        if (addressUpdateDTO.cidade() != null) {
            address.setCidade(addressUpdateDTO.cidade());
        }
        if (addressUpdateDTO.estado() != null) {
            address.setEstado(addressUpdateDTO.estado());
        }
        if (addressUpdateDTO.rua() != null) {
            address.setRua(addressUpdateDTO.rua());
        }
        if (addressUpdateDTO.numero() != null) {
            address.setNumero(addressUpdateDTO.numero());
        }
        if (addressUpdateDTO.complemento() != null) {
            address.setComplemento(addressUpdateDTO.complemento());
        }
        if (addressUpdateDTO.nomeDestinatario() != null) {
            address.setNomeDestinatario(addressUpdateDTO.nomeDestinatario());
        }
        if (addressUpdateDTO.referencia() != null) {
            address.setReferencia(addressUpdateDTO.referencia());
        }
        if (addressUpdateDTO.enderecoPrincipal() != null
                && addressUpdateDTO.enderecoPrincipal()) {

            customers.getEnderecos().forEach(endereco -> {
                if (!endereco.getId().equals(address.getId())) {
                    endereco.setEnderecoPrincipal(false);
                }
            });

            address.setEnderecoPrincipal(true);
        }
        address.setDataAtualizacao(LocalDate.now());
        addressRepository.save(address);
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

    public void deletarById(Integer id) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        Customers usuario = address.getUsuario();

        if (usuario != null) {
            usuario.getEnderecos().remove(address);
        }

        addressRepository.delete(address);
    }
}

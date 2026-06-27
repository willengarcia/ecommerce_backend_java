package com.example.ecommerce.modules.address.service;

import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.address.dto.AddressUpdateDTO;
import com.example.ecommerce.modules.address.exception.AddressException;
import com.example.ecommerce.modules.address.mapper.AddressMapper;
import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.address.repository.AddressRepository;
import com.example.ecommerce.modules.customers.model.CustomerEnum;
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
            throw new AddressException("É necessário informar o usuário");
        }

        if (!address.getCep().matches("^(?:\\d{5}-\\d{3}|\\d{8}|S\\/N)$")){
            throw new AddressException("Formato de CEP inválido, deve conter 8 números ou caso não tenha, informar 'S/N' " +
                    "\nEx: 00000000 ou 99999-000");
        }

        Customers customers = customerRepository.findById(address.getUsuario().getId())
                .orElseThrow(() -> new AddressException("Usuário não encontrado"));

        if (customers.isStatus().equals(CustomerEnum.INATIVO) || customers.isStatus().equals(CustomerEnum.BLOQUEADO)) {
            throw new AddressException("Usuário Inativo ou Bloqueado!");
        }

        if (address.getCep() == null || address.getCep().isEmpty()
                || address.getBairro() == null || address.getBairro().isEmpty()
                || address.getCidade() == null || address.getCidade().isEmpty()
                || address.getNumero() == null || address.getNumero().isEmpty()
                || address.getEstado() == null || address.getEstado().isEmpty()
                || address.getRua() == null || address.getRua().isEmpty()
                || address.getNomeDestinatario() == null || address.getNomeDestinatario().isEmpty()) {
            throw new AddressException("É necessário informar a Cidade, Bairro, Número, Estado, Rua, CEP e o Nome do Destinatário");
        }

        address.setUsuario(customers);
        address.setDataCriacao(LocalDate.now());
        address.setDataAtualizacao(LocalDate.now());

        return addressRepository.save(address);
    }

    public List<AddressListDTO> findAll(){
        List<Address> addresses = addressRepository.findAll();
        return  addresses.stream().map(
                AddressMapper::toAddressList).toList();
    }

    public AddressListDTO findById(Integer id){
        Address address = addressRepository.findById(id).orElseThrow();
        return AddressMapper.toAddressList(address);
    }

    public List<Address> findByUsuarioId(Integer id){
        return addressRepository.findByUsuarioId(id);
    }

    public AddressUpdateDTO alterAddressById(Integer idAddress, Integer idCustomer, AddressUpdateDTO addressUpdateDTO){
        Address address = addressRepository.findById(idAddress).orElseThrow();
        Customers customers = customerRepository.findById(idCustomer).orElseThrow();
        if (!address.getUsuario().getId().equals(customers.getId())) {
            throw new AddressException("Endereço não pertence ao cliente informado");
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
        return AddressMapper.toAddressUpdate(address);
    }

    public void deletarById(Integer id) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressException("Endereço não encontrado"));

        if (address.getUsuario().getOrders().stream().anyMatch(a -> a.getAddress().getId().equals(id))) {
            throw new AddressException("Algum usuário tem pedidos vinculados a esse endereço, com isso não é possível realizar a exclusão do Endereço!");
        }

        Customers usuario = address.getUsuario();

        if (usuario != null) {
            usuario.getEnderecos().remove(address);
        }

        addressRepository.delete(address);
    }
}

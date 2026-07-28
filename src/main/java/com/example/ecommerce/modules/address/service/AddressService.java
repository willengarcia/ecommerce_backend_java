package com.example.ecommerce.modules.address.service;

import com.example.ecommerce.modules.address.dto.AddressCreateDTO;
import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.address.dto.AddressUpdateDTO;
import com.example.ecommerce.modules.address.exception.*;
import com.example.ecommerce.modules.address.mapper.AddressMapper;
import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.address.repository.AddressRepository;
import com.example.ecommerce.modules.customer.exception.CustomerNotFoundException;
import com.example.ecommerce.modules.customer.exception.InactiveCustomerException;
import com.example.ecommerce.modules.customer.model.Customer;
import com.example.ecommerce.modules.customer.model.CustomerEnum;
import com.example.ecommerce.modules.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public AddressCreateDTO createAddress(AddressCreateDTO addressDTO) {

        if (!addressDTO.cep().matches("^(?:\\d{5}-\\d{3}|\\d{8}|S\\/N)$")){
            throw new InvalidZipCodeException("Formato de CEP inválido, deve conter 8 números ou caso não tenha, informar 'S/N' " +
                    "\nEx: 00000000 ou 99999-000");
        }

        Customer customer = customerRepository.findById(addressDTO.usuarioId())
                .orElseThrow(() -> new CustomerNotFoundException("Usuário não encontrado"));

        if (customer.isStatus().equals(CustomerEnum.INATIVO) || customer.isStatus().equals(CustomerEnum.BLOQUEADO)) {
            throw new InactiveCustomerException("Usuário Inativo ou Bloqueado!");
        }


        Address addres = AddressMapper.toEntityAddress(addressDTO);

        addres.setUsuario(customer);
        addres.setDataCriacao(LocalDate.now());
        addres.setDataAtualizacao(LocalDate.now());

        Address savedAddress = addressRepository.save(addres);

        return AddressMapper.toAddressCreate(savedAddress);
    }

    public List<AddressListDTO> findAllAddress(){
        List<Address> addresses = addressRepository.findAll();
        return  addresses.stream().map(
                AddressMapper::toAddressList).toList();
    }

    public AddressListDTO findByIdAddress(Integer idAddress){
        Address address = addressRepository.findById(idAddress).orElseThrow(
                ()-> new AddressNotFoundException("Endereço não encontrado!")
        );
        return AddressMapper.toAddressList(address);
    }

    public List<Address> findByUsuarioId(Integer id){
        Customer customer = customerRepository.findById(id).orElseThrow(
                ()-> new CustomerNotFoundException("Usuário não encontrado.")
        );
        return addressRepository.findByUsuarioId(id);
    }

    @Transactional
    public AddressUpdateDTO alterAddressById(Integer idAddress, Integer idCustomer, AddressUpdateDTO addressUpdateDTO){
        Address address = addressRepository.findById(idAddress).orElseThrow(
                () -> new AddressNotFoundException("Endereço não encontrado pelo ID")
        );
        Customer customer = customerRepository.findById(idCustomer).orElseThrow(
                () -> new CustomerNotFoundException("Usuário não encontrado")
        );
        if (!address.getUsuario().getId().equals(customer.getId())) {
            throw new AddressOwnershipException("Endereço não pertence ao cliente informado");
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

            customer.getEnderecos().forEach(endereco -> {
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

    @Transactional
    public void deleteById(Integer idAddress) {

        Address address = addressRepository.findById(idAddress)
                .orElseThrow(() -> new AddressNotFoundException("Endereço não encontrado"));

        if (address.getUsuario().getOrders().stream().anyMatch(a -> a.getAddress().getId().equals(idAddress))) {
            throw new AddressInUseException("Algum usuário tem pedidos vinculados a esse endereço, com isso não é possível realizar a exclusão do Endereço!");
        }

        Customer usuario = address.getUsuario();

        usuario.getEnderecos().remove(address);

        addressRepository.delete(address);
    }
}

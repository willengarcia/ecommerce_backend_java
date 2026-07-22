package com.example.ecommerce.modules.customers.service;

import com.example.ecommerce.modules.customers.dto.CustomerCreateDTO;
import com.example.ecommerce.modules.customers.dto.CustomerListDTO;
import com.example.ecommerce.modules.customers.dto.CustomerUpdateDTO;
import com.example.ecommerce.modules.customers.exception.*;
import com.example.ecommerce.modules.customers.mapper.CustomerMapper;
import com.example.ecommerce.modules.customers.model.CustomerEnum;
import com.example.ecommerce.modules.customers.model.Customers;
import com.example.ecommerce.modules.customers.repository.CustomerRepository;
import com.example.ecommerce.modules.order.model.OrderEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customers criarUsuario(CustomerCreateDTO customerCreateDTO){
        Customers customers = new Customers();
        if (customerRepository.existsByCpf(customerCreateDTO.cpf())) {
            throw new DuplicateCpfException("CPF já cadastrado");
        } if (customerRepository.existsByEmail(customerCreateDTO.email())) {
            throw new DuplicateEmailException("Email já cadastrado");
        } else {
            customers.setCpf(customerCreateDTO.cpf());
            customers.setNomeCompleto(customerCreateDTO.nomeCompleto());
            customers.setEmail(customerCreateDTO.email());
            customers.setTelefone(customerCreateDTO.telefone());
            customers.setStatus(CustomerEnum.ATIVO);
            customers.setSenhaHash(customerCreateDTO.senhaHash());
            customers.setDataCriacao(LocalDate.now());
            customers.setDataAtualizacao(LocalDate.now());
            return customerRepository.save(customers);
        }
    }

    public List<Customers> findAll(){
        return customerRepository.findAll();
    }

    @Transactional
    public Customers atualizarUsuarioPorId(Integer id, CustomerUpdateDTO customerUpdateDTO){
        Customers customers = customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("Cliente não encontrado pelo ID, CPF ou e-mail")
        );
        if (customerUpdateDTO.nomeCompleto() != null) {
            customers.setNomeCompleto(customerUpdateDTO.nomeCompleto());
        }
        if (customerUpdateDTO.email() != null) {
            customers.setEmail(customerUpdateDTO.email());
        }
        if (customerUpdateDTO.telefone() != null) {
            customers.setTelefone(customerUpdateDTO.telefone());
        }
        if (customerUpdateDTO.status() != null) {
            if (customers.getOrders().stream().anyMatch(order -> order.getStatus().equals(OrderEnum.PREPARANDO))
            || customers.getOrders().stream().anyMatch(order -> order.getStatus().equals(OrderEnum.SEPARADO))
            || customers.getOrders().stream().anyMatch(order -> order.getStatus().equals(OrderEnum.PROCESSADO))) {
                throw new CustomerHasOpenOrdersException("Não é permitido excluir/inativar porque existem pedidos pendentes");
            }
            customers.setStatus(customerUpdateDTO.status());
        }
        customers.setDataAtualizacao(LocalDate.now());
        return customerRepository.save(customers);
    }

    public CustomerListDTO buscarUsuarioPorId(Integer idCustomer){
        Customers customers = customerRepository.findById(idCustomer).orElseThrow(
                () -> new CustomerNotFoundException("Cliente não encontrado pelo ID, CPF ou e-mail")
        );

        return CustomerMapper.toCustomerListResponseDTO(customers);
    }
}

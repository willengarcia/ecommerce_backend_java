package com.example.ecommerce.modules.customer.service;

import com.example.ecommerce.modules.customer.dto.CustomerCreateDTO;
import com.example.ecommerce.modules.customer.dto.CustomerFindDTO;
import com.example.ecommerce.modules.customer.dto.CustomerListDTO;
import com.example.ecommerce.modules.customer.dto.CustomerUpdateDTO;
import com.example.ecommerce.modules.customer.exception.*;
import com.example.ecommerce.modules.customer.mapper.CustomerMapper;
import com.example.ecommerce.modules.customer.model.Customer;
import com.example.ecommerce.modules.customer.model.CustomerEnum;
import com.example.ecommerce.modules.customer.repository.CustomerRepository;
import com.example.ecommerce.modules.order.model.OrderEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Customer createCustomer(CustomerCreateDTO customerCreateDTO){
        Customer customer = new Customer();
        if (customerRepository.existsByCpf(customerCreateDTO.cpf())) {
            throw new DuplicateCpfException("CPF já cadastrado");
        } if (customerRepository.existsByEmail(customerCreateDTO.email())) {
            throw new DuplicateEmailException("Email já cadastrado");
        } else {
            customer.setCpf(customerCreateDTO.cpf());
            customer.setNomeCompleto(customerCreateDTO.nomeCompleto());
            customer.setEmail(customerCreateDTO.email());
            customer.setTelefone(customerCreateDTO.telefone());
            customer.setStatus(CustomerEnum.ATIVO);
            customer.setSenhaHash(customerCreateDTO.senhaHash());
            customer.setDataCriacao(LocalDate.now());
            customer.setDataAtualizacao(LocalDate.now());
            return customerRepository.save(customer);
        }
    }

    public Page<Customer> findAllByNameCustomer(CustomerFindDTO nameCustomer, Pageable pageable) {
        return customerRepository.findAllByNomeCompletoContainingIgnoreCase(nameCustomer.nomeCompleto(), pageable);
    }

    @Transactional
    public Customer updateCustomerById(Integer id, CustomerUpdateDTO customerUpdateDTO){
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("Cliente não encontrado pelo ID, CPF ou e-mail")
        );
        if (customerUpdateDTO.nomeCompleto() != null) {
            customer.setNomeCompleto(customerUpdateDTO.nomeCompleto());
        }
        if (customerUpdateDTO.email() != null) {
            customer.setEmail(customerUpdateDTO.email());
        }
        if (customerUpdateDTO.telefone() != null) {
            customer.setTelefone(customerUpdateDTO.telefone());
        }
        if (customerUpdateDTO.status() != null) {
            if (customer.getOrders().stream().anyMatch(order -> order.getStatus().equals(OrderEnum.PREPARANDO))
            || customer.getOrders().stream().anyMatch(order -> order.getStatus().equals(OrderEnum.SEPARADO))
            || customer.getOrders().stream().anyMatch(order -> order.getStatus().equals(OrderEnum.PROCESSADO))) {
                throw new CustomerHasOpenOrdersException("Não é permitido excluir/inativar porque existem pedidos pendentes");
            }
            customer.setStatus(customerUpdateDTO.status());
        }
        customer.setDataAtualizacao(LocalDate.now());
        return customerRepository.save(customer);
    }

    public CustomerListDTO findCustomerById(Integer idCustomer){
        Customer customer = customerRepository.findById(idCustomer).orElseThrow(
                () -> new CustomerNotFoundException("Cliente não encontrado pelo ID, CPF ou e-mail")
        );

        return CustomerMapper.toCustomerListResponseDTO(customer);
    }
}

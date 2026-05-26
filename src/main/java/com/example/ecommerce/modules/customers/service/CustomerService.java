package com.example.ecommerce.modules.customers.service;

import com.example.ecommerce.modules.customers.dto.CustomerDTO;
import com.example.ecommerce.modules.customers.model.Customers;
import com.example.ecommerce.modules.customers.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public List<Customers> findAll(){
        return customerRepository.findAll();
    }
    public Customers criarUsuario(CustomerDTO customerDTO){
        Customers customers = new Customers();
        customers.setCpf(customerDTO.cpf());
        customers.setNomeCompleto(customerDTO.nomeCompleto());
        customers.setEmail(customerDTO.email());
        customers.setTelefone(customerDTO.telefone());
        customers.setStatus("ATIVO");
        customers.setSenhaHash(customerDTO.senhaHash());
        customers.setDataCriacao(LocalDate.now());
        customers.setDataAtualizacao(LocalDate.now());
        return customerRepository.save(customers);
    }
}

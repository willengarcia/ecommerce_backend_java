package com.example.ecommerce.modules.customers.service;

import com.example.ecommerce.modules.address.dto.AddressListDTO;
import com.example.ecommerce.modules.customers.dto.CustomerCreateDTO;
import com.example.ecommerce.modules.customers.dto.CustomerListDTO;
import com.example.ecommerce.modules.customers.dto.CustomerUpdateDTO;
import com.example.ecommerce.modules.customers.model.CustomerEnum;
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
        List<Customers> customers = customerRepository.findAll();
        return customers;
    }

    public Customers criarUsuario(CustomerCreateDTO customerCreateDTO){
        Customers customers = new Customers();
        if(customerCreateDTO.nomeCompleto().isEmpty() || customerCreateDTO.cpf().isEmpty() || customerCreateDTO.email().isEmpty() || customerCreateDTO.telefone().isEmpty() || customerCreateDTO.senhaHash().isEmpty()){
            throw new RuntimeException("É obrigatório informar os campos: Nome, Email, Telefone, Senha, CPF");
        } else if (customerCreateDTO.cpf().length() != 11) {
            throw new RuntimeException("É necessário informar 11 caracteres");
        } if (customerRepository.existsByCpf(customerCreateDTO.cpf())) {
            throw new RuntimeException("CPF já cadastrado");
        } if (customerRepository.existsByEmail(customerCreateDTO.email())) {
            throw new RuntimeException("Email já cadastrado");
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

    public Customers atualizarUsuarioPorId(Integer id, CustomerUpdateDTO customerUpdateDTO){
        Customers customers = customerRepository.findById(id).orElseThrow();
        if(customerUpdateDTO.nomeCompleto().isEmpty() || customerUpdateDTO.email().isEmpty() || customerUpdateDTO.telefone().isEmpty()){
            throw new RuntimeException("É obrigatório informar os campos: Nome, Email, Telefone, Senha");
        } else{

            customers.setNomeCompleto(customerUpdateDTO.nomeCompleto());
            customers.setEmail(customerUpdateDTO.email());
            customers.setTelefone(customerUpdateDTO.telefone());
            customers.setDataAtualizacao(LocalDate.now());

            return customerRepository.save(customers);
        }
    }

    public CustomerListDTO buscarUsuarioPorId(Integer idCustomer){
        Customers customers = customerRepository.findById(idCustomer).orElseThrow();
        CustomerListDTO dto = new CustomerListDTO(
                customers.getId(),
                customers.getNomeCompleto(),
                customers.getCpf(),
                customers.getEmail(),
                customers.getTelefone(),
                customers.isStatus(),
                customers.getEnderecos()
                .stream()
                .map(address -> new AddressListDTO(
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
                ))
                .toList(),
                customers.getDataCriacao(),
                customers.getDataAtualizacao()
        );
        return dto;
    }
}

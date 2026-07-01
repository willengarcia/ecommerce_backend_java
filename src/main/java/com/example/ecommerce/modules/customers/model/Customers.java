package com.example.ecommerce.modules.customers.model;

import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.cart.model.Cart;
import com.example.ecommerce.modules.order.model.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customers {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Getter
    @Setter
    private String nomeCompleto;
    @Getter
    @Setter
    @Column(unique = true)
    private String cpf;
    @Getter
    @Setter
    @Column(unique = true)
    private String email;
    @Getter
    @Setter
    private String telefone;
    @Getter
    @Setter
    private String senhaHash;
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private CustomerEnum status;
    @Getter
    @Setter
    private LocalDate dataCriacao;
    @Getter
    @Setter
    private LocalDate dataAtualizacao;

    @Getter
    @OneToMany(mappedBy= "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Address> enderecos =  new ArrayList<>();

    @Getter
    @OneToMany(mappedBy= "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Order> orders =  new ArrayList<>();

    @Getter
    @OneToMany(mappedBy = "usuario")
    private List<Cart> carts = new ArrayList<>();

    public Customers() {}

    public Customers (Integer id, String nome_completo, String cpf, String email, String telefone, String senhaHash, CustomerEnum status) {
        this.id = id;
        this.nomeCompleto = nome_completo;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.senhaHash = senhaHash;
        this.status = status;
    }

    public void setEnderecos(List<Address> enderecos) {
        enderecos.forEach(address -> address.setUsuario(this));
        this.enderecos = enderecos;
    }

    public CustomerEnum isStatus() { // Remover método
        return status;
    }

}

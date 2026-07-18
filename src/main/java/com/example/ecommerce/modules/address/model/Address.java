package com.example.ecommerce.modules.address.model;

import com.example.ecommerce.modules.customers.model.Customers;
import com.example.ecommerce.modules.order.model.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nomeEndereco;
    @Column(nullable = false)
    private String nomeDestinatario;
    @Column(nullable = false)
    private String cep;
    @Column(nullable = false)
    private String rua;
    @Column(nullable = false)
    private String numero;
    private String complemento;
    @Column(nullable = false)
    private String bairro;
    @Column(nullable = false)
    private String cidade;
    @Column(nullable = false)
    private String estado;
    private String referencia;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AddressEnum tipoEndereco;
    @Column(nullable = false)
    private Boolean enderecoPrincipal;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;

    @ManyToOne
    private Customers usuario;

    @OneToMany(mappedBy= "address", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Order> orders =  new ArrayList<>();

    public Address() {
    }

    public Address(String nome_endereco, String nome_destinatario, String cep, String rua, String numero, String complemento, String cidade, String bairro, String estado, String referencia, AddressEnum tipo_endereco, Boolean endereco_principal) {
        this.nomeEndereco = nome_endereco;
        this.nomeDestinatario = nome_destinatario;
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.bairro = bairro;
        this.estado = estado;
        this.referencia = referencia;
        this.tipoEndereco = tipo_endereco;
        this.enderecoPrincipal = endereco_principal;
    }

}

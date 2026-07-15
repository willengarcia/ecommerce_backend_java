package com.example.ecommerce.modules.order.model;

import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.address.model.AddressEnum;
import com.example.ecommerce.modules.customers.model.Customers;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private BigDecimal valorTotal;
    @Enumerated(EnumType.STRING)
    private OrderEnum status;
    private Integer idAddress;
    private String nomeEndereco;
    private String nomeDestinatario;
    private String cep;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String referencia;
    @Enumerated(EnumType.STRING)
    private AddressEnum tipoEndereco;
    private Boolean enderecoPrincipal;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Customers usuario;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItem = new ArrayList<>();

    public Order() {}

    public Order(Long orderId, BigDecimal valorTotal, OrderEnum status, String nomeEndereco, String nomeDestinatario, String cep, String rua, String numero, String complemento, String bairro, String estado, String cidade, String referencia, AddressEnum tipoEndereco, Boolean enderecoPrincipal, Integer idAddress) {
        this.orderId = orderId;
        this.valorTotal = valorTotal;
        this.status = status;
        this.idAddress = idAddress;
        this.nomeEndereco = nomeEndereco;
        this.nomeDestinatario = nomeDestinatario;
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.estado = estado;
        this.cidade = cidade;
        this.referencia = referencia;
        this.tipoEndereco = tipoEndereco;
        this.enderecoPrincipal = enderecoPrincipal;
        this.dataCriacao = LocalDate.now();
        this.dataAtualizacao = LocalDate.now();
    }
}

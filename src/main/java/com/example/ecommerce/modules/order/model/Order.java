package com.example.ecommerce.modules.order.model;

import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.address.model.AddressEnum;
import com.example.ecommerce.modules.customers.model.Customers;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Getter
    @Setter
    private Float valorTotal;
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private OrderEnum status;

    @Getter
    @Setter
    private Integer idAddress;
    @Getter
    @Setter
    private String nomeEndereco;
    @Getter
    @Setter
    private String nomeDestinatario;
    @Getter
    @Setter
    private String cep;
    @Getter
    @Setter
    private String rua;
    @Getter
    @Setter
    private String numero;
    @Getter
    @Setter
    private String complemento;
    @Getter
    @Setter
    private String bairro;
    @Getter
    @Setter
    private String cidade;
    @Getter
    @Setter
    private String estado;
    @Getter
    @Setter
    private String referencia;
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private AddressEnum tipoEndereco;
    @Getter
    @Setter
    private Boolean enderecoPrincipal;
    @Getter
    @Setter
    private LocalDate dataCriacao;
    @Getter
    @Setter
    private LocalDate dataAtualizacao;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Customers usuario;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @Getter
    @Setter
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItem = new ArrayList<>();

    public Order() {}

    public Order(Long orderId, Float valorTotal, OrderEnum status, String nomeEndereco, String nomeDestinatario, String cep, String rua, String numero, String complemento, String bairro, String estado, String cidade, String referencia, AddressEnum tipoEndereco, Boolean enderecoPrincipal, Integer idAddress) {
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

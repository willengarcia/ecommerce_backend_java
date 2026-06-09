package com.example.ecommerce.modules.order.model;

import com.example.ecommerce.modules.address.model.Address;
import com.example.ecommerce.modules.category.model.OrderEnum;
import com.example.ecommerce.modules.customers.model.Customers;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Float valorTotal;
    @Enumerated(EnumType.STRING)
    private OrderEnum status;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Customers usuario;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public Order() {}

    public Order(Long orderId, Float valorTotal, OrderEnum status) {
        this.orderId = orderId;
        this.valorTotal = valorTotal;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public OrderEnum getStatus() {
        return status;
    }

    public void setStatus(OrderEnum status) {
        this.status = status;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDate dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Customers getUsuario() {
        return usuario;
    }

    public void setUsuario(Customers usuario) {
        this.usuario = usuario;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}

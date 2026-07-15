package com.example.ecommerce.modules.order.model;

import com.example.ecommerce.modules.product.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeProduto;
    private String skuProduto;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subTotal;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    public OrderItem() {}

    public OrderItem(Long id, String nomeProduto, String skuProduto, Integer quantidade, BigDecimal precoUnitario, BigDecimal subtotal, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.skuProduto = skuProduto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subTotal = subtotal;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }

}

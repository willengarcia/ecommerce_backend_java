package com.example.ecommerce.modules.cart.model;

import com.example.ecommerce.modules.product.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_cart_product",
                        columnNames = {"carro_id", "product_id"}
                )
        }
)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subtotal;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;

    @ManyToOne
    private Cart carro;

    @ManyToOne
    private Product product;

    public CartItem(Long id, Integer quantidade, BigDecimal precoUnitario, BigDecimal subtotal) {
        this.id = id;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = subtotal;
    }

    public CartItem() {}

}

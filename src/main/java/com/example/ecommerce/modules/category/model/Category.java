package com.example.ecommerce.modules.category.model;

import com.example.ecommerce.modules.product.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
    private boolean ativo;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;

    @OneToMany(mappedBy= "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<Product> products =  new ArrayList<>();

    public Category() {
    }
    public Category(String name, String description, boolean ativo) {
        this.name = name;
        this.description = description;
        this.ativo = ativo;
    }

}

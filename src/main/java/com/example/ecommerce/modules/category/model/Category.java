package com.example.ecommerce.modules.category.model;

import com.example.ecommerce.modules.product.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Getter
    @Column(unique = true)
    private String name;
    @Setter
    @Getter
    private String description;
    @Setter
    @Getter
    private boolean ativo;
    @Setter
    @Getter
    private LocalDate dataCriacao;
    @Setter
    @Getter
    private LocalDate dataAtualizacao;

    @OneToMany(mappedBy= "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<Product> products =  new ArrayList<>();

    public Category() {
    }
    public Category(Long id, String name, String description, boolean ativo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ativo = ativo;
        this.dataCriacao = LocalDate.now();
        this.dataAtualizacao = LocalDate.now();
    }

}

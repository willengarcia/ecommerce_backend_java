package com.example.ecommerce.modules.product.model;

import com.example.ecommerce.modules.cart.model.CartItem;
import com.example.ecommerce.modules.category.model.Category;
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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String slug;
    private String descricaoCurta;
    private String descricao;
    private BigDecimal preco;
    private BigDecimal precoPromocional;
    private Integer quantidadeEstoque;
    private Integer quantidadeReservada;
    private Integer estoqueMinimo;
    @Column(unique = true)
    private String sku;
    private float peso;
    private float altura;
    private float largura;
    private float comprimento;
    private float mediaAvaliacao;
    private Integer totalAvaliacoes;
    @Enumerated(EnumType.STRING)
    private ProductEnum status;

    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;

    @OneToMany(mappedBy= "product", cascade = CascadeType.ALL)
    private final List<ProductImage> imagem =  new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy= "product", cascade = CascadeType.ALL)
    private final List<CartItem> items = new ArrayList<>();


    public Product(String nome, String slug, String descricao_curta, String descricao, BigDecimal preco, BigDecimal preco_promocional, Integer quantidade_estoque, Integer estoque_minimo, String sku, float peso, float altura, float largura, float comprimento) {
        this.nome = nome;
        this.slug = slug;
        this.descricaoCurta = descricao_curta;
        this.descricao = descricao;
        this.preco = preco;
        this.precoPromocional = preco_promocional;
        this.quantidadeEstoque = quantidade_estoque;
        this.quantidadeReservada = 0;
        this.estoqueMinimo = estoque_minimo;
        this.sku = sku;
        this.peso = peso;
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
        this.mediaAvaliacao = 0;
        this.totalAvaliacoes = 0;
        this.status = ProductEnum.ATIVO;
        this.dataCriacao = LocalDate.now();
        this.dataAtualizacao = LocalDate.now();
    }


    public Product() {}
}

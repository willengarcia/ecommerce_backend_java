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
    @Column(name = "descricao_curta")
    private String descricaoCurta;
    private String descricao;
    private BigDecimal preco;
    @Column(name = "preco_promocional")
    private BigDecimal precoPromocional;
    @Column(name = "quantidadeEstoque")
    private Integer quantidadeEstoque;
    @Column(name = "quantidade_reservada")
    private Integer quantidadeReservada;
    @Column(name = "estoqueMinimo")
    private Integer estoqueMinimo;
    @Column(unique = true)
    private String sku;
    private float peso;
    private float altura;
    private float largura;
    private float comprimento;
    @Column(name = "media_avaliacao")
    private float mediaAvaliacao;
    @Column(name = "total_avaliacao")
    private Integer totalAvaliacoes;
    @Enumerated(EnumType.STRING)
    private ProductEnum status;

    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;

    @OneToMany(mappedBy= "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<ProductImages> imagem =  new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy= "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<CartItem> items = new ArrayList<>();


    public Product(Long id, String nome, String slug, String descricao_curta, String descricao, BigDecimal preco, BigDecimal preco_promocional, Integer quantidade_estoque, Integer quantidade_reservada, Integer estoque_minimo, String sku, float peso, float altura, float largura, float comprimento, float media_avaliacao, Integer total_avaliacoes, ProductEnum status) {
        this.id = id;
        this.nome = nome;
        this.slug = slug;
        this.descricaoCurta = descricao_curta;
        this.descricao = descricao;
        this.preco = preco;
        this.precoPromocional = preco_promocional;
        this.quantidadeEstoque = quantidade_estoque;
        this.quantidadeReservada = quantidade_reservada;
        this.estoqueMinimo = estoque_minimo;
        this.sku = sku;
        this.peso = peso;
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
        this.mediaAvaliacao = media_avaliacao;
        this.totalAvaliacoes = total_avaliacoes;
        this.status = status;
    }


    public Product() {}
}

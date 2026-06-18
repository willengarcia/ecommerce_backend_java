package com.example.ecommerce.modules.address.model;

import com.example.ecommerce.modules.customers.model.Customers;
import com.example.ecommerce.modules.order.model.Order;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public Address(Integer id, String nome_endereco, String nome_destinatario, String cep, String rua, String numero, String complemento, String cidade, String bairro, String estado, String referencia, AddressEnum tipo_endereco, Boolean endereco_principal) {
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customers getUsuario() {
        return usuario;
    }

    public void setUsuario(Customers usuario) {
        this.usuario = usuario;
    }

    public String getNomeEndereco() {
        return nomeEndereco;
    }

    public void setNomeEndereco(String nomeEndereco) {
        this.nomeEndereco = nomeEndereco;
    }

    public String getNomeDestinatario() {
        return nomeDestinatario;
    }

    public void setNomeDestinatario(String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public AddressEnum getTipoEndereco() {
        return tipoEndereco;
    }

    public void setTipoEndereco(AddressEnum tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
    }

    public Boolean getEnderecoPrincipal() {
        return enderecoPrincipal;
    }

    public void setEnderecoPrincipal(Boolean enderecoPrincipal) {
        this.enderecoPrincipal = enderecoPrincipal;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}

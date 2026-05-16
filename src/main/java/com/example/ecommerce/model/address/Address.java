package com.example.ecommerce.model.address;

import com.example.ecommerce.model.customer.Customers;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome_endereco;
    private String nome_destinatario;
    @Column(nullable = false)
    private String cep;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String referencia;
    private String tipo_endereco;
    @Column(nullable = false)
    private String endereco_principal;
    private Date data_criacao;
    private Date data_atualizacao;

    @ManyToOne
    private Customers usuario;

    public Address(Long id, String nome_endereco, String nome_destinatario, String cep, String rua, String numero, String complemento, String cidade, String bairro, String estado, String referencia, String tipo_endereco, String endereco_principal) {
        this.id = id;
        this.nome_endereco = nome_endereco;
        this.nome_destinatario = nome_destinatario;
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.bairro = bairro;
        this.estado = estado;
        this.referencia = referencia;
        this.tipo_endereco = tipo_endereco;
        this.endereco_principal = endereco_principal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customers getUsuario() {
        return usuario;
    }

    public void setUsuario(Customers usuario) {
        this.usuario = usuario;
    }

    public String getNome_endereco() {
        return nome_endereco;
    }

    public void setNome_endereco(String nome_endereco) {
        this.nome_endereco = nome_endereco;
    }

    public String getNome_destinatario() {
        return nome_destinatario;
    }

    public void setNome_destinatario(String nome_destinatario) {
        this.nome_destinatario = nome_destinatario;
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

    public String getTipo_endereco() {
        return tipo_endereco;
    }

    public void setTipo_endereco(String tipo_endereco) {
        this.tipo_endereco = tipo_endereco;
    }

    public String getEndereco_principal() {
        return endereco_principal;
    }

    public void setEndereco_principal(String endereco_principal) {
        this.endereco_principal = endereco_principal;
    }

    public Date getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(Date data_criacao) {
        this.data_criacao = data_criacao;
    }

    public Date getData_atualizacao() {
        return data_atualizacao;
    }

    public void setData_atualizacao(Date data_atualizacao) {
        this.data_atualizacao = data_atualizacao;
    }
}

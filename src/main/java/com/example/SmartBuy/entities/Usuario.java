package com.example.SmartBuy.entities;

import com.example.SmartBuy.enums.SituacaoUsuarioEnum;
import com.example.SmartBuy.enums.UsuarioEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "smb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario", updatable = false, nullable = false)

    private long idUsuario;
    @Column(name = "nome",nullable = false)
    private String nome;

    @Column(name = "email",nullable = true)
    private String email;

    @Column(name = "senha",nullable = false)
    private String senha;

    @Column(name = "cpf",nullable = true)
    private String cpf;

    @Column(name = "cnpj",nullable = true)
    private String cnpj;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoPessoa",nullable = true)
    private UsuarioEnum tipoPessoa;

    @Column(name = "dataCriacao")
    private LocalDateTime dataCriacao;

    @Column(name = "dataAtualizacao")
    private LocalDateTime dataAtualizacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacaoUsuario")
    private SituacaoUsuarioEnum situacaoUsuario;

    public Usuario() {
    }

    public Usuario(long idUsuario, String nome, String email, String senha, String cpf, String cnpj, UsuarioEnum tipo, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.tipoPessoa = tipo;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public UsuarioEnum getTipo() {
        return tipoPessoa;
    }

    public void setTipo(UsuarioEnum tipo) {
        this.tipoPessoa = tipo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public UsuarioEnum getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(UsuarioEnum tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public SituacaoUsuarioEnum getSituacaoUsuario() {
        return situacaoUsuario;
    }

    public void setSituacaoUsuario(SituacaoUsuarioEnum situacaoUsuario) {
        this.situacaoUsuario = situacaoUsuario;
    }
}

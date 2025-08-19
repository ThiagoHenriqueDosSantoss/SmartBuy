package com.example.SmartBuy.dto.Usuario;

import com.example.SmartBuy.enums.UsuarioEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class UsuarioDTO {

    @NotBlank(message = "O campo nome é obrigatório!")
    @Size(min = 3, max = 30)
    private String nome;

    @NotBlank(message = "O campo email é obrigatório!")
    @Size(min = 3, max = 30)
    private String email;

    @NotBlank(message = "O campo senha é obrigatório!")
    private String senha;

    @Size(max = 11)
    private String cpf;
    private String cnpj;

    @NotBlank(message = "É obrigatório assinalar o campo tipo pessoa: (Pessoa Fisica), (Pessoa Jurídica)")
    private UsuarioEnum tipo;
    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String nome, String email, String senha, String cpf, String cnpj, UsuarioEnum tipo, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.tipo = tipo;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
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

    public UsuarioEnum getTipo() {
        return tipo;
    }

    public void setTipo(UsuarioEnum tipo) {
        this.tipo = tipo;
    }
}

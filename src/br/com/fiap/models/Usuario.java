package br.com.fiap.models;

public class Usuario {
    String nome, email, senha;
    ContaCorrente contaCorrente;

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }

    ContaPoupanca contaPoupanca;

    public ContaPoupanca getContaPoupanca() {
        return contaPoupanca;
    }

    public Usuario(String nome, String email, String senha, ContaCorrente contaCorrente) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.contaCorrente = contaCorrente;
    }

    public Usuario(String nome, String email, String senha, ContaPoupanca contaPoupanca) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.contaPoupanca = contaPoupanca;
    }

    public Usuario(String nome, String email, String senha, ContaCorrente contaCorrente, ContaPoupanca contaPoupanca) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.contaCorrente = contaCorrente;
        this.contaPoupanca = contaPoupanca;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

}

package br.com.fiap.models;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Usuario {
    String nome, email, senha;
    ContaCorrente contaCorrente;
    ImageIcon foto;

    public ImageIcon getFoto() {
        return foto;
    }

    public void setFoto(String fotopath) {
        ImageIcon profile = new ImageIcon(fotopath);
        Image image = profile.getImage(); // transform it
        Image newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
        this.foto = new ImageIcon(newimg);
    }

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

    public Usuario(String nome, String email, String senha, ContaCorrente contaCorrente, ContaPoupanca contaPoupanca,
            String fotopath) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.contaCorrente = contaCorrente;
        this.contaPoupanca = contaPoupanca;
        ImageIcon profile = new ImageIcon(fotopath);
        Image image = profile.getImage(); // transform it
        Image newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
        this.foto = new ImageIcon(newimg);

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

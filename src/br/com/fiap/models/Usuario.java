package br.com.fiap.models;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Usuario {
    String nome, email, senha;
    ContaCorrente contaCorrente;
    ContaPoupanca contaPoupanca;
    List<Investimento> investimentos = new ArrayList<Investimento>();
    ImageIcon foto;
    String fotopath = "";

    public String getFotopath() {
        return fotopath;
    }

    public ImageIcon getFoto() {
        return foto;
    }

    public void setFoto(String fotopath) {
        this.fotopath = fotopath;
        ImageIcon profile = new ImageIcon(fotopath);
        Image image = profile.getImage(); // transform it
        Image newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
        this.foto = new ImageIcon(newimg);
    }

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }


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
        this.fotopath = fotopath;
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

    public List<Investimento> getInvestimentos() {
        return investimentos;
    }

    public void addInvestimento(Investimento investimento) {
        investimentos.add(investimento);
    }
}

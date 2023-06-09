package br.com.fiap.models;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

public class Usuario {
    String nome, email, senha;
    ContaCorrente contaCorrente;
    ContaPoupanca contaPoupanca;
    Boolean hasAssessor = false;

    public Boolean hasAssessor() {
        return hasAssessor;
    }

    public void setAssessor(Boolean hasAssessor) {
        this.hasAssessor = hasAssessor;
    }

    List<Investimento> investimentos = new ArrayList<Investimento>();
    List<String> transacoesSuspeitas = new ArrayList<String>();

    public List<String> getTransacoesSuspeitas() {
        return transacoesSuspeitas;
    }

    ImageIcon foto;
    Boolean bloqueado = false;

    public Boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    Boolean suspeito = false;

    public Boolean getSuspeito() {
        return suspeito;
    }

    public void setSuspeito(Boolean suspeito) {
        this.suspeito = suspeito;
    }

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

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nome, String email, String senha, String fotopath) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.fotopath = fotopath;
        ImageIcon profile = new ImageIcon(fotopath);
        Image image = profile.getImage();
        Image newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
        this.foto = new ImageIcon(newimg);
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
        Image image = profile.getImage();
        Image newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);

        this.foto = new ImageIcon(newimg);
    }

    public Usuario(String nome, String email, String senha, ContaCorrente contaCorrente, ContaPoupanca contaPoupanca,
            String fotopath, Boolean suspeito, Boolean bloqueado) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.contaCorrente = contaCorrente;
        this.contaPoupanca = contaPoupanca;
        this.fotopath = fotopath;
        ImageIcon profile = new ImageIcon(fotopath);
        Image image = profile.getImage();
        Image newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
        this.foto = new ImageIcon(newimg);
        this.suspeito = suspeito;
        this.bloqueado = bloqueado;
    }

    public Usuario(String nome, String email, String senha, ContaCorrente contaCorrente, ContaPoupanca contaPoupanca,
            String fotopath, Boolean suspeito, Boolean bloqueado, List<String> transacoesSuspeitas,
            Boolean hasAssessor) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.contaCorrente = contaCorrente;
        this.contaPoupanca = contaPoupanca;
        this.fotopath = fotopath;
        ImageIcon profile = new ImageIcon(fotopath);
        Image image = profile.getImage();
        Image newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
        this.foto = new ImageIcon(newimg);
        this.suspeito = suspeito;
        this.bloqueado = bloqueado;
        this.transacoesSuspeitas = transacoesSuspeitas;
        this.hasAssessor = hasAssessor;
    }

    public Usuario(String nome, String email, String senha, ContaCorrente contaCorrente, ContaPoupanca contaPoupanca,
            JFileChooser fotopath) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.contaCorrente = contaCorrente;
        this.contaPoupanca = contaPoupanca;
        this.fotopath = fotopath.getSelectedFile().getAbsolutePath();
        try {
            BufferedImage originalImage = ImageIO.read(fotopath.getSelectedFile());

            BufferedImage roundedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = roundedImage.createGraphics();
            g2.setClip(new Ellipse2D.Double(0, 0, originalImage.getWidth(), originalImage.getHeight()));
            g2.drawImage(originalImage, 0, 0, null);
            g2.dispose();

            ImageIcon profile = new ImageIcon(roundedImage);
            Image image = profile.getImage();
            Image newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
            this.foto = new ImageIcon(newimg);
        } catch (Exception e) {
            System.out.println(e);
        }
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

    public void removeInvestimento(Investimento investimento) {
        investimentos.remove(investimento);
    }

    public void addTransacaoSuspeita(String transacao) {
        transacoesSuspeitas.add(transacao);
        suspeito = true;
    }

    public void removeTransacaoSuspeita(String transacao) {
        transacoesSuspeitas.remove(transacao);
        if (transacoesSuspeitas.isEmpty()) {
            suspeito = false;
        }
    }
}

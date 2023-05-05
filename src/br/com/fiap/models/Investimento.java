package br.com.fiap.models;

public class Investimento {
    private String nome;
 

    private double valorInicial;
    private double jurosPorSegundo;
    private int periodoSegundos;

    public Investimento(String nome, double valorInicial, double jurosPorSegundo, int periodoSegundos) {
        this.nome = nome;
        this.valorInicial = valorInicial;
        this.jurosPorSegundo = jurosPorSegundo;
        this.periodoSegundos = periodoSegundos;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public double getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(double valorInicial) {
        this.valorInicial = valorInicial;
    }

    public double getJurosPorSegundo() {
        return jurosPorSegundo;
    }

    public void setJurosPorSegundo(double jurosMensais) {
        this.jurosPorSegundo = jurosMensais;
    }

    public int getPeriodoSegundos() {
        return periodoSegundos;
    }

    public void setPeriodoSegundos(int periodoMeses) {
        this.periodoSegundos = periodoMeses;
    }

    public double calcularLucro() {
        double lucro = valorInicial * Math.pow(1 + jurosPorSegundo, periodoSegundos) - valorInicial;
        return lucro;
    }
}

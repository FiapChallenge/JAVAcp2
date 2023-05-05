package br.com.fiap.models;

import javax.swing.SwingWorker;

public class Investimento {
    private String nome;
    private int segundosAtual = 0;
    public int getSegundosAtual() {
        return segundosAtual;
    }

    public void setSegundosAtual(int segundosAtual) {
        this.segundosAtual = segundosAtual;
    }

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

    public void imprimir() {
        System.out.println("Nome: " + nome);
        System.out.println("Valor inicial: " + valorInicial);
        System.out.println("Juros por segundo: " + jurosPorSegundo);
        System.out.println("Periodo em segundos: " + periodoSegundos);
        System.out.println("Lucro: " + calcularLucro());
    }

    public void timer() {
        SwingWorker<Void, Void> worker1 = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                int i = 0;
                while (i <= periodoSegundos) {
                    setSegundosAtual(i);
                    Thread.sleep(1000);
                    i++;
                }
                return null;
            }
        };
        worker1.execute();
    }

    public double calcularLucro() {
        if (segundosAtual == periodoSegundos) {
            double lucro = valorInicial * Math.pow(1 + jurosPorSegundo, periodoSegundos) - valorInicial;
            return lucro;
        } else {
            return 0;
        }
    }
}

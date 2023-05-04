package br.com.fiap.models;

import java.util.Date;

public class Boleto {
    private String numeroBoleto;
    private double valor;
    private Date dataVencimento;
    private String beneficiario;
    private boolean estaPago = false;

    public Boleto(String numeroBoleto, double valor, Date dataVencimento, String beneficiario) {
        this.numeroBoleto = numeroBoleto;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.beneficiario = beneficiario;
    }

    public String getNumeroBoleto() {
        return numeroBoleto;
    }

    public double getValor() {
        return valor;
    }

    public boolean estaPago() {
        return estaPago;
    }

    public void pagar() {
        estaPago = true;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public boolean estaVenvido() {
        Date dataAtual = new Date();
        return dataAtual.after(dataVencimento);
    }



}
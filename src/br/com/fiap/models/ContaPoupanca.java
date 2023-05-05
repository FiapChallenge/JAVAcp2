package br.com.fiap.models;

public class ContaPoupanca extends Conta {
    private double taxaJurosMensal = 0.2;

    public ContaPoupanca(String numero) {
        super(numero);
    }

    public ContaPoupanca(String numero, double saldo) {
        super(numero, saldo);
    }

    public double getTaxaJurosMensal() {
        return taxaJurosMensal;
    }

    public void setTaxaJurosMensal(double taxaJuros) {
        this.taxaJurosMensal = taxaJuros;
    }

    public void renderJurosMensal() {
        double juros = getSaldo() * taxaJurosMensal;
        depositar(juros, "Rendimento de juros");
    }

}

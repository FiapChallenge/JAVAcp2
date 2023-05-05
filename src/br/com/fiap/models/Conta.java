package br.com.fiap.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public abstract class Conta {
    String numero;
    double saldo = 0;
    // lista de transacoes
    List<String> transacoes = new ArrayList<String>();

    public String getTransacoesFormatted() {
        if (!(transacoes.isEmpty())) {
            Collections.reverse(transacoes);
            return String.join("\n", transacoes);
        } else {
            return "Nenhuma transação realizada";
        }
    }

    public Conta(String numero) {
        this.numero = numero;
        this.saldo = 0;
    }

    public Conta(String numero, double saldo) {
        this.numero = numero;
        this.saldo = saldo;
    }

    public void depositar(double valor, String... descricao) {
        valor = Math.round(valor * 100.0) / 100.0;
        this.saldo += valor;
        this.saldo = Math.round(this.saldo * 100.0) / 100.0;
        Date dataHoraAtual = new Date();
        String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
        if (descricao.length > 0) {
            transacoes.add(descricao[0]);
        } else {
            transacoes.add("Deposito de R$" + valor + " as " + hora);
        }
    }

    public void sacar(double valor, String... descricao) throws Exception {
        if (valor <= this.saldo) {
            valor = Math.round(valor * 100.0) / 100.0;
            this.saldo -= valor;
            this.saldo = Math.round(this.saldo * 100.0) / 100.0;
            if (descricao.length > 0) {
                transacoes.add(descricao[0]);
            } else {
                Date dataHoraAtual = new Date();
                String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
                transacoes.add("Saque de R$" + valor + " as " + hora);
            }
        } else {
            throw new Exception("Saldo insuficiente");
        }
    }

    public String getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

}

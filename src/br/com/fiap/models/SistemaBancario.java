package br.com.fiap.models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SistemaBancario {
    List<Usuario> usuarios = new ArrayList<Usuario>();
    List<ContaCorrente> contasCorrente = new ArrayList<ContaCorrente>();
    List<ContaPoupanca> contasPoupanca = new ArrayList<ContaPoupanca>();

    public void addUsuario(Usuario usuario) {
        usuarios.add(usuario);
        if (usuario.contaCorrente != null) {
            contasCorrente.add(usuario.contaCorrente);
        }
        if (usuario.contaPoupanca != null) {
            contasPoupanca.add(usuario.contaPoupanca);
        }
    }

    public Conta buscarConta(String numero) {
        for (ContaCorrente contaCorrente : contasCorrente) {
            if (contaCorrente.getNumero().equals(numero)) {
                return contaCorrente;
            }
        }
        for (ContaPoupanca contaPoupanca : contasPoupanca) {
            if (contaPoupanca.getNumero().equals(numero)) {
                return contaPoupanca;
            }
        }
        return null;
    }

    public Usuario buscarUsuario(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }

    public void transferir(String numeroOrigem, String numeroDestino, double valor) throws Exception {
        Conta contaOrigem = buscarConta(numeroOrigem);
        if (contaOrigem == null) {
            System.out.println("Conta de origem não encontrada");
            throw new Exception("Conta de origem não encontrada");
        }
        if (contaOrigem instanceof ContaPoupanca) {
            System.out.println("Conta de origem não é corrente");
            throw new Exception("Conta de origem não é corrente");
        }
        Conta contaDestino = buscarConta(numeroDestino);
        if (contaDestino == null) {
            System.out.println("Conta de destino não encontrada");
            throw new Exception("Conta de destino não encontrada");
        }
        if (contaDestino instanceof ContaPoupanca) {
            System.out.println("Conta de destino não é corrente");
            throw new Exception("Conta de destino não é corrente");
        }
        if (contaOrigem != null && contaDestino != null) {
            try {
                Date dataHoraAtual = new Date();
                String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
                contaOrigem.sacar(valor,
                        "Transferencia de R$" + valor + " para a conta " + contaDestino.getNumero() + " as " + hora);
            } catch (Exception e) {
                System.out.println("Saldo insuficiente");
                throw new Exception("Saldo insuficiente");
            }
            Date dataHoraAtual = new Date();
            String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
            contaDestino.depositar(valor,
                    "Transferencia de R$" + valor + " da conta " + contaOrigem.getNumero() + " as " + hora);
        } else {
            System.out.println("Conta não encontrada");
            throw new Exception("Conta não encontrada");
        }
    }

    public void saveData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data.txt"))) {
            for (Usuario usuario : usuarios) {
                String row = usuario.getNome() + " " + usuario.getEmail() + " " + usuario.getSenha() + " "
                        + usuario.getContaCorrente().getNumero() + " " + usuario.getContaCorrente().getSaldo() + " "
                        + usuario.getContaPoupanca().getNumero() + " " + usuario.getContaPoupanca().getSaldo() + " " + usuario.getFotopath();
                bw.write(row);
                bw.newLine(); // write each line on a new line
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}

package br.com.fiap.models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SistemaBancario {
    List<Usuario> usuarios = new ArrayList<Usuario>();

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    List<ContaCorrente> contasCorrente = new ArrayList<ContaCorrente>();
    List<ContaPoupanca> contasPoupanca = new ArrayList<ContaPoupanca>();
    List<Boleto> boletos = new ArrayList<Boleto>();
    List<Investimento> investimentos = new ArrayList<Investimento>();

    public List<Investimento> getInvestimentos() {
        return investimentos;
    }

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

    public void transferir(String numeroOrigem, String numeroDestino, double valor, boolean... isBoleto)
            throws Exception {
        Conta contaOrigem = buscarConta(numeroOrigem);
        if (contaOrigem == null) {
            // System.out.println("Conta de origem não encontrada");
            throw new Exception("Conta de origem não encontrada");
        }
        if (isBoleto.length == 0 || isBoleto[0] == false) {
            if (contaOrigem instanceof ContaPoupanca) {
                // System.out.println("Conta de origem não é corrente");
                throw new Exception("Conta de origem não é corrente");
            }
        }
        Conta contaDestino = buscarConta(numeroDestino);
        if (contaDestino == null) {
            // System.out.println("Conta de destino não encontrada");
            throw new Exception("Conta de destino não encontrada");
        }
        if (isBoleto.length == 0 || isBoleto[0] == false) {
            if (contaDestino instanceof ContaPoupanca) {
                // System.out.println("Conta de destino não é corrente");
                throw new Exception("Conta de destino não é corrente");
            }
        }
        if (contaOrigem != null && contaDestino != null) {
            try {
                Date dataHoraAtual = new Date();
                String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
                if (isBoleto.length == 0 || isBoleto[0] == false) {
                    contaOrigem.sacar(valor,
                            "Transferencia feita de R$" + valor + " para a conta " + contaDestino.getNumero() + " as "
                                    + hora);
                } else if (isBoleto[0] == true) {
                    contaOrigem.sacar(valor,
                            "Pagamento feito de boleto de R$" + valor + " para a conta " + contaDestino.getNumero()
                                    + " as " + hora);
                }
            } catch (Exception e) {
                // System.out.println("Saldo insuficiente");
                throw new Exception("Saldo insuficiente");
            }
            Date dataHoraAtual = new Date();
            String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
            if (isBoleto.length == 0 || isBoleto[0] == false) {
                contaDestino.depositar(valor,
                        "Transferencia recebida de R$" + valor + " da conta " + contaOrigem.getNumero() + " as "
                                + hora);
            } else if (isBoleto[0] == true) {
                contaDestino.depositar(valor,
                        "Pagamento recebido de boleto de R$" + valor + " da conta " + contaOrigem.getNumero() + " as "
                                + hora);
            }
        } else {
            // System.out.println("Conta não encontrada");
            throw new Exception("Conta não encontrada");
        }
    }

    public String emitirBoleto(String numeroBeneficiario, double valor) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 10);
        Date dataVencimento = calendar.getTime();
        String numeroBoleto = Integer.toString(boletos.size() + 1);
        Boleto boleto = new Boleto(numeroBoleto, valor, dataVencimento, numeroBeneficiario);
        boletos.add(boleto);
        return numeroBoleto;
    }

    public void pagarBoleto(String numeroOrigem, String numeroBoleto) throws Exception {
        Boleto boleto = null;
        for (Boleto boletoTeste : boletos) {
            if (boletoTeste.getNumeroBoleto().equals(numeroBoleto)) {
                boleto = boletoTeste;
            }
        }
        if (boleto == null) {
            throw new Exception("Boleto não encontrado");
        }
        try {
            this.transferir(numeroOrigem, boleto.getBeneficiario(), boleto.getValor(), true);
            boleto.pagar();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void saveData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data.txt"))) {
            for (Usuario usuario : usuarios) {
                String row = usuario.getNome() + " " + usuario.getEmail() + " " + usuario.getSenha() + " "
                        + usuario.getContaCorrente().getNumero() + " " + usuario.getContaCorrente().getSaldo() + " "
                        + usuario.getContaPoupanca().getNumero() + " " + usuario.getContaPoupanca().getSaldo() + " "
                        + usuario.getFotopath();
                bw.write(row);
                bw.newLine(); // write each line on a new line
            }
        } catch (IOException e) {
            // System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public Investimento addInvestimento(Usuario usuario, Conta conta, Investimento investimento) throws Exception {
        Date dataHoraAtual = new Date();
        String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
        try {
            conta.sacar(investimento.getValorInicial(), "Investimento de R$" + investimento.getValorInicial()
                    + " feito em " + investimento.getNome() + " as " + hora);
        } catch (Exception e) {
            // System.out.println("Saldo insuficiente");
            throw new Exception("Saldo insuficiente");
        }
        Investimento investimentoReturn = new Investimento(investimento.getNome(), investimento.getValorInicial(),
                investimento.getJurosPorSegundo(), investimento.getPeriodoSegundos());
        usuario.addInvestimento(investimentoReturn);
        return investimentoReturn;

    }

    public void addInvestimentoToList(Investimento investimento) {
        investimentos.add(investimento);
    }

    public void removeInvestimento(Usuario usuario, Investimento investimento, Conta conta) {
        usuario.removeInvestimento(investimento);
        double novoValor = investimento.getValorInicial() + investimento.calcularLucro();
        // rounded novo valor to 2 decimal places
        novoValor = Math.round(novoValor * 100d) / 100d;
        conta.depositar(
                novoValor,
                "Investimento de R$" + investimento.getValorInicial()
                        + " retirado de " + investimento.getNome());
        investimentos.remove(investimento);
    }

    public ContaCorrente createNewContaCorrente() {
        // create new number
        int newNumber = 0;
        boolean numberExists = true;
        while (numberExists) {
            newNumber = (int) (Math.random() * 999);
            numberExists = false;
            for (Usuario usuarioTeste : usuarios) {
                if (usuarioTeste.getContaCorrente().getNumero() == Integer.toString(newNumber)) {
                    numberExists = true;
                }
                if (usuarioTeste.getContaPoupanca().getNumero() == Integer.toString(newNumber)) {
                    numberExists = true;
                }
            }
        }
        ContaCorrente contaCorrente = new ContaCorrente(Integer.toString(newNumber));
        return contaCorrente;
    }

    public ContaPoupanca createNewContaPoupanca() {
        // create new number
        int newNumber = 0;
        boolean numberExists = true;
        while (numberExists) {
            newNumber = (int) (Math.random() * 999);
            numberExists = false;
            for (Usuario usuarioTeste : usuarios) {
                if (usuarioTeste.getContaPoupanca().getNumero() == Integer.toString(newNumber)) {
                    numberExists = true;
                }
                if (usuarioTeste.getContaCorrente().getNumero() == Integer.toString(newNumber)) {
                    numberExists = true;
                }
            }
        }
        ContaPoupanca contaPoupanca = new ContaPoupanca(Integer.toString(newNumber));
        return contaPoupanca;
    }

}

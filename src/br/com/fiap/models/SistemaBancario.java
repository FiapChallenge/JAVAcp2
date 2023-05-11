package br.com.fiap.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SistemaBancario {
    private List<Usuario> usuarios = new ArrayList<Usuario>();
    private List<ContaCorrente> contasCorrente = new ArrayList<ContaCorrente>();
    private List<ContaPoupanca> contasPoupanca = new ArrayList<ContaPoupanca>();
    private List<Boleto> boletos = new ArrayList<Boleto>();
    private List<Investimento> investimentos = new ArrayList<Investimento>();

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

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
                    String descricao = "Transferencia feita de R$" + valor + " para a conta " + contaDestino.getNumero()
                            + " as " + hora;
                    if (valor > 1000) {
                        Usuario usuario = getUsuarioByAccountNumber(numeroOrigem);
                        usuario.addTransacaoSuspeita(hora + "|" + valor + "|" + "Transferencia feita" + "|"
                                + contaOrigem.getNumero() + "|" + contaDestino.getNumero());
                    }
                    contaOrigem.sacar(valor, descricao);
                } else if (isBoleto[0] == true) {
                    String descricao = "Pagamento feito de boleto de R$" + valor + " para a conta "
                            + contaDestino.getNumero() + " as " + hora;
                    if (valor > 1000) {
                        Usuario usuario = getUsuarioByAccountNumber(numeroOrigem);
                        usuario.addTransacaoSuspeita(hora + "|" + valor + "|" + "Pagamento de boleto" + "|"
                                + contaOrigem.getNumero() + "|" + contaDestino.getNumero());
                    }
                    contaOrigem.sacar(valor, descricao);
                }
            } catch (Exception e) {
                // System.out.println("Saldo insuficiente");
                throw new Exception("Saldo insuficiente");
            }
            Date dataHoraAtual = new Date();
            String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
            if (isBoleto.length == 0 || isBoleto[0] == false) {
                String descricao = "Transferencia recebida de R$" + valor + " da conta " + contaOrigem.getNumero()
                        + " as " + hora;
                if (valor > 1000) {
                    Usuario usuario = getUsuarioByAccountNumber(numeroDestino);
                    usuario.addTransacaoSuspeita(hora + "|" + valor + "|" + "Transferencia recebida" + "|"
                            + contaOrigem.getNumero() + "|" + contaDestino.getNumero());
                }
                contaDestino.depositar(valor, descricao);
            } else if (isBoleto[0] == true) {
                String descricao = "Pagamento recebido de boleto de R$" + valor + " da conta " + contaOrigem.getNumero()
                        + " as " + hora;
                if (valor > 1000) {
                    Usuario usuario = getUsuarioByAccountNumber(numeroDestino);
                    usuario.addTransacaoSuspeita(hora + "|" + valor + "|" + "Recebido de boleto" + "|"
                            + contaOrigem.getNumero() + "|" + contaDestino.getNumero());
                }
                contaDestino.depositar(valor, descricao);
            }
        } else {
            // System.out.println("Conta não encontrada");
            throw new Exception("Conta não encontrada");
        }
    }

    public Usuario getUsuarioByAccountNumber(String numeroConta) {
        for (Usuario usuario : usuarios) {
            if (usuario.contaCorrente != null) {
                if (usuario.contaCorrente.getNumero().equals(numeroConta)) {
                    return usuario;
                }
            }
            if (usuario.contaPoupanca != null) {
                if (usuario.contaPoupanca.getNumero().equals(numeroConta)) {
                    return usuario;
                }
            }
        }
        return null;
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

    public void loadData() {
        List<List<String>> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                data.add(Arrays.asList(values));
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo");
        }
        for (List<String> row : data) {
            List<String> transacoesSuspeitas = new ArrayList<>();
            if (row.size() > 10 && row.get(10) != null && !row.get(10).equals("[]")) {
                String[] transacoes = row.get(10).replace("[", "").replace("]", "").split(",");
                for (String transacao : transacoes) {
                    transacoesSuspeitas.add(transacao.trim());
                }
            }
            addUsuario(new Usuario(row.get(0), row.get(1), row.get(2),
                    new ContaCorrente(row.get(3), Double.parseDouble(row.get(4))),
                    new ContaPoupanca(row.get(5), Double.parseDouble(row.get(6))), row.get(7),
                    Boolean.parseBoolean(row.get(8)), Boolean.parseBoolean(row.get(9)), transacoesSuspeitas));
        }
    }

    public void saveData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data.txt"))) {
            for (Usuario usuario : usuarios) {
                String row = usuario.getNome() + ";" + usuario.getEmail() + ";" + usuario.getSenha() + ";"
                        + usuario.getContaCorrente().getNumero() + ";" + usuario.getContaCorrente().getSaldo() + ";"
                        + usuario.getContaPoupanca().getNumero() + ";" + usuario.getContaPoupanca().getSaldo() + ";"
                        + usuario.getFotopath() + ";" + usuario.getSuspeito() + ";" + usuario.getBloqueado() + ";"
                        + usuario.getTransacoesSuspeitas();
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
        double valor = investimento.calcularTotal();
        valor = Math.round(valor * 100d) / 100d;
        conta.depositar(valor, "Recebido R$" + valor + " do investimento " + investimento.getNome() + " (Lucro de R$"
                + (valor - investimento.getValorInicial()) + ")");
        investimentos.remove(investimento);
    }

    public ContaCorrente createNewContaCorrente() {
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

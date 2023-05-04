package br.com.fiap.models;

import javax.swing.JOptionPane;

public class Interface {
    public static int escolhaConta() {
        return JOptionPane.showOptionDialog(null, "Escolha uma conta:", "Banco FinHive", 0,
                JOptionPane.QUESTION_MESSAGE, null, new String[] { "Conta Corrente", "Conta Poupança" },
                "Conta Corrente");
    }


    public static int menu(String menu, Usuario usuario) {
        String info;
        if (usuario.getContaCorrente() == null) {
            info = "Nome: " + usuario.getNome() + "\n" + "Conta Poupança: "
                    + usuario.getContaPoupanca().getSaldo() + "\n\n";
        } else if (usuario.getContaPoupanca() == null) {
            info = "Nome: " + usuario.getNome() + "\n" + "Conta Corrente: "
                    + usuario.getContaCorrente().getSaldo() + "\n\n";
        } else {
            info = "Nome: " + usuario.getNome() + "\n" + "Conta Corrente: "
                    + usuario.getContaCorrente().getSaldo() + "\n" + "Conta Poupança: "
                    + usuario.getContaPoupanca().getSaldo() + "\n\n";
        }

        int opcao = JOptionPane.showOptionDialog(null, (info + menu), "Banco FinHive", 0,
                JOptionPane.QUESTION_MESSAGE, usuario.getFoto(),
                new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" }, "1");
        return opcao;
    }

    public static void depositar(Usuario usuario) {
        int contaOpcaoDepositar = escolhaConta();
        if (contaOpcaoDepositar == 0) {
            double valor = Double
                    .parseDouble(JOptionPane.showInputDialog("Digite o valor a ser depositado:"));
            usuario.getContaCorrente().depositar(valor);
        } else if (contaOpcaoDepositar == 1) {
            double valor = Double
                    .parseDouble(JOptionPane.showInputDialog("Digite o valor a ser depositado:"));
            usuario.getContaPoupanca().depositar(valor);
        } else {
            JOptionPane.showMessageDialog(null, "Opção inválida");
        }
    }

    public static void sacar(Usuario usuario) {
        int contaOpcaoSacar = escolhaConta();
        if (contaOpcaoSacar == 0) {
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor a ser sacado:"));
            System.out.println(valor);
            try {
                usuario.getContaCorrente().sacar(valor);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Saldo Insuficiente");
            }
        } else if (contaOpcaoSacar == 1) {
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor a ser sacado:"));
            try {
                usuario.getContaPoupanca().sacar(valor);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Saldo Insuficiente");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Opção inválida");
        }
    }

    public static void transferir(Usuario usuario, SistemaBancario sb) {
        String numeroDestino = JOptionPane.showInputDialog("Digite o número da conta de destino:");
        double valor = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor a ser transferido:"));

        try {
            sb.transferir(usuario.getContaCorrente().getNumero(), numeroDestino, valor);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void relatorio(Usuario usuario) {
        int contaOpcaoExibirTransacoes = escolhaConta();
        if (contaOpcaoExibirTransacoes == 0) {
            JOptionPane.showMessageDialog(null, usuario.getContaCorrente().getTransacoesFormatted());
        } else if (contaOpcaoExibirTransacoes == 1) {
            JOptionPane.showMessageDialog(null, usuario.getContaPoupanca().getTransacoesFormatted());
        } else {
            JOptionPane.showMessageDialog(null, "Opção inválida");
        }
    }

    public static void boleto(Usuario usuario, SistemaBancario sb) {
        int opcaoPagarOuEmitir = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Banco FinHive",
                0,
                JOptionPane.QUESTION_MESSAGE, null, new String[] { "Emitir Boleto", "Pagar Boleto" },
                "Emitir Boleto");
        if (opcaoPagarOuEmitir == 0) {
            int contaOpcaoEmitirBoleto = escolhaConta();
            if (contaOpcaoEmitirBoleto == 0) {
                double valorEmitirBoleto = Double.parseDouble(
                        JOptionPane.showInputDialog("Digite o valor a ser emitido no boleto:"));
                String numeroBoleto = sb.emitirBoleto(usuario.getContaCorrente().getNumero(),
                        valorEmitirBoleto);
                JOptionPane.showMessageDialog(null,
                        "Boleto emitido com sucesso!\nNúmero do boleto: " + numeroBoleto);
            } else if (contaOpcaoEmitirBoleto == 1) {
                double valorEmitirBoleto = Double.parseDouble(
                        JOptionPane.showInputDialog("Digite o valor a ser emitido no boleto:"));
                String numeroBoleto = sb.emitirBoleto(usuario.getContaPoupanca().getNumero(),
                        valorEmitirBoleto);
                JOptionPane.showMessageDialog(null,
                        "Boleto emitido com sucesso!\nNúmero do boleto: " + numeroBoleto);
            } else {
                JOptionPane.showMessageDialog(null, "Opção inválida");
            }
        } else if (opcaoPagarOuEmitir == 1) {
            int contaOpcaoPagarBoleto = escolhaConta();
            if (contaOpcaoPagarBoleto == 0) {
                String numeroBoleto = JOptionPane.showInputDialog("Digite o número do boleto:");
                try {
                    sb.pagarBoleto(usuario.getContaCorrente().getNumero(), numeroBoleto);
                    JOptionPane.showMessageDialog(null, "Boleto pago com sucesso!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            } else if (contaOpcaoPagarBoleto == 1) {
                String numeroBoleto = JOptionPane.showInputDialog("Digite o número do boleto:");
                try {
                    sb.pagarBoleto(usuario.getContaPoupanca().getNumero(), numeroBoleto);
                    JOptionPane.showMessageDialog(null, "Boleto pago com sucesso!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Opção inválida");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Opção inválida");
        }
    }
}

package br.com.fiap.view;

import javax.swing.JOptionPane;

import br.com.fiap.models.*;

public class Terminal {
    static Usuario usuario = null;

    public static Usuario login(SistemaBancario sb) {
        while (true) {
            String email = JOptionPane.showInputDialog("Digite seu email:");
            if (email == null) {
                System.exit(0);
            }
            String senha = JOptionPane.showInputDialog("Digite sua senha:");
            if (senha == null) {
                System.exit(0);
            }
            usuario = sb.buscarUsuario(email);
            if (usuario == null || !usuario.getSenha().equals(senha)) {
                JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos");
                continue;
            }
            return usuario;
        }
    }

    public static void main(String[] args) {
        SistemaBancario sb = new SistemaBancario();

        Usuario asteriuz = new Usuario("Asteriuz", "augustobb@live.com", "pandorinha",
                new ContaCorrente("123", 1000), new ContaPoupanca("10", 5500));
        Usuario gabriela = new Usuario("Gabriela", "gabs.gnt@hotmail.com", "carlinhos", new ContaCorrente("456"),
                new ContaPoupanca("20"));
        Usuario gribl = new Usuario("Gabs", "gribl88@gmail.com", "maya", new ContaCorrente("789", 1600),
                new ContaPoupanca("30", 1000));
        Usuario queiroz = new Usuario("Queiroz", "queiroz@fiap.com.br", "fiap123", new ContaCorrente("101112", 2000),
                new ContaPoupanca("40", 2000));

        sb.addUsuario(asteriuz);
        sb.addUsuario(gabriela);
        sb.addUsuario(gribl);
        sb.addUsuario(queiroz);

        // login
        // Usuario usuario = null;
        Usuario usuario = login(sb);

        String menu = "1 - Depositar\n2 - Sacar\n3 - Transferir\n4 - Exibir Transações\n5 - Trocar Conta\n6 - Sair\n\nEscolha uma opção:";
        while (true) {
            // info com nome, e saldo das 2 contas
            // verificar se o usuario tem conta corrente e poupança
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
                    JOptionPane.QUESTION_MESSAGE, null,
                    new String[] { "1", "2", "3", "4", "5", "6" }, "1");
            switch (opcao + 1) {
                case 1:
                    int contaOpcaoDepositar = JOptionPane.showOptionDialog(null, "Escolha uma conta:", "Banco FinHive",
                            0,
                            JOptionPane.QUESTION_MESSAGE, null, new String[] { "Conta Corrente", "Conta Poupança" },
                            "Conta Corrente");
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
                    break;
                case 2:
                    int contaOpcaoSacar = JOptionPane.showOptionDialog(null, "Escolha uma conta:", "Banco FinHive", 0,
                            JOptionPane.QUESTION_MESSAGE, null, new String[] { "Conta Corrente", "Conta Poupança" },
                            "Conta Corrente");
                    if (contaOpcaoSacar == 0) {
                        double valor = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor a ser sacado:"));
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
                    break;
                case 3:
                    String numeroDestino = JOptionPane.showInputDialog("Digite o número da conta de destino:");
                    double valor = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor a ser transferido:"));

                    try {
                        sb.transferir(usuario.getContaCorrente().getNumero(), numeroDestino, valor);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                    break;
                case 4:
                    int contaOpcaoExibirTransacoes = JOptionPane.showOptionDialog(null, "Escolha uma conta:",
                            "Banco FinHive", 0,
                            JOptionPane.QUESTION_MESSAGE, null, new String[] { "Conta Corrente", "Conta Poupança" },
                            "Conta Corrente");
                    if (contaOpcaoExibirTransacoes == 0) {
                        JOptionPane.showMessageDialog(null, usuario.getContaCorrente().getTransacoesFormatted());
                    } else if (contaOpcaoExibirTransacoes == 1) {
                        JOptionPane.showMessageDialog(null, usuario.getContaPoupanca().getTransacoesFormatted());
                    } else {
                        JOptionPane.showMessageDialog(null, "Opção inválida");
                    }
                    break;
                case 5:
                    usuario = login(sb);
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.exit(0);
            }
        }
    }
}

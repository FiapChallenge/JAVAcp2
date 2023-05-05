package br.com.fiap.models;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Interface {
    public static int escolhaConta() {
        return JOptionPane.showOptionDialog(null, "Escolha uma conta:", "Banco FinHive", 0,
                JOptionPane.QUESTION_MESSAGE, null, new String[] { "Conta Corrente", "Conta Poupança" },
                "Conta Corrente");
    }

    public static double escolhaValor(String text) throws Exception {
        String valorTxt = JOptionPane.showInputDialog(text);
        if (valorTxt == null) {
            throw new Exception("Operação cancelada");
        } else {
            try {
                return Double.parseDouble(valorTxt);
            } catch (Exception e) {
                throw new Exception("Valor inválido");
            }
        }
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
            double valor;
            try {
                valor = escolhaValor("Digite o valor a ser depositado:");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                return;
            }
            usuario.getContaCorrente().depositar(valor);
        } else if (contaOpcaoDepositar == 1) {
            double valor;
            try {
                valor = escolhaValor("Digite o valor a ser depositado:");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                return;
            }
            usuario.getContaPoupanca().depositar(valor);
        } else {
            JOptionPane.showMessageDialog(null, "Opção inválida");
        }
    }

    public static void sacar(Usuario usuario) {
        int contaOpcaoSacar = escolhaConta();
        if (contaOpcaoSacar == 0) {
            double valor;
            try {
                valor = escolhaValor("Digite o valor a ser sacado:");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                return;
            }
            // System.out.println(valor);
            try {
                usuario.getContaCorrente().sacar(valor);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Saldo Insuficiente");
            }
        } else if (contaOpcaoSacar == 1) {
            double valor;
            try {
                valor = escolhaValor("Digite o valor a ser sacado:");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                return;
            }
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
        if (sb.buscarConta(numeroDestino) == null) {
            JOptionPane.showMessageDialog(null, "Conta não encontrada");
            return;
        }
        double valor;
        try {
            valor = escolhaValor("Digite o valor a ser transferido:");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }

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
                double valorEmitirBoleto;
                try {
                    valorEmitirBoleto = escolhaValor("Digite o valor a ser emitido no boleto:");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    return;
                }
                String numeroBoleto = sb.emitirBoleto(usuario.getContaCorrente().getNumero(),
                        valorEmitirBoleto);
                JOptionPane.showMessageDialog(null,
                        "Boleto emitido com sucesso!\nNúmero do boleto: " + numeroBoleto);
            } else if (contaOpcaoEmitirBoleto == 1) {
                double valorEmitirBoleto;
                try {
                    valorEmitirBoleto = escolhaValor("Digite o valor a ser emitido no boleto:");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    return;
                }
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

    public static void investir(Usuario usuario, SistemaBancario sb) {

        String[] columnNames = { "ID", "Nome", "Valor Inicial", "Juros (s)", "Período (segundos)" };
        Object[][] rowData = new Object[sb.getInvestimentos().size()][5];
        int index = 0;
        for (Investimento investimento : sb.getInvestimentos()) {
            rowData[index][0] = index;
            rowData[index][1] = investimento.getNome();
            rowData[index][2] = investimento.getValorInicial();
            rowData[index][3] = investimento.getJurosPorSegundo();
            rowData[index][4] = investimento.getPeriodoSegundos();
            index++;
        }

        JTable table = new JTable(rowData, columnNames);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table);

        String message = "Por favor, selecione o investimento desejado:";
        Object[] options = { "OK" };
        int result = JOptionPane.showOptionDialog(null, scrollPane, message,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (result == JOptionPane.OK_OPTION) {
            int selectedInvestimento = table.getSelectedRow();
            if (selectedInvestimento == -1) {
                JOptionPane.showMessageDialog(null, "Nenhum investimento selecionado");
                return;
            }
            Investimento investimento = sb.getInvestimentos().get(selectedInvestimento);
            int contaOpcaoInvestir = escolhaConta();
            if (contaOpcaoInvestir == 0) {
                try {
                sb.addInvestimento(usuario, usuario.getContaCorrente(), investimento);}
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            } else if (contaOpcaoInvestir == 1) {
                try {
                sb.addInvestimento(usuario, usuario.getContaPoupanca(), investimento);}
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Opção inválida");
            }
            

        }

        // String investimentosTxt = String.format("%-8s%-50s%-20s%-20s%-20s\n", "ID",
        // "Nome", "Valor Inicial",
        // "Juros (s)", "Periodo (s)");
        // int index = 1;
        // for (Investimento investimento : sb.getInvestimentos()) {
        // investimentosTxt += String.format("%-8s%-50s%-20s%-20s%-20s\n",
        // Integer.toString(index), investimento.getNome(),
        // investimento.getValorInicial(), investimento.getJurosPorSegundo(),
        // investimento.getPeriodoSegundos());
        // index++;
        // }

        // int opcaoInvestir = JOptionPane.showOptionDialog(null, "Escolha uma opção:",
        // "Banco FinHive",
        // 0,
        // JOptionPane.QUESTION_MESSAGE, null, new String[] { "Investir", "Resgatar" },
        // "Investir");
        // if (opcaoInvestir == 0) {
        // int opcaoInvestimento = JOptionPane.showOptionDialog(null,
        // (investimentosTxt), "Banco FinHive", 0,
        // JOptionPane.QUESTION_MESSAGE, null,
        // new String[] { "1", "2", "3" }, "1");
        // }
    }

    public static void assessoria(Usuario usuario, SistemaBancario sb) {

    }
}

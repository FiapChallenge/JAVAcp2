package br.com.fiap.models;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JScrollPane;

public class InterfaceAssessor {
    public static int menu(Assessor assessor) {
        String menu = "1 - Listar Clientes\n2 - Serviços de Assessoria\n3 - Trocar Conta\n4 - Sair\n\nEscolha uma opção:";
        int opcao = JOptionPane.showOptionDialog(null, menu, "Banco FinHive", 0,
                JOptionPane.QUESTION_MESSAGE, assessor.getFoto(),
                new String[] { "1", "2", "3", "4" }, "1");
        return opcao;
    }

    public static Usuario listarClientes(Assessor assessor, SistemaBancario sb) {
        // create table with users that has assessor and design table
        String[] columnNames = { "Nome", "Conta Corrente", "Saldo", "Conta Poupança", "Saldo", "Suspeito",
                "Bloqueado" };
        // list with users that has assessor
        List<Usuario> users = new ArrayList<Usuario>();
        for (Usuario usuario : sb.getUsuarios()) {
            if (usuario.hasAssessor) {
                users.add(usuario);
            }
        }
        Object[][] data = new Object[users.size()][columnNames.length];
        int index = 0;
        for (Usuario usuario : users) {
            data[index][0] = usuario.getNome();
            data[index][1] = usuario.getContaCorrente().getNumero();
            data[index][2] = usuario.getContaCorrente().getSaldo();
            data[index][3] = usuario.getContaPoupanca().getNumero();
            data[index][4] = usuario.getContaPoupanca().getSaldo();
            data[index][5] = usuario.getSuspeito();
            data[index][6] = usuario.getBloqueado();
            index++;
        }
        JTable table = new JTable(data, columnNames);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
                if (table.getValueAt(row, 6).equals(true)) {
                    c.setBackground(Color.LIGHT_GRAY);
                } else {
                    c.setBackground(Color.WHITE);
                }
                if (isSelected) {
                    c.setBackground(new Color(184, 207, 229));
                }
                ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);

                return c;
            }
        });
        table.setDefaultEditor(Object.class, null);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        int opcaoConta = JOptionPane.showOptionDialog(null, scrollPane, "Banco FinHive", 0,
                JOptionPane.QUESTION_MESSAGE, null,
                new String[] { "Acessar conta do usuário", "Exibir Transações Suspeitas", "Voltar" },
                "Voltar");
        if (opcaoConta == 0) {
            int linha = table.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um usuário para acessar a conta");
            } else {
                Usuario usuario = sb.getUsuarios().get(linha);
                return usuario;
            }
        }
        if (opcaoConta == 1) {
            int linha = table.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um usuário para exibir as transações suspeitas");
            } else {
                Usuario usuario = sb.getUsuarios().get(linha);
                String[] columnNames2 = { "Horário", "Valor", "Tipo", "Conta Origem", "Conta Destino" };
                if (usuario.getTransacoesSuspeitas().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Não há transações suspeitas para este usuário");
                    return null;
                }
                Object[][] data2 = new Object[usuario.getTransacoesSuspeitas().size()][columnNames2.length];
                int index2 = 0;
                // exemplo [04:05:49|2000.0|Transferencia feita|123|456]
                for (String transacao : usuario.getTransacoesSuspeitas()) {
                    String[] transacaoSplit = transacao.split("\\|");
                    data2[index2][0] = transacaoSplit[0];
                    data2[index2][1] = transacaoSplit[1];
                    data2[index2][2] = transacaoSplit[2];
                    data2[index2][3] = transacaoSplit[3];
                    data2[index2][4] = transacaoSplit[4];
                    index2++;
                }

                JTable table2 = new JTable(data2, columnNames2);
                // minimo de width pelo nome de coluna
                table2.getColumnModel().getColumn(0).setPreferredWidth(100);
                table2.getColumnModel().getColumn(1).setPreferredWidth(100);
                table2.getColumnModel().getColumn(2).setPreferredWidth(140);
                table2.getColumnModel().getColumn(3).setPreferredWidth(100);
                table2.getColumnModel().getColumn(4).setPreferredWidth(100);
                table2.setDefaultEditor(Object.class, null);
                table2.setPreferredScrollableViewportSize(table2.getPreferredSize());
                table2.setFillsViewportHeight(true);
                DefaultTableCellRenderer centerRenderer2 = new DefaultTableCellRenderer();
                centerRenderer2.setHorizontalAlignment(JLabel.CENTER);
                for (int i = 0; i < table2.getColumnCount(); i++) {
                    table2.getColumnModel().getColumn(i).setCellRenderer(centerRenderer2);
                }
                JScrollPane scrollPane2 = new JScrollPane(table2);
                int opcaoConta2 = JOptionPane.showOptionDialog(null, scrollPane2,
                        "Banco FinHive" + " - " + usuario.getNome(), 0,
                        JOptionPane.QUESTION_MESSAGE, null,
                        new String[] { "Voltar" }, "Voltar");
                if (opcaoConta2 == 0) {
                    return null;
                }
            }
        }
        return null;
    }
}
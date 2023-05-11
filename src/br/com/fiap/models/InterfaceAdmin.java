package br.com.fiap.models;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class InterfaceAdmin {
    public static int menu() {
        String menu = "1 -Sair \n2 - Exibir Usuários\n3 - Ir para o App Normal\n\nEscolha uma opção:";
        ImageIcon profile = new ImageIcon("GFX/profiles/defaultAvatar.png");
        Image image = profile.getImage(); // transform it
        Image newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
        profile = new ImageIcon(newimg);
        int opcao = JOptionPane.showOptionDialog(null, menu, "Banco FinHive", 0,
                JOptionPane.QUESTION_MESSAGE, profile,
                new String[] { "1", "2", "3"}, "1");
        return opcao;
    }

    public static void exibirUsuarios(SistemaBancario sb) {
        // exibir em uma lista
        String[] columnNames = { "Nome", "Conta Corrente", "Saldo", "Conta Poupança", "Saldo", "Suspeito", "Bloqueado" };
        Object[][] data = new Object[sb.getUsuarios().size()][columnNames.length];
        int index = 0;
        for (Usuario usuario : sb.getUsuarios()) {
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
        table.setDefaultEditor(Object.class, null);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(table);
        int opcaoConta = JOptionPane.showOptionDialog(null, scrollPane, "Banco FinHive", 0,
                JOptionPane.QUESTION_MESSAGE, null,
                new String[] { "Bloquear Usuario", "Voltar" }, "Voltar");

        if (opcaoConta == 0) {
            int linha = table.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um usuário para bloquear");
            } else {
                Usuario usuario = sb.getUsuarios().get(linha);
                usuario.setBloqueado(true);
                JOptionPane.showMessageDialog(null, "Usuário bloqueado com sucesso");
            }
        }
        

    }
}

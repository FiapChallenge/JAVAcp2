package br.com.fiap.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.fiap.models.*;

public class Terminal {
    static boolean debug = true;
    static Usuario usuario = null;
    static ImageIcon icon = new ImageIcon("GFX/logo/logo.png");

    public static Usuario login(SistemaBancario sb) {
        while (true) {
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH); 
            icon = new ImageIcon(newimg);

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setPreferredSize(new java.awt.Dimension(200, 100));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.LINE_START;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            panel.add(new JLabel("Digite seu email:"), gbc);
            gbc.gridy++;
            JTextField textField = new JTextField(10);
            textField.setPreferredSize(new java.awt.Dimension(200, 24));
            panel.add(textField, gbc);
            int result = JOptionPane.showOptionDialog(null, panel, "Banco FinHive",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                    icon, new String[] { "OK", "Cadastrar", "Cancelar" }, "OK");

            if (result == 2) {
                System.exit(0);
            }

            if (result == 1) {
                cadastrar(sb);
            } else {

                String email = textField.getText();
                if (email == null) {
                    System.exit(0);
                }
                String senha = (String) JOptionPane.showInputDialog(null, "Digite sua senha:", "Banco FinHive",
                        JOptionPane.QUESTION_MESSAGE, icon, null, null);
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
    }

    public static void cadastrar(SistemaBancario sb) {
        while (true) {
            Image image = icon.getImage(); 
            Image newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH); 
            icon = new ImageIcon(newimg);

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setPreferredSize(new java.awt.Dimension(200, 150));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.LINE_START;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            panel.add(new JLabel("Digite seu nome:"), gbc);
            gbc.gridy++;
            JTextField textField = new JTextField(10);
            textField.setPreferredSize(new java.awt.Dimension(200, 24));
            panel.add(textField, gbc);
            gbc.gridy++;
            panel.add(new JLabel("Digite seu email:"), gbc);
            gbc.gridy++;
            JTextField textField2 = new JTextField(10);
            textField2.setPreferredSize(new java.awt.Dimension(200, 24));
            panel.add(textField2, gbc);
            gbc.gridy++;
            panel.add(new JLabel("Digite sua senha:"), gbc);
            gbc.gridy++;
            JTextField textField3 = new JTextField(10);
            textField3.setPreferredSize(new java.awt.Dimension(200, 24));
            panel.add(textField3, gbc);
            int result = JOptionPane.showOptionDialog(null, panel, "Banco FinHive",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                    icon, new String[] { "OK", "Cancelar" }, "OK");

            if (result == 1) {
                System.exit(0);
            }

            String nome = textField.getText();
            String email = textField2.getText();
            String senha = textField3.getText();
            if (nome == null || email == null || senha == null) {
                System.exit(0);
            }
            if (sb.buscarUsuario(email) != null) {
                JOptionPane.showMessageDialog(null, "Email já cadastrado");
                continue;
            }
            sb.addUsuario(
                    new Usuario(nome, email, senha, sb.createNewContaCorrente(), sb.createNewContaPoupanca(), "GFX/profiles/defaultAvatar.png"));
            JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso");
            return;
        }
    }

    public static void main(String[] args) {

        SistemaBancario sb = new SistemaBancario();

        /* -------------------------------------------------------------------------- */
        /* Adding Investimentos */
        /* -------------------------------------------------------------------------- */

        sb.addInvestimentoToList(new Investimento("MercadoPago", 500, 0.05, 5));
        sb.addInvestimentoToList(new Investimento("Blizzard", 1000, 0.01, 12));
        sb.addInvestimentoToList(new Investimento("Riot", 1500, 0.02, 20));
        sb.addInvestimentoToList(new Investimento("Microsoft", 2000, 0.5, 30));
        sb.addInvestimentoToList(new Investimento("Apple", 2500, 0.75, 40));
        sb.addInvestimentoToList(new Investimento("Google", 3000, 0.9, 50));
        sb.addInvestimentoToList(new Investimento("Facebook", 3500, 0.95, 60));

        /* -------------------------------------------------------------------------- */
        /* Adding Usuarios */
        /* -------------------------------------------------------------------------- */

        // Usuario asteriuz = new Usuario("Asteriuz", "augustobb@live.com",
        // "pandorinha",
        // new ContaCorrente("123", 1000), new ContaPoupanca("10", 5500),
        // "GFX/profiles/asteriuz.jpg");
        // Usuario gabriela = new Usuario("Gabriela", "gabs.gnt@hotmail.com",
        // "carlinhos", new ContaCorrente("456"),
        // new ContaPoupanca("20"), "GFX/profiles/gabs.jpg");
        // Usuario gribl = new Usuario("Gabs", "gribl88@gmail.com", "maya", new
        // ContaCorrente("789", 1600),
        // new ContaPoupanca("30", 1000), "GFX/profiles/gribl.jpg");
        // Usuario queiroz = new Usuario("Queiroz", "queiroz@fiap.com.br", "fiap123",
        // new ContaCorrente("101112", 2000),
        // new ContaPoupanca("40", 2000), "GFX/profiles/queiroz.jpg");

        // sb.addUsuario(asteriuz);
        // sb.addUsuario(gabriela);
        // sb.addUsuario(gribl);
        // sb.addUsuario(queiroz);

        // sb.saveData();

        String filePath = "data.txt";
        List<List<String>> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\\s+");
                List<String> row = Arrays.asList(values);
                data.add(row);
            }
        } catch (IOException e) {
            System.out.println("Error importing file: " + e.getMessage());
        }

        for (List<String> row : data) {
            sb.addUsuario(new Usuario(row.get(0), row.get(1), row.get(2),
                    new ContaCorrente(row.get(3), Double.parseDouble(row.get(4))),
                    new ContaPoupanca(row.get(5), Double.parseDouble(row.get(6))), row.get(7)));
        }

        /* -------------------------------------------------------------------------- */
        /* Login and Entering Menu */
        /* -------------------------------------------------------------------------- */

        if (debug) {
            usuario = sb.getUsuarios().get(0);
        } else {
            usuario = login(sb);
        }

        String menu = "1 - Depositar\n2 - Sacar\n3 - Transferir\n4 - Exibir Transações\n5 - Emitir/Pagar Boleto\n6 - Investimento\n7 - Serviço Assessoria\n8 - Trocar Conta\n9 - Sair\n\nEscolha uma opção:";
        while (true) {
            int opcao = Interface.menu(menu, usuario);
            switch (opcao + 1) {
                case 1:
                    Interface.depositar(usuario);
                    break;
                case 2:
                    Interface.sacar(usuario);
                    break;
                case 3:
                    Interface.transferir(usuario, sb);
                    break;
                case 4:
                    Interface.relatorio(usuario);
                    break;
                case 5:
                    Interface.boleto(usuario, sb);
                    break;
                case 6:
                    Interface.investir(usuario, sb);
                    break;
                case 7:
                    Interface.assessoria(usuario, sb);
                    break;
                case 8:
                    usuario = login(sb);
                    break;
                case 9:
                    sb.saveData();
                    System.exit(0);
                default:
                    sb.saveData();
                    System.exit(0);
            }
        }
    }
}

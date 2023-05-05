package br.com.fiap.view;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import br.com.fiap.models.*;

public class Terminal {
    static boolean debug = true;
    static Usuario usuario = null;
    static ImageIcon icon = new ImageIcon("GFX/logo/logo.png");

    public static Usuario login(SistemaBancario sb) {
        while (true) {
            Image image = icon.getImage(); // transform it
            Image newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            icon = new ImageIcon(newimg);
            String email = (String) JOptionPane.showInputDialog(null, "Digite seu e-mail", "Banco FinHive",
                    JOptionPane.QUESTION_MESSAGE, icon, null, null);
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

    public static void main(String[] args) {

        SistemaBancario sb = new SistemaBancario();

        /* -------------------------------------------------------------------------- */
        /*                             Adding Investimentos                           */
        /* -------------------------------------------------------------------------- */

        sb.addInvestimentoToList(new Investimento("MercadoPago", 500, 0.05, 5));
        sb.addInvestimentoToList(new Investimento("Blizzard", 1000, 0.01, 12));
        sb.addInvestimentoToList(new Investimento("Riot", 1500, 0.02, 20));
        sb.addInvestimentoToList(new Investimento("Microsoft", 2000, 0.5, 30));

        /* -------------------------------------------------------------------------- */
        /*                              Adding Usuarios                               */
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
        /*                           Login and Entering Menu                          */
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.UIManager;

import br.com.fiap.models.*;

public class App {
    static boolean debug = true;

    public static void main(String[] args) throws IOException {
        UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
        Usuario usuarioLogado = null;

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

        String filePath = "./data.txt";
        try (InputStream in = App.class.getResourceAsStream("/data.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            // Use resource
        }
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
            usuarioLogado = sb.getUsuarios().get(0);
        } else {
            usuarioLogado = Interface.login(sb);
        }

        String menu = "1 - Depositar\n2 - Sacar\n3 - Transferir\n4 - Exibir Transações\n5 - Emitir/Pagar Boleto\n6 - Investimento\n7 - Serviço Assessoria\n8 - Trocar Conta\n9 - Sair\n\nEscolha uma opção:";
        while (true) {
            int opcao = Interface.menu(menu, usuarioLogado);
            switch (opcao + 1) {
                case 1:
                    Interface.depositar(usuarioLogado);
                    break;
                case 2:
                    Interface.sacar(usuarioLogado);
                    break;
                case 3:
                    Interface.transferir(usuarioLogado, sb);
                    break;
                case 4:
                    Interface.relatorio(usuarioLogado);
                    break;
                case 5:
                    Interface.boleto(usuarioLogado, sb);
                    break;
                case 6:
                    Interface.investir(usuarioLogado, sb);
                    break;
                case 7:
                    Interface.assessoria(usuarioLogado, sb);
                    break;
                case 8:
                    usuarioLogado = Interface.login(sb);
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

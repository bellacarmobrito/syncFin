package br.com.fiap.syncfin.teste;

import br.com.fiap.syncfin.dao.CadastroDao;
import br.com.fiap.syncfin.dao.EnderecoDao;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.Endereco;
import br.com.fiap.syncfin.teste.cadastro.MenuCadastro;
import br.com.fiap.syncfin.teste.conta.MenuContaBancaria;
import br.com.fiap.syncfin.teste.despesa.MenuDespesa;
import br.com.fiap.syncfin.teste.investimento.MenuInvestimento;
import br.com.fiap.syncfin.teste.receita.MenuReceita;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Teste {

    private static void popularBancoInicial() {
        System.out.println("--- Populando banco com dados iniciais ---");
        CadastroDao cadastroDao;
        EnderecoDao enderecoDao;

        try {
            cadastroDao = new CadastroDao();
            enderecoDao = new EnderecoDao();

            List<Endereco> enderecos = new ArrayList<>();
            enderecos.add(new Endereco("Rua das Flores", 10, "Jardim", "12345-010", "Florianópolis", "SC"));
            enderecos.add(new Endereco("Avenida Principal", 250, "Centro", "88010-100", "São Paulo", "SP"));
            enderecos.add(new Endereco("Travessa das Palmeiras", 33, "Boa Vista", "50070-080", "Recife", "PE"));
            enderecos.add(new Endereco("Alameda dos Anjos", 777, "Paraíso", "04004-000", "Belo Horizonte", "MG"));
            enderecos.add(new Endereco("Praça da Liberdade", 1, "Savassi", "30140-010", "Curitiba", "PR"));

            List<Cadastro> cadastros = new ArrayList<>();
            cadastros.add(new Cadastro("Jordana Silva", enderecos.get(0), "48911112222", "11122233344", "ana.silva@email.com", "senha123", "Ativa"));
            cadastros.add(new Cadastro("Marisete Elias", enderecos.get(1), "11988887777", "22233344455", "bruno.costa@email.com", "senha456", "Ativa"));
            cadastros.add(new Cadastro("Maria Clara Jonnas", enderecos.get(2), "81999998888", "33344455566", "carla.dias@email.com", "senha789", "Inativa"));
            cadastros.add(new Cadastro("Gomes Costa", enderecos.get(3), "31977776666", "44455566677", "daniel.faria@email.com", "senha101", "Ativa"));
            cadastros.add(new Cadastro("Felix Smith", enderecos.get(4), "41966665555", "55566677788", "elisa.gomes@email.com", "senha202", "Ativa"));

            int cadastrosRealizados = 0;
            for (int i = 0; i < cadastros.size(); i++) {
                Cadastro c = cadastros.get(i);
                Endereco e = enderecos.get(i);
                try {
                    int idCliente = cadastroDao.cadastrar(c);
                    enderecoDao.cadastrarEndereco(e, idCliente);
                    System.out.println("-> Cadastro '" + c.getNomeCliente() + "' e endereço inseridos com sucesso (ID Cliente: " + idCliente + ").");
                    cadastrosRealizados++;
                } catch (SQLException ex) {
                    System.err.println("Erro ao inserir cadastro: " + ex.getMessage());
                }
            }
            System.out.println("--- " + cadastrosRealizados + " de " + cadastros.size() + " cadastros inseridos. ---");

        } catch (SQLException e) {
            System.err.println("Erro ao popular o banco: " + e.getMessage());
        }
    }

    public static void main( String[] args ) {

        popularBancoInicial();
        Scanner scanner = new Scanner(System.in);

        int opcao;

        do {
            System.out.println("\n=== SEJA BEM VINDO A SYNCFIN ===");
            System.out.println("-------------------------------------------");
            System.out.println("Selecione a opção desejada: ");
            System.out.println("-------------------------------------------");
            System.out.println("1 - Cadastro");
            System.out.println("2 - Conta Bancária");
            System.out.println("3 - Despesa");
            System.out.println("4 - Receita");
            System.out.println("5 - Investimento");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    MenuCadastro.exibirMenu(scanner);
                    break;
                case 2:
                    MenuContaBancaria.exibirMenu(scanner);
                    break;
                case 3:
                    MenuDespesa.exibirMenu(scanner);
                    break;
                case 4:
                    MenuReceita.exibirMenu(scanner);
                    break;
                case 5:
                    MenuInvestimento.exibirMenu(scanner);
                    break;
                case 0:
                    System.out.println("Saindo do SYNCFIN. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }
}

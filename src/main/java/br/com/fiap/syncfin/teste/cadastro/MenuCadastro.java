package br.com.fiap.syncfin.teste.cadastro;

import br.com.fiap.syncfin.dao.CadastroDao;
import br.com.fiap.syncfin.dao.EnderecoDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.Endereco;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuCadastro {

    public static void exibirMenu(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- MENU CADASTRO ---");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Listar Clientes");
            System.out.println("3 - Pesquisar Cliente");
            System.out.println("4 - Atualizar Cliente");
            System.out.println("5 - Remover Cliente");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrar(scanner);
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    pesquisar(scanner);
                    break;
                case 4:
                    atualizar(scanner);
                    break;
                case 5:
                    remover(scanner);
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void cadastrar(Scanner scanner) {
        try {
            CadastroDao dao = new CadastroDao();
            EnderecoDao enderecoDao = new EnderecoDao();

            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Celular: ");
            String celular = scanner.nextLine();
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();
            System.out.print("Status (Ativa/Inativa): ");
            String status = scanner.nextLine();

            System.out.println("\n--- Endereço ---");
            System.out.print("Rua: ");
            String rua = scanner.nextLine();
            System.out.print("Número: ");
            int numero = scanner.nextInt(); scanner.nextLine();
            System.out.print("Bairro: ");
            String bairro = scanner.nextLine();
            System.out.print("CEP: ");
            String cep = scanner.nextLine();
            System.out.print("Cidade: ");
            String cidade = scanner.nextLine();
            System.out.print("UF: ");
            String uf = scanner.nextLine();

            Endereco endereco = new Endereco(rua, numero, bairro, cep, cidade, uf);
            Cadastro cadastro = new Cadastro(nome, endereco, celular, cpf, email, senha, status);

            int id = dao.cadastrar(cadastro);
            enderecoDao.cadastrarEndereco(endereco, id);

            dao.fecharConexao();
            enderecoDao.fecharConexao();

            System.out.println("Cliente cadastrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    private static void listar() {
        try {
            CadastroDao dao = new CadastroDao();
            List<Cadastro> clientes = dao.getAll();
            for (Cadastro cadastro : clientes) {
                System.out.println("Lista de Clientes Cadastrados: "  + clientes.size());
                System.out.println("-------------------------------------------");
                System.out.println("ID: " + cadastro.getIdCliente());
                System.out.println("Nome: " + cadastro.getNomeCliente());
                System.out.println("Celular: " + cadastro.getCelular());
                System.out.println("CPF: " + cadastro.getCpf());
                System.out.println("E-mail: " + cadastro.getEmail());
                System.out.println("Senha: " + cadastro.getSenha());
                System.out.println("Data de Cadastro: " + cadastro.getDataCadastro());
                System.out.println("Status da Conta: " + (cadastro.isStatusConta() ? "Ativa" : "Inativa"));
                System.out.println("-------------------------------------------");
            }
            dao.fecharConexao();
        } catch (SQLException e) {
            System.err.println("Erro ao listar: " + e.getMessage());
        }
    }

    private static void pesquisar(Scanner scanner) {
        try {
            CadastroDao dao = new CadastroDao();
            System.out.print("Digite o ID do cliente: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Cadastro cadastro = dao.pesquisar(id);
            System.out.println("Detalhamento do Cadastro");
            System.out.println("-------------------------------------------");
            System.out.println("ID: " + cadastro.getIdCliente());
            System.out.println("Nome: " + cadastro.getNomeCliente());
            System.out.println("Celular: " + cadastro.getCelular());
            System.out.println("CPF: " + cadastro.getCpf());
            System.out.println("E-mail: " + cadastro.getEmail());
            System.out.println("Senha: " + cadastro.getSenha());
            System.out.println("Data de Cadastro: " + cadastro.getDataCadastro());
            System.out.println("Status da Conta: " + (cadastro.isStatusConta() ? "Ativa" : "Inativa"));
            System.out.println("-------------------------------------------");
            dao.fecharConexao();
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Nenhum cadastro localizado para o ID informado.");
        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar: " + e.getMessage());
        }
    }

    private static void atualizar(Scanner scanner) {
        try {
            CadastroDao dao = new CadastroDao();
            System.out.print("ID do cliente a atualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Novo nome: ");
            String nome = scanner.nextLine();
            System.out.print("Novo celular: ");
            String celular = scanner.nextLine();
            System.out.print("Novo CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Novo email: ");
            String email = scanner.nextLine();
            System.out.print("Nova senha: ");
            String senha = scanner.nextLine();

            Cadastro cadastro = new Cadastro(id, nome, celular, cpf, email, senha, null, "Ativa");

            dao.atualizar(cadastro);
            dao.fecharConexao();

            System.out.println("Cliente atualizado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    private static void remover(Scanner scanner) {
        try {
            CadastroDao dao = new CadastroDao();
            System.out.print("ID do cliente a remover: ");
            int id = scanner.nextInt();
            System.out.println("A exclusão do cadastro não está disponível. Cadastros podem ser apenas inativados.");

            scanner.nextLine();

            dao.inativarCadastro(id);
            dao.fecharConexao();

            System.out.println("Cliente inativado com sucesso.");
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Nenhum cadastro localizado para o ID informado.");
        } catch (SQLException e) {
            System.err.println("Erro ao remover: " + e.getMessage());
        }
    }
}

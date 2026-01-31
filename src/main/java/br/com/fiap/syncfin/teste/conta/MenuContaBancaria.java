package br.com.fiap.syncfin.teste.conta;

import br.com.fiap.syncfin.dao.ContaBancariaDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.ContaBancaria;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuContaBancaria {

    public static void exibirMenu(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- MENU CONTA BANCÁRIA ---");
            System.out.println("1 - Cadastrar Conta");
            System.out.println("2 - Listar Contas");
            System.out.println("3 - Pesquisar Conta por ID");
            System.out.println("4 - Atualizar Conta");
            System.out.println("5 - Remover Conta");
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
            ContaBancariaDao dao = new ContaBancariaDao();

            System.out.print("ID do Cliente: ");
            int idCliente = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Banco: ");
            String nomeInstituicao = scanner.nextLine();
            System.out.print("Agência: ");
            String agencia = scanner.nextLine();
            System.out.print("Número da Conta: ");
            String numeroConta = scanner.nextLine();
            System.out.print("Informe o tipo de Conta: (Corrente/Poupança) ");
            String tipoConta = scanner.nextLine();
            System.out.print("Saldo Inicial: ");
            double saldo = scanner.nextDouble();
            scanner.nextLine();

            ContaBancaria conta = new ContaBancaria(idCliente, nomeInstituicao, agencia, tipoConta, numeroConta, saldo);

            dao.cadastrarConta(conta);
            dao.fecharConexao();

            System.out.println("Conta bancária cadastrada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar conta: " + e.getMessage());
        }
    }

    private static void listar() {
        try {
            ContaBancariaDao dao = new ContaBancariaDao();
            List<ContaBancaria> contas = dao.getAllContas();
            System.out.println("Lista de Contas Bancárias Cadastradas: " + contas.size());
            for (ContaBancaria conta : contas) {
                System.out.println("-------------------------------------------");
                System.out.println("ID Conta: " + conta.getIdConta());
                System.out.println("ID Cliente: " + conta.getCliente().getIdCliente());
                System.out.println("Banco: " + conta.getNomeInstituicao());
                System.out.println("Agência: " + conta.getAgencia());
                System.out.println("Número: " + conta.getNumeroConta());
                System.out.println("Saldo: R$ " + conta.getSaldo());
                System.out.println("-------------------------------------------");
            }
            dao.fecharConexao();
        } catch (SQLException e) {
            System.err.println("Erro ao listar contas: " + e.getMessage());
        }
    }

    private static void pesquisar(Scanner scanner) {
        try {
            ContaBancariaDao dao = new ContaBancariaDao();
            System.out.print("Digite o ID da conta: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            ContaBancaria conta = dao.pesquisarContaPorId(id);
            System.out.println("Detalhamento da Conta Bancária");
            System.out.println("-------------------------------------------");
            System.out.println("ID Conta: " + conta.getIdConta());
            System.out.println("ID Cliente: " + conta.getCliente().getIdCliente());
            System.out.println("Banco: " + conta.getNomeInstituicao());
            System.out.println("Agência: " + conta.getAgencia());
            System.out.println("Número: " + conta.getNumeroConta());
            System.out.println("Saldo: R$ " + conta.getSaldo());
            System.out.println("-------------------------------------------");

            dao.fecharConexao();
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Nenhuma conta localizada para o ID informado.");
        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar conta: " + e.getMessage());
        }
    }

    private static void atualizar(Scanner scanner) {
        try {
            ContaBancariaDao dao = new ContaBancariaDao();

            System.out.print("ID da conta a atualizar: ");
            int idConta = scanner.nextInt();
            scanner.nextLine();

            ContaBancaria contaParaAtualizar = dao.pesquisarContaPorId(idConta);

            System.out.println("--- Atualizando Conta ID: " + contaParaAtualizar.getIdConta() + " (Cliente ID: " + contaParaAtualizar.getCliente().getIdCliente() + ") ---");

            System.out.print("Novo Banco (" + contaParaAtualizar.getNomeInstituicao() + "): ");
            String nomeInstituicao = scanner.nextLine();
            System.out.print("Nova Agência (" + contaParaAtualizar.getAgencia() + "): ");
            String agencia = scanner.nextLine();
            System.out.print("Novo Número da Conta (" + contaParaAtualizar.getNumeroConta() + "): ");
            String numeroConta = scanner.nextLine();
            System.out.print("Novo Tipo de Conta (" + contaParaAtualizar.getTipoConta() + "): ");
            String tipoConta = scanner.nextLine();
            System.out.print("Novo Saldo (Atual: " + contaParaAtualizar.getSaldo() + "): ");

            double saldo = scanner.nextDouble();
            scanner.nextLine();
            contaParaAtualizar.setNomeInstituicao(nomeInstituicao);
            contaParaAtualizar.setAgencia(agencia);
            contaParaAtualizar.setNumeroConta(numeroConta);
            contaParaAtualizar.setTipoConta(tipoConta);
            contaParaAtualizar.setSaldo(saldo);
            dao.atualizarConta(contaParaAtualizar);
            dao.fecharConexao();

            System.out.println("Conta bancária atualizada com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar conta: " + e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Nenhuma conta localizada para o ID informado.");
        }
    }

    private static void remover(Scanner scanner) {
        try {
            ContaBancariaDao dao = new ContaBancariaDao();
            System.out.print("ID da conta a remover: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            dao.removerConta(id);
            dao.fecharConexao();

            System.out.println("Conta bancária removida com sucesso.");
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Nenhuma conta localizada para o ID informado.");
        } catch (SQLException e) {
            System.err.println("Erro ao remover conta: " + e.getMessage());
        }
    }
}

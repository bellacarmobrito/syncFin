package br.com.fiap.syncfin.teste.receita;

import br.com.fiap.syncfin.dao.ContaBancariaDao;
import br.com.fiap.syncfin.dao.ReceitaDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.ContaBancaria;
import br.com.fiap.syncfin.model.Receita;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuReceita {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public static void exibirMenu(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- MENU RECEITA ---");
            System.out.println("1 - Cadastrar Receita");
            System.out.println("2 - Listar Receitas");
            System.out.println("3 - Pesquisar Receita por ID");
            System.out.println("4 - Atualizar Receita");
            System.out.println("5 - Remover Receita");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

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
                    if (opcao != -1) {
                        System.out.println("Opção inválida.");
                    }
            }
        } while (opcao != 0);
    }

    private static void cadastrar(Scanner scanner) {
        ReceitaDao receitaDao;
        ContaBancariaDao contaDao;

        try {
            receitaDao = new ReceitaDao();
            contaDao = new ContaBancariaDao();

            System.out.println("\n--- Cadastrar Nova Receita ---");

            System.out.print("Digite o ID da Conta Bancária associada: ");
            int idConta = scanner.nextInt();
            scanner.nextLine();
            ContaBancaria contaAssociada = contaDao.pesquisarContaPorId(idConta);

            System.out.print("Valor da Receita: ");
            double valor = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Categoria da Receita: ");
            String categoria = scanner.nextLine();

            System.out.print("Data de Recebimento (Formato: 2025-04-22): ");
            String dataRecebimentoStr = scanner.nextLine();
            LocalDate dataRecebimento = LocalDate.parse(dataRecebimentoStr, DATE_FORMATTER);

            System.out.print("Descrição/Observação: ");
            String descricao = scanner.nextLine();

            System.out.print("Status inicial (Ex: Pendente, Recebido): ");
            String status = scanner.nextLine();

            Receita novaReceita = new Receita();
            novaReceita.setContaBancaria(contaAssociada);
            novaReceita.setValor(valor);
            novaReceita.setCategoria(categoria);
            novaReceita.setDataRecebimento(dataRecebimento);
            novaReceita.setDescricao(descricao);
            novaReceita.setStatus(status);

            receitaDao.cadastrarReceita(novaReceita);

            System.out.println("Receita cadastrada com sucesso!");

        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Não foi localizado nenhum cadastro para o ID informado.");
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao cadastrar receita: " + e.getMessage());
        }
    }

    private static void listar() {
        ReceitaDao dao;
        try {
            dao = new ReceitaDao();
            List<Receita> receitas = dao.getAllReceitas();

            if (receitas.isEmpty()) {
                System.out.println("Nenhuma receita encontrada.");
                return;
            }

            System.out.println("\n--- Lista de Receitas Cadastradas (" + receitas.size() + ") ---");
            for (Receita receita : receitas) {

                receita.exibirTransacao();

                System.out.println("   ID Conta: " + receita.getContaBancaria().getIdConta());
                if (receita.getContaBancaria().getCliente() != null) {
                    System.out.println("   ID Cliente: " + receita.getContaBancaria().getCliente().getIdCliente());
                }
                System.out.println("-------------------------------------------");

            }
            dao.fecharConexao();
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao listar receitas: " + e.getMessage());
        }
    }

    private static void pesquisar(Scanner scanner) {
        ReceitaDao dao;
        try {
            dao = new ReceitaDao();
            System.out.print("Digite o ID da Receita a pesquisar: ");
            int idReceita = scanner.nextInt();
            scanner.nextLine();

            Receita receita = dao.pesquisarReceitaPorId(idReceita);

            System.out.println("\n--- Detalhamento da Receita ---");
            receita.exibirTransacao();
            System.out.println("   ID Conta Associada: " + receita.getContaBancaria().getIdConta());
            if (receita.getContaBancaria().getCliente() != null) {
                System.out.println("   ID Cliente Associado: " + receita.getContaBancaria().getCliente().getIdCliente());
            }
            System.out.println("-------------------------------------------");

            dao.fecharConexao();

        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Não foi localizada nenhuma receita para o ID informado.");
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao pesquisar receita: " + e.getMessage());
        }
    }

    private static void atualizar(Scanner scanner) {
        ReceitaDao receitaDao;

        try {
            receitaDao = new ReceitaDao();

            System.out.print("Digite o ID da Receita a atualizar: ");
            int idReceita = scanner.nextInt();
            scanner.nextLine();

            Receita receitaParaAtualizar = receitaDao.pesquisarReceitaPorId(idReceita);

            System.out.println("\n--- Atualizando Receita ID: " + idReceita + " ---");

            System.out.print("Novo Valor (Atual: " + receitaParaAtualizar.getValor() + "): ");
            String novoValorStr = scanner.nextLine();
            receitaParaAtualizar.setValor(Double.parseDouble(novoValorStr));

            System.out.print("Nova Categoria (Atual: " + receitaParaAtualizar.getCategoria() + "): ");
            String novaCategoria = scanner.nextLine();
            receitaParaAtualizar.setCategoria(novaCategoria);

            System.out.print("Nova Data de Recebimento (Atual: " + (receitaParaAtualizar.getDataRecebimento() != null ? receitaParaAtualizar.getDataRecebimento().format(DATE_FORMATTER) : "N/A") + ", Formato: yyyy-MM-dd): ");
            String novaDataStr = scanner.nextLine();
            receitaParaAtualizar.setDataRecebimento(LocalDate.parse(novaDataStr, DATE_FORMATTER));

            System.out.print("Nova Descrição (Atual: " + receitaParaAtualizar.getDescricao() + "): ");
            String novaDescricao = scanner.nextLine();
            receitaParaAtualizar.setDescricao(novaDescricao);

            System.out.print("Novo Status (Atual: " + receitaParaAtualizar.getStatus() + "): ");
            String novoStatus = scanner.nextLine();
            receitaParaAtualizar.setStatus(novoStatus);

            receitaDao.atualizarReceita(receitaParaAtualizar);
            System.out.println("Receita atualizada com sucesso!");

        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Não foi localizada nenhuma receita para o ID informado.");
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao atualizar receita: " + e.getMessage());
        }
    }

    private static void remover(Scanner scanner) {
        ReceitaDao receitaDao;

        try {
            receitaDao = new ReceitaDao();
            System.out.print("Digite o ID da Receita a remover: ");
            int idReceita = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Tem certeza que deseja remover a receita ID " + idReceita + "? (S/N): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                receitaDao.deletarReceita(idReceita);
                System.out.println("Receita removida com sucesso.");
            } else {
                System.out.println("Remoção cancelada.");
            }
            receitaDao.fecharConexao();
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Erro ao remover: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao remover receita: " + e.getMessage());
        }
    }
}
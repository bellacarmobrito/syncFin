package br.com.fiap.syncfin.teste.despesa;

import br.com.fiap.syncfin.dao.ContaBancariaDao;
import br.com.fiap.syncfin.dao.DespesaDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.ContaBancaria;
import br.com.fiap.syncfin.model.Despesa;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuDespesa {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public static void exibirMenu(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- MENU DESPESA ---");
            System.out.println("1 - Cadastrar Despesa");
            System.out.println("2 - Listar Despesas");
            System.out.println("3 - Pesquisar Despesa por ID");
            System.out.println("4 - Atualizar Despesa");
            System.out.println("5 - Remover Despesa");
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
                    if(opcao != -1) {
                        System.out.println("Opção inválida.");
                    }
            }
        } while (opcao != 0);
    }

    private static void cadastrar(Scanner scanner) {

        DespesaDao despesaDao;
        ContaBancariaDao contaDao;

        try {
            despesaDao = new DespesaDao();
            contaDao = new ContaBancariaDao();

            System.out.println("\n--- Cadastrar Nova Despesa ---");

            System.out.print("Digite o ID da Conta Bancária associada: ");
            int idConta = scanner.nextInt();
            scanner.nextLine();

            ContaBancaria contaAssociada = contaDao.pesquisarContaPorId(idConta);
            System.out.print("Valor da Despesa: ");
            double valor = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Categoria da Despesa: ");
            String categoria = scanner.nextLine();

            System.out.print("Data de Vencimento (Formato: 2025-04-22 " + DATE_FORMATTER.toString().replace("ISO_LOCAL_DATE","yyyy-MM-dd") + "): ");
            String dataVencimentoStr = scanner.nextLine();
            LocalDate dataVencimento = LocalDate.parse(dataVencimentoStr, DATE_FORMATTER);

            System.out.print("Descrição/Observação: ");
            String descricao = scanner.nextLine();

            System.out.print("Status inicial (Ex: Pendente, Pago, Vencida): ");
            String status = scanner.nextLine();

            Despesa novaDespesa = new Despesa();
            novaDespesa.setContaBancaria(contaAssociada);
            novaDespesa.setValor(valor);
            novaDespesa.setCategoria(categoria);
            novaDespesa.setVencimento(dataVencimento);
            novaDespesa.setDescricao(descricao);
            novaDespesa.setStatus(status);

            despesaDao.cadastrarDespesa(novaDespesa);
            despesaDao.fecharConexao();

            System.out.println("Despesa cadastrada com sucesso!");

        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Não foi localizado nenhum cadastro para o ID informado.");
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao cadastrar despesa: " + e.getMessage());
        }
    }

    private static void listar() {
        DespesaDao dao;
        try {
            dao = new DespesaDao();
            List<Despesa> despesas = dao.getAllDespesas();

            if (despesas.isEmpty()) {
                System.out.println("Nenhuma despesa encontrada.");
                return;
            }

            System.out.println("\n--- Lista de Despesas Cadastradas (" + despesas.size() + ") ---");
            for (Despesa despesa : despesas) {
                System.out.println("-------------------------------------------");
                System.out.println("ID Despesa: " + despesa.getId());

                System.out.println("ID Conta: " + despesa.getContaBancaria().getIdConta());

                if (despesa.getContaBancaria().getCliente() != null) {
                    System.out.println("ID Cliente: " + despesa.getContaBancaria().getCliente().getIdCliente());
                }

                System.out.println("Valor: R$ " + despesa.getValor());
                System.out.println("Categoria: " + despesa.getCategoria());
                System.out.println("Vencimento: " + (despesa.getVencimento() != null ? despesa.getVencimento().format(DATE_FORMATTER) : "N/A"));
                System.out.println("Descrição: " + despesa.getDescricao());
                System.out.println("Status: " + despesa.getStatus());
            }
            dao.fecharConexao();
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao listar despesas: " + e.getMessage());
        }
    }

    private static void pesquisar(Scanner scanner) {
        DespesaDao dao;
        try {
            dao = new DespesaDao();
            System.out.print("Digite o ID da Despesa a pesquisar: ");
            int idDespesa = scanner.nextInt();
            scanner.nextLine();

            Despesa despesa = dao.pesquisarDespesaPorId(idDespesa);

            System.out.println("\n--- Detalhamento da Despesa ---");
            System.out.println("-------------------------------------------");
            System.out.println("ID Despesa: " + despesa.getId());
            System.out.println("ID Conta: " + despesa.getContaBancaria().getIdConta());

            if (despesa.getContaBancaria().getCliente() != null) {
                System.out.println("ID Cliente: " + despesa.getContaBancaria().getCliente().getIdCliente());
            }

            System.out.println("Valor: R$ " + despesa.getValor());
            System.out.println("Categoria: " + despesa.getCategoria());
            System.out.println("Vencimento: " + (despesa.getVencimento() != null ? despesa.getVencimento().format(DATE_FORMATTER) : "N/A"));
            System.out.println("Descrição: " + despesa.getDescricao());
            System.out.println("Status: " + despesa.getStatus());
            System.out.println("-------------------------------------------");

            dao.fecharConexao();
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Não foi localizada nenhuma despesa para o ID informado.");
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao pesquisar despesa: " + e.getMessage());
        }
    }

    private static void atualizar(Scanner scanner) {
        DespesaDao dao;
        try {
            dao = new DespesaDao();

            System.out.print("Digite o ID da Despesa a atualizar: ");
            int idDespesa = scanner.nextInt();
            scanner.nextLine();

            Despesa despesaParaAtualizar = dao.pesquisarDespesaPorId(idDespesa);

            System.out.println("\n--- Atualizando Despesa ID: " + idDespesa + " ---");

            System.out.print("Novo Valor (Atual: " + despesaParaAtualizar.getValor() + "): ");
            String novoValorStr = scanner.nextLine();
            despesaParaAtualizar.setValor(Double.parseDouble(novoValorStr));

            System.out.print("Nova Categoria (Atual: " + despesaParaAtualizar.getCategoria() + "): ");
            String novaCategoria = scanner.nextLine();
            despesaParaAtualizar.setCategoria(novaCategoria);

            System.out.print("Nova Data de Vencimento (Atual: " + (despesaParaAtualizar.getVencimento() != null ? despesaParaAtualizar.getVencimento().format(DATE_FORMATTER) : "N/A") + ", Formato: yyyy-MM-dd): ");
            String novaDataStr = scanner.nextLine();
            despesaParaAtualizar.setVencimento(LocalDate.parse(novaDataStr, DATE_FORMATTER));

            System.out.print("Nova Descrição (Atual: " + despesaParaAtualizar.getDescricao() + "): ");
            String novaDescricao = scanner.nextLine();
            despesaParaAtualizar.setDescricao(novaDescricao);

            System.out.print("Novo Status (Atual: " + despesaParaAtualizar.getStatus() + "): ");
            String novoStatus = scanner.nextLine();
            despesaParaAtualizar.setStatus(novoStatus);

            dao.atualizarDespesa(despesaParaAtualizar);

            System.out.println("Despesa atualizada com sucesso!");

        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Não foi localizada nenhuma despesa para o ID informado.");
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao atualizar despesa: " + e.getMessage());
        }
    }

    private static void remover(Scanner scanner) {
        DespesaDao dao;
        try {
            dao = new DespesaDao();
            System.out.print("Digite o ID da Despesa a remover: ");
            int idDespesa = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Tem certeza que deseja remover a despesa ID " + idDespesa + "? (S/N): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                dao.deletarDespesa(idDespesa);
                System.out.println("Despesa removida com sucesso.");
            } else {
                System.out.println("Remoção cancelada.");
            }
            dao.fecharConexao();
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Não foi localizada nenhuma despesa para o ID informado.");
        } catch (SQLException e) {
            System.err.println("Erro ao remover: " + e.getMessage());

        }
    }
}
package br.com.fiap.syncfin.teste.investimento;

import br.com.fiap.syncfin.dao.ContaBancariaDao;
import br.com.fiap.syncfin.dao.InvestimentoDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.ContaBancaria;
import br.com.fiap.syncfin.model.Investimento;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuInvestimento {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public static void exibirMenu(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- MENU INVESTIMENTO ---");
            System.out.println("1 - Cadastrar Investimento");
            System.out.println("2 - Listar Investimentos");
            System.out.println("3 - Pesquisar Investimento por ID");
            System.out.println("4 - Atualizar Investimento");
            System.out.println("5 - Remover Investimento");
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
        InvestimentoDao investimentoDao;
        ContaBancariaDao contaDao;

        try {
            investimentoDao = new InvestimentoDao();
            contaDao = new ContaBancariaDao();

            System.out.println("\n--- Cadastrar Novo Investimento ---");

            System.out.print("Digite o ID da Conta Bancária associada: ");
            int idConta = scanner.nextInt();
            scanner.nextLine();
            ContaBancaria contaAssociada = contaDao.pesquisarContaPorId(idConta);

            System.out.print("Tipo do Investimento (Ex: CDB, Ações, Tesouro Direto): ");
            String tipoInvestimento = scanner.nextLine();

            System.out.print("Valor Investido: ");
            double valor = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Data do Investimento (Formato: 2025-04-22): ");
            String dataInvestimentoStr = scanner.nextLine();
            LocalDate dataInvestimento = LocalDate.parse(dataInvestimentoStr, DATE_FORMATTER);

            System.out.print("Data de Vencimento (Formato: 2025-04-22): ");
            String dataVencimentoStr = scanner.nextLine();
            LocalDate dataVencimento = LocalDate.parse(dataVencimentoStr, DATE_FORMATTER);

            System.out.print("Rendimento esperado (%): ");
            double rendimento = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Recorrência do Rendimento (Ex: Mensal, Anual, N/A): ");
            String recorrencia = scanner.nextLine();

            System.out.print("Status inicial (Ex: Ativo, Planejado): ");
            String status = scanner.nextLine();

            Investimento novoInvestimento = new Investimento();
            novoInvestimento.setContaBancaria(contaAssociada);
            novoInvestimento.setTipoInvestimento(tipoInvestimento);
            novoInvestimento.setValor(valor);
            novoInvestimento.setDataInvestimento(dataInvestimento);
            novoInvestimento.setDataVencimento(dataVencimento);
            novoInvestimento.setRendimento(rendimento);
            novoInvestimento.setRecorrencia(recorrencia);
            novoInvestimento.setStatus(status);

            investimentoDao.cadastrarInvestimento(novoInvestimento);
            investimentoDao.fecharConexao();
            System.out.println("Investimento cadastrado com sucesso!");

        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Nenhuma conta localizada com o ID informado.");
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao cadastrar investimento: " + e.getMessage());
        }
    }

    private static void listar() {
        InvestimentoDao dao;
        try {
            dao = new InvestimentoDao();
            List<Investimento> investimentos = dao.getAllInvestimentos();

            if (investimentos.isEmpty()) {
                System.out.println("Nenhum investimento encontrado.");
                return;
            }

            System.out.println("\n--- Lista de Investimentos Cadastrados (" + investimentos.size() + ") ---");
            for (Investimento investimento : investimentos) {

                investimento.exibirTransacao();

                System.out.println("   ID Conta: " + investimento.getContaBancaria().getIdConta());
                if (investimento.getContaBancaria().getCliente() != null) {
                    System.out.println("   ID Cliente: " + investimento.getContaBancaria().getCliente().getIdCliente());
                }
                System.out.println("-------------------------------------------");
            }

            dao.fecharConexao();
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao listar investimentos: " + e.getMessage());
        }
    }

    private static void pesquisar(Scanner scanner) {
        InvestimentoDao dao;
        try {
            dao = new InvestimentoDao();
            System.out.print("Digite o ID do Investimento a pesquisar: ");
            int idInvestimento = scanner.nextInt();
            scanner.nextLine();

            Investimento investimento = dao.pesquisarInvestimentoPorId(idInvestimento);

            System.out.println("\n--- Detalhamento do Investimento ---");
            investimento.exibirTransacao();
            System.out.println("   ID Conta Associada: " + investimento.getContaBancaria().getIdConta());
            if (investimento.getContaBancaria().getCliente() != null) {
                System.out.println("   ID Cliente Associado: " + investimento.getContaBancaria().getCliente().getIdCliente());
            }
            System.out.println("-------------------------------------------");

            dao.fecharConexao();
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Não foi localizado nenhum investimento para o ID informado.");
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao pesquisar investimento: " + e.getMessage());
        }
    }

    private static void atualizar(Scanner scanner) {
        InvestimentoDao investimentoDao;
        try {
            investimentoDao = new InvestimentoDao();

            System.out.print("Digite o ID do Investimento a atualizar: ");
            int idInvestimento = scanner.nextInt();
            scanner.nextLine();

            Investimento investimentoParaAtualizar = investimentoDao.pesquisarInvestimentoPorId(idInvestimento);

            System.out.println("\n--- Atualizando Investimento ID: " + idInvestimento + " ---");

            System.out.print("Novo Tipo (Atual: " + investimentoParaAtualizar.getTipoInvestimento() + "): ");
            String novoTipo = scanner.nextLine();
            investimentoParaAtualizar.setTipoInvestimento(novoTipo);

            System.out.print("Novo Valor (Atual: " + investimentoParaAtualizar.getValor() + "): ");
            String novoValorStr = scanner.nextLine();
            investimentoParaAtualizar.setValor(Double.parseDouble(novoValorStr));

            System.out.print("Nova Data de Investimento (Atual: " + investimentoParaAtualizar.getDataInvestimento().format(DATE_FORMATTER) + ", Formato: yyyy-MM-dd): ");
            String novaDataInvStr = scanner.nextLine();
            investimentoParaAtualizar.setDataInvestimento(LocalDate.parse(novaDataInvStr, DATE_FORMATTER));

            String dataVencAtualStr = investimentoParaAtualizar.getDataVencimento() != null ? investimentoParaAtualizar.getDataVencimento().format(DATE_FORMATTER) : "N/A";
            System.out.print("Nova Data de Vencimento (Atual: " + dataVencAtualStr + ", Formato: yyyy-MM-dd): ");
            String novaDataVencStr = scanner.nextLine();
            investimentoParaAtualizar.setDataVencimento(LocalDate.parse(novaDataVencStr, DATE_FORMATTER));

            System.out.print("Novo Rendimento % (Atual: " + investimentoParaAtualizar.getRendimento() + "): ");
            String novoRendimentoStr = scanner.nextLine();
            investimentoParaAtualizar.setRendimento(Double.parseDouble(novoRendimentoStr));

            System.out.print("Nova Recorrência (Atual: " + (investimentoParaAtualizar.getRecorrencia() != null ? investimentoParaAtualizar.getRecorrencia() : "N/A") + "): ");
            String novaRecorrencia = scanner.nextLine();
            investimentoParaAtualizar.setRecorrencia(novaRecorrencia);

            System.out.print("Novo Status (Atual: " + investimentoParaAtualizar.getStatus() + "): ");
            String novoStatus = scanner.nextLine();
            investimentoParaAtualizar.setStatus(novoStatus);

            investimentoDao.atualizarInvestimento(investimentoParaAtualizar);
            System.out.println("Investimento atualizado com sucesso!");

        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Não foi localizado nenhum investimento para o ID informado.");
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao atualizar investimento: " + e.getMessage());
        }
    }

    private static void remover(Scanner scanner) {
        InvestimentoDao investimentoDao;
        try {
            investimentoDao = new InvestimentoDao();

            System.out.print("Digite o ID do Investimento a remover: ");
            int idInvestimento = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Tem certeza que deseja remover o investimento ID " + idInvestimento + "? (S/N): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                investimentoDao.deletarInvestimento(idInvestimento);
                System.out.println("Investimento removido com sucesso.");
            } else {
                System.out.println("Remoção cancelada.");
            }

        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Não foi localizado nenhum investimento para o ID informado.");
        } catch (SQLException e) {
            System.err.println("Erro de banco de dados ao remover investimento: " + e.getMessage());
        }
    }
}
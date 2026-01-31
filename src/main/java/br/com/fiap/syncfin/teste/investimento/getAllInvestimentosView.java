package br.com.fiap.syncfin.teste.investimento;

import br.com.fiap.syncfin.dao.InvestimentoDao;
import br.com.fiap.syncfin.model.Investimento;

import java.sql.SQLException;
import java.util.List;

public class getAllInvestimentosView {

    public static void main(String[] args) {

        try {
            InvestimentoDao dao = new InvestimentoDao();

            List<Investimento> investimentos = dao.getAllInvestimentos();

            System.out.println("Lista de Todos as Investimentos Cadastrados: " + investimentos.size());
            System.out.println("-------------------------------------------");

            for (Investimento investimento : investimentos) {
                investimento.exibirTransacao();
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar investimentos: " + e.getMessage());
        }
    }
}

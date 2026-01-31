package br.com.fiap.syncfin.teste.investimento;

import br.com.fiap.syncfin.dao.InvestimentoDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Investimento;

import java.sql.SQLException;

public class pesquisarInvestimentoPorIdView {

    public static void main(String[] args) {

        try {
            InvestimentoDao dao = new InvestimentoDao();
            int idInvestimento = 4;
            Investimento investimento = dao.pesquisarInvestimentoPorId(idInvestimento);
            dao.fecharConexao();
            investimento.exibirTransacao();

        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar investimento: " + e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Não há investimento cadastrado para o ID informado.");
        }
    }
}

package br.com.fiap.syncfin.teste.investimento;

import br.com.fiap.syncfin.dao.InvestimentoDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;

import java.sql.SQLException;

public class deletarInvestimentoView {

    public static void main(String[] args) {

        try{
            InvestimentoDao dao = new InvestimentoDao();
            dao.deletarInvestimento(2);
            dao.fecharConexao();
            System.out.println("Investimento excluído com sucesso !");
        }  catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Investimento não localizado!");
        }
    }
}

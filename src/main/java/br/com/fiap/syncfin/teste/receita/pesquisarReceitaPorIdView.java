package br.com.fiap.syncfin.teste.receita;

import br.com.fiap.syncfin.dao.ReceitaDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Receita;

import java.sql.SQLException;

public class pesquisarReceitaPorIdView {

    public static void main(String[] args) {

        try {
            ReceitaDao dao = new ReceitaDao();

            int idReceita = 1;
            Receita receita = dao.pesquisarReceitaPorId(idReceita);
            dao.fecharConexao();

            receita.exibirTransacao();

        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar receita: " + e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println(e.getMessage());
        }
    }
}

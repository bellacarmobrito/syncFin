package br.com.fiap.syncfin.teste.despesa;

import br.com.fiap.syncfin.dao.DespesaDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Despesa;

import java.sql.SQLException;

public class pesquisarDespesaPorIdView {

    public static void main(String[] args) {

        try {
            DespesaDao dao = new DespesaDao();

            int idConta = 4;
            Despesa despesa = dao.pesquisarDespesaPorId(idConta);
            dao.fecharConexao();

            despesa.exibirTransacao();

        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar despesa: " + e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println(e.getMessage());
        }

    }
}

package br.com.fiap.syncfin.teste.despesa;

import br.com.fiap.syncfin.dao.DespesaDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;

import java.sql.SQLException;

public class deletarDespesaView {

    public static void main(String[] args) {

        try {
            DespesaDao dao = new DespesaDao();
            dao.deletarDespesa(11);
            dao.fecharConexao();
            System.out.println("Despesa excluída com sucesso!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Despesa não localizada!");
        }

    }
}

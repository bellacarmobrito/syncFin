package br.com.fiap.syncfin.teste.receita;

import br.com.fiap.syncfin.dao.ReceitaDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;

import java.sql.SQLException;

public class deletarReceitaView {

    public static void main(String[] args) {

        try {
            ReceitaDao dao = new ReceitaDao();
            dao.deletarReceita(6);
            dao.fecharConexao();
            System.out.println("Receita excluída com sucesso!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Despesa não localizada!");
        }

    }
}

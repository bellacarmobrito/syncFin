package br.com.fiap.syncfin.teste.cadastro;

import br.com.fiap.syncfin.dao.CadastroDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;

import java.sql.SQLException;

public class InativarClienteView {

    public static void main(String[] args) {

        try {
            CadastroDao dao = new CadastroDao();
            dao.inativarCadastro(7);
            dao.fecharConexao();
            System.out.println("Cliente inativado com sucesso!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Cliente não localizado.");
        }
    }
}

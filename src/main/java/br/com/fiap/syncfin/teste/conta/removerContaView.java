package br.com.fiap.syncfin.teste.conta;

import br.com.fiap.syncfin.dao.ContaBancariaDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;

import java.sql.SQLException;

public class removerContaView {

    public static void main(String[] args) throws SQLException, EntidadeNaoEncontradaException {

        try {
            ContaBancariaDao dao = new ContaBancariaDao();
            dao.removerConta(4);
            dao.fecharConexao();
            System.out.println("Conta encerrada com sucesso !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Conta não localizada!");
        }

    }
}

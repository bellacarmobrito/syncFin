package br.com.fiap.syncfin.teste.endereco;

import br.com.fiap.syncfin.dao.EnderecoDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;

import java.sql.SQLException;

public class removerEnderecoView {

    public static void main(String[] args) {

        try {
            EnderecoDao dao = new EnderecoDao();
            dao.removerEndereco(9);
            dao.fecharConexao();
            System.out.println("Endereço removido com sucesso !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Não há clientes para o ID informado.");
        }
    }
}

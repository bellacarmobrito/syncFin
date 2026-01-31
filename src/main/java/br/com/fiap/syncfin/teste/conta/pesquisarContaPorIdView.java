package br.com.fiap.syncfin.teste.conta;

import br.com.fiap.syncfin.dao.ContaBancariaDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.ContaBancaria;

import java.sql.SQLException;

public class pesquisarContaPorIdView {

    public static void main(String[] args) {


        try {
            ContaBancariaDao dao = new ContaBancariaDao();
            int idConta = 2;

            ContaBancaria conta = dao.pesquisarContaPorId(idConta);

            if (conta != null) {
                conta.exibirConta();
            } else {
                System.out.println("Conta não encontrada para o ID informado.");
            }

            dao.fecharConexao();
        } catch (SQLException e) {
            System.err.println("Erro ao pesquisar conta: " + e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            throw new RuntimeException(e);
        }


    }
}

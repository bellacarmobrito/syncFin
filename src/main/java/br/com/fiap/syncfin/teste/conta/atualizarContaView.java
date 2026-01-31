package br.com.fiap.syncfin.teste.conta;

import br.com.fiap.syncfin.dao.ContaBancariaDao;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;

import java.sql.SQLException;

public class atualizarContaView {

    public static void main(String[] args) {

        try {
            ContaBancariaDao dao = new ContaBancariaDao();
            Cadastro cliente = new Cadastro();

            ContaBancaria contaAtualizada = new ContaBancaria(
                    cliente,
                    "Apple",
                    "0458",
                    "8899659-9",
                    "Poupança",
                    3000.00
            );
            contaAtualizada.setIdConta(6);

            dao.atualizarConta(contaAtualizada);
            dao.fecharConexao();

            System.out.println("Conta bancária atualizada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar conta: " + e.getMessage());
        }
    }
}

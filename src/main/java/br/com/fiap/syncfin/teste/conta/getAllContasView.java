package br.com.fiap.syncfin.teste.conta;

import br.com.fiap.syncfin.dao.ContaBancariaDao;
import br.com.fiap.syncfin.model.ContaBancaria;

import java.sql.SQLException;
import java.util.List;

public class getAllContasView {

    public static void main(String[] args) {

        try {
            ContaBancariaDao dao = new ContaBancariaDao();
            List<ContaBancaria> contas = dao.getAllContas();

            if (contas.isEmpty()) {
                System.out.println("Nenhuma conta bancária cadastrada.");
            } else {
                for (ContaBancaria conta : contas) {
                    conta.exibirConta();
                    System.out.println();
                }
            }

            dao.fecharConexao();

        } catch (SQLException e) {
            System.err.println("Erro ao listar contas: " + e.getMessage());
        }

    }
}

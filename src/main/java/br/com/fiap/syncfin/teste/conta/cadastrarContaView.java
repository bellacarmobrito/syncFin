package br.com.fiap.syncfin.teste.conta;

import br.com.fiap.syncfin.dao.ContaBancariaDao;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;

import java.sql.SQLException;

public class cadastrarContaView {

    public static void main(String[] args) throws SQLException {

        try {
            ContaBancariaDao dao = new ContaBancariaDao();
            Cadastro cliente = new Cadastro();
            cliente.setIdCliente(7);

            ContaBancaria conta = new ContaBancaria(
                    cliente,
                    "Itaú",
                    "8646",
                    "10106-7",
                    "Pessoa Jurídica",
                    75500.00
            );

            dao.cadastrarConta(conta);
            dao.fecharConexao();

            System.out.println("Conta bancária cadastrada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar conta: " + e.getMessage());
        }
    }
}

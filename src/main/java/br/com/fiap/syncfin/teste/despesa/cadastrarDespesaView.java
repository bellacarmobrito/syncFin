package br.com.fiap.syncfin.teste.despesa;

import br.com.fiap.syncfin.dao.DespesaDao;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;
import br.com.fiap.syncfin.model.Despesa;

import java.sql.SQLException;
import java.time.LocalDate;

public class cadastrarDespesaView {

    public static void main(String[] args) {

        try {
            DespesaDao dao = new DespesaDao();

            Cadastro cliente = new Cadastro();
            cliente.setIdCliente(9);

            ContaBancaria conta = new ContaBancaria();
            conta.setIdConta(2);
            conta.setCliente(cliente);

            Despesa despesa = new Despesa();
            despesa.setContaBancaria(conta);
            despesa.setValor(350.75);
            despesa.setCategoria("Mercado");
            despesa.setVencimento(LocalDate.of(2025, 5, 10));
            despesa.setStatus("Pendente");
            despesa.setDescricao("Mercearia do Seu João");

            dao.cadastrarDespesa(despesa);
            dao.fecharConexao();

            System.out.println("Despesa cadastrada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar despesa: " + e.getMessage());
        }

    }
}

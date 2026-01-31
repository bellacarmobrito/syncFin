package br.com.fiap.syncfin.teste.receita;

import br.com.fiap.syncfin.dao.ReceitaDao;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;
import br.com.fiap.syncfin.model.Receita;

import java.sql.SQLException;
import java.time.LocalDate;

public class cadastrarReceitaView {

    public static void main(String[] args) {

        try {
            ReceitaDao dao = new ReceitaDao();

            Cadastro cliente = new Cadastro();
            cliente.setIdCliente(7);

            ContaBancaria conta = new ContaBancaria();
            conta.setIdConta(6);
            conta.setCliente(cliente);

            Receita receita = new Receita();
            receita.setContaBancaria(conta);
            receita.setValor(5350.40);
            receita.setCategoria("Reembolso");
            receita.setDataRecebimento(LocalDate.of(2025, 7, 15));
            receita.setStatus("A receber");
            receita.setDescricao("Reembolso Notebook Asus");

            dao.cadastrarReceita(receita);
            dao.fecharConexao();

            System.out.println("Receita cadastrada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar receita: " + e.getMessage());
        }
    }
}

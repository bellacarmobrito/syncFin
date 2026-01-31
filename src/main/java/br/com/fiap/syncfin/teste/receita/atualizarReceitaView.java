package br.com.fiap.syncfin.teste.receita;

import br.com.fiap.syncfin.dao.ReceitaDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;
import br.com.fiap.syncfin.model.Receita;

import java.sql.SQLException;
import java.time.LocalDate;

public class atualizarReceitaView {

    public static void main(String[] args) {

        try {
            ReceitaDao dao = new ReceitaDao();

            Cadastro cliente = new Cadastro();
            cliente.setIdCliente(7);

            ContaBancaria conta = new ContaBancaria();
            conta.setIdConta(6);
            conta.setCliente(cliente);

            Receita receitaAtualizada = new Receita();
            receitaAtualizada.setId(6);
            receitaAtualizada.setContaBancaria(conta);
            receitaAtualizada.setValor(350);
            receitaAtualizada.setCategoria("Banco de Horas");
            receitaAtualizada.setDataRecebimento(LocalDate.of(2025, 5, 15));
            receitaAtualizada.setDescricao("Reembolso Uber");
            receitaAtualizada.setStatus("Recebido");

            dao.atualizarReceita(receitaAtualizada);
            dao.fecharConexao();

            System.out.println("Receita atualizada com sucesso!");

        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Nenhuma receita localizada com o ID informado.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}

package br.com.fiap.syncfin.teste.despesa;

import br.com.fiap.syncfin.dao.DespesaDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;
import br.com.fiap.syncfin.model.Despesa;

import java.sql.SQLException;
import java.time.LocalDate;

public class atualizarDespesaView {

    public static void main(String[] args) {

        try {
            DespesaDao dao = new DespesaDao();

            Cadastro cliente = new Cadastro();
            cliente.setIdCliente(9);

            ContaBancaria conta = new ContaBancaria();
            conta.setIdConta(2);
            conta.setCliente(cliente);

            Despesa despesaAtualizada = new Despesa();
            despesaAtualizada.setId(8);
            despesaAtualizada.setContaBancaria(conta);
            despesaAtualizada.setValor(420.00);
            despesaAtualizada.setCategoria("Supermercado");
            despesaAtualizada.setVencimento(LocalDate.of(2025, 5, 15));
            despesaAtualizada.setDescricao("Compras do mês");
            despesaAtualizada.setStatus("Pendente");

            dao.atualizarDespesa(despesaAtualizada);
            dao.fecharConexao();

            System.out.println("Despesa atualizada com sucesso!");

        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Nenhuma despesa localizada com o ID informado.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }
}

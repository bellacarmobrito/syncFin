package br.com.fiap.syncfin.teste.investimento;

import br.com.fiap.syncfin.dao.InvestimentoDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;
import br.com.fiap.syncfin.model.Investimento;

import java.sql.SQLException;
import java.time.LocalDate;

public class atualizarInvestimentoView {

    public static void main(String[] args) {

        try {
            InvestimentoDao dao = new InvestimentoDao();

            Cadastro cliente = new Cadastro();
            cliente.setIdCliente(9);

            ContaBancaria conta = new ContaBancaria();
            conta.setIdConta(2);
            conta.setCliente(cliente);

            Investimento investimentoAtualizado = new Investimento();
            investimentoAtualizado.setId(5);
            investimentoAtualizado.setContaBancaria(conta);
            investimentoAtualizado.setTipoInvestimento("CBD");
            investimentoAtualizado.setValor(5300);
            investimentoAtualizado.setDataInvestimento(LocalDate.of(2025, 4, 25));
            investimentoAtualizado.setRecorrencia("Bimestral");
            investimentoAtualizado.setStatus("Pendente");
            investimentoAtualizado.setRendimento(3.89);
            investimentoAtualizado.setDataVencimento(LocalDate.of(2028, 9, 7));

            dao.atualizarInvestimento(investimentoAtualizado);
            dao.fecharConexao();

            System.out.println("Investimento atualizado com sucesso!");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Nenhum investimento localizado com o ID informado.");
        }
    }
}

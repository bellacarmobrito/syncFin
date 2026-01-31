package br.com.fiap.syncfin.teste.investimento;

import br.com.fiap.syncfin.dao.InvestimentoDao;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;
import br.com.fiap.syncfin.model.Investimento;

import java.sql.SQLException;
import java.time.LocalDate;

public class cadastrarInvestimentoView {

    public static void main(String[] args) {

        try {
            InvestimentoDao dao = new InvestimentoDao();

            Cadastro cliente = new Cadastro();
            cliente.setIdCliente(7);

            ContaBancaria conta = new ContaBancaria();
            conta.setIdConta(6);
            conta.setCliente(cliente);

            Investimento investimento = new Investimento();
            investimento.setContaBancaria(conta);
            investimento.setTipoInvestimento("LCI");
            investimento.setValor(5700);
            investimento.setDataInvestimento(LocalDate.of(2025, 4, 15));
            investimento.setRecorrencia("Mensal");
            investimento.setStatus("Em liquidação");
            investimento.setRendimento(2.35);
            investimento.setDataVencimento(LocalDate.of(2026, 12, 7));

            dao.cadastrarInvestimento(investimento);
            dao.fecharConexao();

            System.out.println("Investimento cadastrado com sucesso!");


        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar investimento: " + e.getMessage());
        }
    }
}

package br.com.fiap.syncfin.teste.despesa;

import br.com.fiap.syncfin.dao.DespesaDao;
import br.com.fiap.syncfin.model.Despesa;

import java.sql.SQLException;
import java.util.List;

public class getAllDespesasView {

    public static void main(String[] args) {

        try {
            DespesaDao dao = new DespesaDao();

            List<Despesa> despesas = dao.getAllDespesas();

            System.out.println("Lista de Todas as Despesas Cadastradas: " + despesas.size());
            System.out.println("-------------------------------------------");

            for (Despesa despesa : despesas) {
                despesa.exibirTransacao();
            }

            dao.fecharConexao();

        } catch (SQLException e) {
            System.err.println("Erro ao buscar despesas: " + e.getMessage());
        }

    }
}

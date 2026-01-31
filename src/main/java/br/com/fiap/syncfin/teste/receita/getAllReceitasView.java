package br.com.fiap.syncfin.teste.receita;

import br.com.fiap.syncfin.dao.ReceitaDao;
import br.com.fiap.syncfin.model.Receita;

import java.sql.SQLException;
import java.util.List;

public class getAllReceitasView {

    public static void main(String[] args) {

        try {
            ReceitaDao dao = new ReceitaDao();

            List<Receita> receitas = dao.getAllReceitas();

            System.out.println("Lista de Todas as Receitas Cadastradas: " + receitas.size());
            System.out.println("-------------------------------------------");

            for (Receita receita : receitas) {
                receita.exibirTransacao();
            }

            dao.fecharConexao();

        } catch (SQLException e) {
            System.err.println("Erro ao buscar receitas: " + e.getMessage());
        }
    }
}

package br.com.fiap.syncfin.teste.endereco;

import br.com.fiap.syncfin.dao.EnderecoDao;
import br.com.fiap.syncfin.model.Endereco;

import java.sql.SQLException;
import java.util.List;

public class getAllEnderecosView {

    public static void main(String[] args) {

        try {
            EnderecoDao dao = new EnderecoDao();
            List<Endereco> enderecos = dao.getAllEnderecos();

            System.out.println("Lista de todos os endereços cadastrados: " + enderecos.size());
            System.out.println("--------------------------------------------");

            for (Endereco e : enderecos) {
                System.out.println(e.getEnderecoCompleto());
                System.out.println("--------------------------------------------");
            }

            dao.fecharConexao();

        } catch (SQLException e) {
            System.err.println("Erro ao listar endereços: " + e.getMessage());
        }
    }
}

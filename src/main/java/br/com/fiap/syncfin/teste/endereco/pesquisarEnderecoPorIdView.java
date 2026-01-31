package br.com.fiap.syncfin.teste.endereco;

import br.com.fiap.syncfin.dao.EnderecoDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Endereco;

import java.sql.SQLException;

public class pesquisarEnderecoPorIdView {

    public static void main(String[] args) {

        try {
            EnderecoDao dao = new EnderecoDao();
            int idCliente = 8;
            Endereco endereco = dao.pesquisarEnderecoPorId(idCliente);
            System.out.println("Endereço do Cliente ID: " + idCliente);
            System.out.println("-------------------------------------------");
            System.out.println("Logradouro: " + endereco.getLogradouro());
            System.out.println("Número: " + endereco.getNumero());
            System.out.println("Bairro: " + endereco.getBairro());
            System.out.println("CEP: " + endereco.getCep());
            System.out.println("Cidade: " + endereco.getCidade());
            System.out.println("Estado: " + endereco.getEstado());
            dao.fecharConexao();

        } catch (SQLException e) {
            System.err.println("Erro ao buscar endereço: " + e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Endereço não encontrado: " + e.getMessage());

        }
    }
}

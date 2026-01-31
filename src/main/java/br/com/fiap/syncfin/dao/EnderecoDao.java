package br.com.fiap.syncfin.dao;

import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.factory.ConnectionFactory;
import br.com.fiap.syncfin.model.Endereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDao {

    private Connection conexao;

    public EnderecoDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void cadastrarEndereco(Endereco endereco, int idCliente) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("INSERT INTO T_ENDERECO (LOGRADOURO, NUMERO, BAIRRO, CEP, CIDADE, ESTADO, ID_CLIENTE) VALUES (?,?,?,?,?,?,?)");
        stm.setString(1, endereco.getLogradouro());
        stm.setInt(2,endereco.getNumero());
        stm.setString(3, endereco.getBairro());
        stm.setString(4, endereco.getCep());
        stm.setString(5, endereco.getCidade());
        stm.setString(6, endereco.getEstado());
        stm.setInt(7, idCliente);
        stm.executeUpdate();
    }

    public void fecharConexao() throws SQLException {
        conexao.close();
    }

    public Endereco pesquisarEnderecoPorId(int idCliente) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM T_ENDERECO WHERE ID_CLIENTE = ?");
        stm.setInt(1, idCliente);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            Endereco endereco = new Endereco(
                    rs.getString("LOGRADOURO"),
                    rs.getInt("NUMERO"),
                    rs.getString("BAIRRO"),
                    rs.getString("CEP"),
                    rs.getString("CIDADE"),
                    rs.getString("ESTADO")
            );
            return endereco;
        } else throw new EntidadeNaoEncontradaException("Não há endereço cadastrado para o ID informado.");
    }

    public List<Endereco> getAllEnderecos() throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM T_ENDERECO");
        ResultSet rs = stm.executeQuery();
        List<Endereco> lista = new ArrayList<>();

        while (rs.next()) {

            int idCliente = rs.getInt("ID_CLIENTE");
            String logradouro = rs.getString("LOGRADOURO");
            int numero = rs.getInt("NUMERO");
            String bairro = rs.getString("BAIRRO");
            String cep = rs.getString("CEP");
            String cidade = rs.getString("CIDADE");
            String estado = rs.getString("ESTADO");

            Endereco endereco = new Endereco(idCliente, logradouro, numero, bairro, cep, cidade, estado);
            lista.add(endereco);

        }
        return lista;
    }

    public void atualizarEndereco(Endereco endereco) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("UPDATE T_ENDERECO SET LOGRADOURO = ?, NUMERO = ?, BAIRRO = ?, CEP = ?, CIDADE = ?, ESTADO = ? WHERE ID_CLIENTE = ?");
        stm.setString(1, endereco.getLogradouro());
        stm.setInt(2, endereco.getNumero());
        stm.setString(3, endereco.getBairro());
        stm.setString(4, endereco.getCep());
        stm.setString(5, endereco.getCidade());
        stm.setString(6, endereco.getEstado());
        stm.setInt(7, endereco.getIdCliente());
        int linhasAfetadas = stm.executeUpdate();

        if (linhasAfetadas == 0) {
            throw new SQLException("Nenhum endereço foi atualizado. ID informado não possui endereço cadastrado.");
        }
    }

    public void removerEndereco(int idCliente) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("DELETE FROM T_ENDERECO WHERE ID_CLIENTE = ?");
        stm.setInt(1, idCliente);
        int linha = stm.executeUpdate();
        if (linha == 0) {
            throw new EntidadeNaoEncontradaException("Cliente não localizado.");
        }
    }

}

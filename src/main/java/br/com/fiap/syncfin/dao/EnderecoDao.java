package br.com.fiap.syncfin.dao;

import br.com.fiap.syncfin.model.Endereco;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnderecoDao extends BaseDao {

    public EnderecoDao() throws SQLException {
        super();
    }

    private Endereco mapEndereco(ResultSet rs) throws SQLException {
        return new Endereco(
                rs.getInt("ID_CLIENTE"),
                rs.getString("LOGRADOURO"),
                rs.getInt("NUMERO"),
                rs.getString("BAIRRO"),
                rs.getString("CEP"),
                rs.getString("CIDADE"),
                rs.getString("ESTADO")
        );
    }

    public void salvar(Endereco endereco) throws SQLException {

        String sql = "INSERT INTO T_ENDERECO (ID_CLIENTE, CEP, LOGRADOURO, NUMERO, BAIRRO, CIDADE, ESTADO) " +
                "VALUES (?,?,?,?,?,?,?) " +
                "ON CONFLICT (ID_CLIENTE) DO UPDATE SET " +
                "CEP = EXCLUDED.CEP, LOGRADOURO = EXCLUDED.LOGRADOURO, NUMERO = EXCLUDED.NUMERO, " +
                "BAIRRO = EXCLUDED.BAIRRO, CIDADE = EXCLUDED.CIDADE, ESTADO = EXCLUDED.ESTADO";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, endereco.getIdCliente());
            stm.setString(2, endereco.getCep());
            stm.setString(3, endereco.getLogradouro());
            stm.setInt(4, endereco.getNumero());
            stm.setString(5, endereco.getBairro());
            stm.setString(6, endereco.getCidade());
            stm.setString(7, endereco.getEstado());
            stm.executeUpdate();
        }
    }

    public Endereco buscarPorCliente(int idCliente) throws SQLException {

        String sql = "SELECT * FROM T_ENDERECO WHERE ID_CLIENTE = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idCliente);

            try (ResultSet rs = stm.executeQuery()) {
                return rs.next() ? mapEndereco(rs) : null;
            }
        }
    }
}

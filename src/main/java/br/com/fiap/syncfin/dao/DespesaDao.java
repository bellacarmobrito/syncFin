package br.com.fiap.syncfin.dao;

import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;
import br.com.fiap.syncfin.model.Despesa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DespesaDao extends BaseDao {

    public DespesaDao() throws SQLException {
        super();
    }

    private Despesa mapDespesa(ResultSet rs) throws SQLException {
        Cadastro cliente = new Cadastro();
        cliente.setIdCliente(rs.getInt("ID_CLIENTE"));

        ContaBancaria conta = new ContaBancaria();
        conta.setIdConta(rs.getInt("ID_CONTA"));
        conta.setCliente(cliente);

        Despesa despesa = new Despesa();
        despesa.setId(rs.getInt("ID_DESPESA"));
        despesa.setContaBancaria(conta);
        despesa.setValor(rs.getDouble("VL_DESPESA"));
        despesa.setCategoria(rs.getString("CATEGORIA_DESPESA"));
        despesa.setVencimento(rs.getDate("DT_VENCIMENTO").toLocalDate());
        despesa.setStatus(rs.getString("ST_DESPESA"));
        despesa.setDescricao(rs.getString("OB_DESPESA"));

        return despesa;
    }


    public void cadastrarDespesa(Despesa despesa) throws SQLException {

        String sql = "INSERT INTO T_DESPESA (ID_CLIENTE, ID_CONTA, VL_DESPESA, CATEGORIA_DESPESA, DT_VENCIMENTO, OB_DESPESA, ST_DESPESA) VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, despesa.getContaBancaria().getCliente().getIdCliente());
            stm.setInt(2, despesa.getContaBancaria().getIdConta());
            stm.setDouble(3, despesa.getValor());
            stm.setString(4, despesa.getCategoria());
            stm.setDate(5, Date.valueOf(despesa.getVencimento()));
            stm.setString(6, despesa.getDescricao());
            stm.setString(7, despesa.getStatus());
            stm.executeUpdate();
        }

    }

    public Despesa pesquisarDespesaPorId(int idDespesa) throws SQLException, EntidadeNaoEncontradaException {

        String sql = "SELECT * FROM T_DESPESA WHERE ID_DESPESA = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idDespesa);

            try (ResultSet rs = stm.executeQuery()) {
                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("Despesa não encontrada pelo ID informado.");
                }
                return mapDespesa(rs);
            }
        }
    }

    public Despesa pesquisarDespesaPorIdDoCliente(int idCliente, int idDespesa) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_DESPESA WHERE ID_CLIENTE = ? AND ID_DESPESA = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idCliente);
            stm.setInt(2, idDespesa);

            try (ResultSet rs = stm.executeQuery()) {
                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("Despesa não encontrada ou acesso negado");
                }
                return mapDespesa(rs);
            }
        }
    }

    public List<Despesa> pesquisarDespesasPorCliente(int idCliente) throws SQLException {
        String sql = "SELECT * FROM T_DESPESA WHERE ID_CLIENTE = ?";
        List<Despesa> lista = new ArrayList<>();

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idCliente);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapDespesa(rs));
                }
            }
        }
        return lista;
    }

    public void atualizarDespesa(Despesa despesa) throws SQLException, EntidadeNaoEncontradaException {

        String sql = "UPDATE T_DESPESA SET VL_DESPESA = ?, CATEGORIA_DESPESA = ?, DT_VENCIMENTO = ?, OB_DESPESA = ?, ST_DESPESA = ? WHERE ID_DESPESA = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setDouble(1, despesa.getValor());
            stm.setString(2, despesa.getCategoria());
            stm.setDate(3, Date.valueOf(despesa.getVencimento()));
            stm.setString(4, despesa.getDescricao());
            stm.setString(5, despesa.getStatus());
            stm.setInt(6, despesa.getId());

            int linhas = stm.executeUpdate();
            if (linhas == 0) {
                throw new EntidadeNaoEncontradaException("Erro ao atualizar: nenhuma despesa foi encontrada com o ID informado.");
            }
        }
    }

    public void atualizarDespesaDoCliente(Despesa despesa, int idCliente) throws SQLException, EntidadeNaoEncontradaException {

        String sql = "UPDATE T_DESPESA SET VL_DESPESA = ?, CATEGORIA_DESPESA = ?, DT_VENCIMENTO = ?, OB_DESPESA = ?, ST_DESPESA = ? WHERE ID_DESPESA = ? AND ID_CLIENTE = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setDouble(1, despesa.getValor());
            stm.setString(2, despesa.getCategoria());
            stm.setDate(3, Date.valueOf(despesa.getVencimento()));
            stm.setString(4, despesa.getDescricao());
            stm.setString(5, despesa.getStatus());
            stm.setInt(6, despesa.getId());
            stm.setInt(7, idCliente);

            int linhas = stm.executeUpdate();
            if (linhas == 0) {
                throw new EntidadeNaoEncontradaException("Erro ao atualizar: nenhuma despesa foi encontrada com o ID informado.");
            }
        }
    }

    public void deletarDespesa(int idDespesa) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM T_DESPESA WHERE ID_DESPESA = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idDespesa);
            int linha = stm.executeUpdate();

            if (linha == 0) {
                throw new EntidadeNaoEncontradaException("Nenhuma despesa foi encontrada com o ID informado.");
            }
        }
    }

    public void deletarDespesaDoCliente(int idDespesa, int idCliente) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM T_DESPESA WHERE ID_DESPESA = ? AND ID_CLIENTE = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idDespesa);
            stm.setInt(2, idCliente);
            int linha = stm.executeUpdate();

            if (linha == 0) {
                throw new EntidadeNaoEncontradaException("Nenhuma despesa foi encontrada com o ID informado.");
            }
        }
    }


    public double calcularDespesasPorCliente(int idCliente) throws SQLException {

        String sql = "SELECT SUM(VL_DESPESA) AS TOTAL FROM T_DESPESA WHERE ID_CLIENTE = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idCliente);

            try (ResultSet rs = stm.executeQuery()) {
                return rs.next() ? rs.getDouble("TOTAL") : 0;
            }
        }
    }
}

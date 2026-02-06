package br.com.fiap.syncfin.dao;

import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;
import br.com.fiap.syncfin.model.Receita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceitaDao extends BaseDao {

    public ReceitaDao() throws SQLException {
        super();
    }

    private Receita mapReceita(ResultSet rs) throws SQLException {
        Cadastro cliente = new Cadastro();
        cliente.setIdCliente(rs.getInt("ID_CLIENTE"));

        ContaBancaria conta = new ContaBancaria();
        conta.setIdConta(rs.getInt("ID_CONTA"));
        conta.setCliente(cliente);

        Receita receita = new Receita();
        receita.setId(rs.getInt("ID_RECEITA"));
        receita.setContaBancaria(conta);
        receita.setValor(rs.getDouble("VL_RECEITA"));
        receita.setCategoria(rs.getString("CATEGORIA_RECEITA"));
        receita.setDataRecebimento(rs.getDate("DT_RECEBIMENTO").toLocalDate());
        receita.setDescricao(rs.getString("OB_RECEITA"));
        receita.setStatus(rs.getString("ST_RECEITA"));

        return receita;
    }

    public void cadastrarReceita(Receita receita) throws SQLException {

        String sql = "INSERT INTO T_RECEITA (ID_CLIENTE, ID_CONTA, VL_RECEITA, CATEGORIA_RECEITA, DT_RECEBIMENTO, OB_RECEITA, ST_RECEITA) VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, receita.getContaBancaria().getCliente().getIdCliente());
            stm.setInt(2, receita.getContaBancaria().getIdConta());
            stm.setDouble(3, receita.getValor());
            stm.setString(4, receita.getCategoria());
            stm.setDate(5, Date.valueOf(receita.getDataRecebimento()));
            stm.setString(6, receita.getDescricao());
            stm.setString(7, receita.getStatus());
            stm.executeUpdate();
        }
    }

    public Receita pesquisarReceitaPorId(int idReceita) throws SQLException, EntidadeNaoEncontradaException {

        String sql = "SELECT * FROM T_RECEITA WHERE ID_RECEITA = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idReceita);

            try (ResultSet rs = stm.executeQuery()) {
                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("Nenhuma receita encontrada com o ID informado.");
                }
                return mapReceita(rs);
            }
        }
    }

    public Receita pesquisarReceitaPorIdDoCliente(int idCliente, int idReceita) throws SQLException, EntidadeNaoEncontradaException {

        String sql = "SELECT * FROM T_RECEITA WHERE ID_CLIENTE = ? AND ID_RECEITA = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idCliente);
            stm.setInt(2, idReceita);

            try (ResultSet rs = stm.executeQuery()) {
                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("Receita não encontrada ou acesso negado.");
                }
                return mapReceita(rs);
            }
        }
    }

    public List<Receita> pesquisarReceitasPorCliente(int idCliente) throws SQLException {
        String sql = "SELECT * FROM T_RECEITA WHERE ID_CLIENTE = ?";
        List<Receita> receitas = new ArrayList<>();

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idCliente);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    receitas.add(mapReceita(rs));
                }
            }
        }
        return receitas;
    }

    public List<Receita> getAllReceitas() throws SQLException {

        String sql = "SELECT * FROM T_RECEITA";
        List<Receita> lista = new ArrayList<>();

        try (PreparedStatement stm = conexao.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                lista.add(mapReceita(rs));
            }
        }
        return lista;
    }

    public void atualizarReceita(Receita receita) throws SQLException, EntidadeNaoEncontradaException {

        String sql = "UPDATE T_RECEITA SET VL_RECEITA = ?, CATEGORIA_RECEITA = ?, DT_RECEBIMENTO = ?, OB_RECEITA = ?, ST_RECEITA = ? WHERE ID_RECEITA = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setDouble(1, receita.getValor());
            stm.setString(2, receita.getCategoria());
            stm.setDate(3, Date.valueOf(receita.getDataRecebimento()));
            stm.setString(4, receita.getDescricao());
            stm.setString(5, receita.getStatus());
            stm.setInt(6, receita.getId());

            int linhas = stm.executeUpdate();
            if (linhas == 0) {
                throw new EntidadeNaoEncontradaException("Erro ao atualizar: nenhuma receita foi encontrada com o ID informado.");
            }
        }
    }

    public void atualizarReceitaDoCliente(Receita receita, int idCliente) throws SQLException, EntidadeNaoEncontradaException {

        String sql = "UPDATE T_RECEITA SET VL_RECEITA = ?, CATEGORIA_RECEITA = ?, DT_RECEBIMENTO = ?, OB_RECEITA = ?, ST_RECEITA = ? WHERE ID_RECEITA = ? AND ID_CLIENTE = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setDouble(1, receita.getValor());
            stm.setString(2, receita.getCategoria());
            stm.setDate(3, Date.valueOf(receita.getDataRecebimento()));
            stm.setString(4, receita.getDescricao());
            stm.setString(5, receita.getStatus());
            stm.setInt(6, receita.getId());
            stm.setInt(7, idCliente);

            int linhas = stm.executeUpdate();
            if (linhas == 0) {
                throw new EntidadeNaoEncontradaException("Erro ao atualizar: nenhuma receita foi encontrada com o ID informado.");
            }
        }
    }

    public void deletarReceita(int idReceita) throws SQLException, EntidadeNaoEncontradaException {

        String sql = "DELETE FROM T_RECEITA WHERE ID_RECEITA = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idReceita);
            int linha = stm.executeUpdate();

            if (linha == 0) {
                throw new EntidadeNaoEncontradaException("Nenhuma receita foi encontrada com o ID informado.");
            }
        }
    }

    public void deletarReceitaDoCliente(int idCliente, int idReceita) throws SQLException, EntidadeNaoEncontradaException {

        String sql = "DELETE FROM T_RECEITA WHERE ID_RECEITA = ? AND ID_CLIENTE = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idReceita);
            stm.setInt(2, idCliente);
            int linha = stm.executeUpdate();

            if (linha == 0) {
                throw new EntidadeNaoEncontradaException("Receita não encontrada ou acesso negado.");
            }
        }
    }

    public double calcularReceitasPorCliente(int idCliente) throws SQLException {

        String sql = "SELECT SUM(VL_RECEITA) AS TOTAL FROM T_RECEITA WHERE ID_CLIENTE = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idCliente);

            try (ResultSet rs = stm.executeQuery()) {
                return rs.next() ? rs.getDouble("TOTAL") : 0;
            }
        }
    }
}

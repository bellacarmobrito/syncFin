package br.com.fiap.syncfin.dao;

import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.factory.ConnectionFactory;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;
import br.com.fiap.syncfin.model.Receita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceitaDao {

    private Connection conexao;

    public ReceitaDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void fecharConexao() throws SQLException {
        conexao.close();
    }

    public void cadastrarReceita(Receita receita) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("INSERT INTO T_RECEITA (ID_CLIENTE, ID_CONTA, VL_RECEITA, CATEGORIA_RECEITA, DT_RECEBIMENTO, OB_RECEITA, ST_RECEITA) VALUES(?,?,?,?,?,?,?)");
        stm.setInt(1, receita.getContaBancaria().getCliente().getIdCliente());
        stm.setInt(2, receita.getContaBancaria().getIdConta());
        stm.setDouble(3, receita.getValor());
        stm.setString(4, receita.getCategoria());
        stm.setDate(5, Date.valueOf(receita.getDataRecebimento()));
        stm.setString(6, receita.getDescricao());
        stm.setString(7, receita.getStatus());
        stm.executeUpdate();
    }

    public Receita pesquisarReceitaPorId (int idReceita) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM T_RECEITA WHERE ID_RECEITA = ?");
        stm.setInt(1, idReceita);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {

            Cadastro cliente = new Cadastro();
            cliente.setIdCliente(rs.getInt("ID_CLIENTE"));

            ContaBancaria conta = new ContaBancaria();
            conta.setIdConta(rs.getInt("ID_CONTA"));
            conta.setCliente(cliente);

            Receita receita = new Receita();
            receita.setContaBancaria(conta);
            receita.setId(rs.getInt("ID_RECEITA"));
            receita.setValor(rs.getDouble("VL_RECEITA"));
            receita.setCategoria(rs.getString("CATEGORIA_RECEITA"));
            receita.setDataRecebimento(rs.getDate("DT_RECEBIMENTO").toLocalDate());
            receita.setStatus(rs.getString("ST_RECEITA"));
            receita.setDescricao(rs.getString("OB_RECEITA"));

            stm.close();
            return receita;
        } else {
            stm.close();
            throw new EntidadeNaoEncontradaException("Receita não encontrada para o ID informado.");
        }

    }

    public List<Receita> getAllReceitas() throws SQLException {
        List<Receita> lista = new ArrayList<>();

        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM T_RECEITA");
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
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
            receita.setStatus(rs.getString("ST_RECEITA"));
            receita.setDescricao(rs.getString("OB_RECEITA"));

            lista.add(receita);
        }

        stm.close();
        return lista;
    }

    public List<Receita> pesquisarReceitasPorCliente(int idCliente) throws SQLException {
        List<Receita> lista = new ArrayList<>();

        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM T_RECEITA WHERE ID_CLIENTE = ?");
        stm.setInt(1, idCliente);
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
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
            receita.setStatus(rs.getString("ST_RECEITA"));
            receita.setDescricao(rs.getString("OB_RECEITA"));

            lista.add(receita);
        }

        stm.close();
        return lista;
    }


    public void atualizarReceita(Receita receita) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("UPDATE T_RECEITA SET VL_RECEITA = ?, CATEGORIA_RECEITA = ?, DT_RECEBIMENTO = ?, OB_RECEITA = ?, ST_RECEITA = ? WHERE ID_RECEITA = ?");

        stm.setDouble(1, receita.getValor());
        stm.setString(2, receita.getCategoria());
        stm.setDate(3, Date.valueOf(receita.getDataRecebimento()));
        stm.setString(4, receita.getDescricao());
        stm.setString(5, receita.getStatus());
        stm.setInt(6, receita.getId());

        int linhas = stm.executeUpdate();
        stm.close();

        if (linhas == 0) {
            throw new EntidadeNaoEncontradaException("Erro ao atualizar: nenhuma receita foi encontrada com o ID informado.");
        }
    }

    public void deletarReceita(int idReceita) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("DELETE FROM T_RECEITA WHERE ID_RECEITA = ?");
        stm.setInt(1, idReceita);
        int linha = stm.executeUpdate();

        if (linha == 0) {
            throw new EntidadeNaoEncontradaException("Nenhuma receita foi encontrada com o ID informado.");
        }

    }

    public double calcularReceitasPorCliente(int idCliente) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("SELECT SUM(VL_RECEITA) AS TOTAL FROM T_RECEITA WHERE ID_CLIENTE = ?");
        stm.setInt(1, idCliente);
        ResultSet rs = stm.executeQuery();

        if(rs.next()) {
            return rs.getDouble("TOTAL");
        } else {
            return 0;
        }
    }
}

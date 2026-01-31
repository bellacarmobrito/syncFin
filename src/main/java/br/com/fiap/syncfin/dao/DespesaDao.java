package br.com.fiap.syncfin.dao;

import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.factory.ConnectionFactory;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;
import br.com.fiap.syncfin.model.Despesa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DespesaDao {

    private Connection conexao;


    public DespesaDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void fecharConexao() throws SQLException {
        conexao.close();
    }

    public void cadastrarDespesa(Despesa despesa) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("INSERT INTO T_DESPESA (ID_CLIENTE, ID_CONTA, VL_DESPESA, CATEGORIA_DESPESA, DT_VENCIMENTO, OB_DESPESA, ST_DESPESA) VALUES(?,?,?,?,?,?,?)");
        stm.setInt(1, despesa.getContaBancaria().getCliente().getIdCliente());
        stm.setInt(2, despesa.getContaBancaria().getIdConta());
        stm.setDouble(3, despesa.getValor());
        stm.setString(4, despesa.getCategoria());
        stm.setDate(5, Date.valueOf(despesa.getVencimento()));
        stm.setString(6, despesa.getDescricao());
        stm.setString(7, despesa.getStatus());
        stm.executeUpdate();
    }

    public Despesa pesquisarDespesaPorId (int idDespesa) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM T_DESPESA WHERE ID_DESPESA = ?");
        stm.setInt(1, idDespesa);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {

            Cadastro cliente = new Cadastro();
            cliente.setIdCliente(rs.getInt("ID_CLIENTE"));

            ContaBancaria conta = new ContaBancaria();
            conta.setIdConta(rs.getInt("ID_CONTA"));
            conta.setCliente(cliente);

            Despesa despesa = new Despesa();
            despesa.setContaBancaria(conta);
            despesa.setId(rs.getInt("ID_DESPESA"));
            despesa.setValor(rs.getDouble("VL_DESPESA"));
            despesa.setCategoria(rs.getString("CATEGORIA_DESPESA"));
            despesa.setVencimento(rs.getDate("DT_VENCIMENTO").toLocalDate());
            despesa.setStatus(rs.getString("ST_DESPESA"));
            despesa.setDescricao(rs.getString("OB_DESPESA"));

            stm.close();
            return despesa;
        } else {
            stm.close();
            throw new EntidadeNaoEncontradaException("Despesa não encontrada para o ID informado.");
        }

    }

    public List<Despesa> getAllDespesas() throws SQLException {
        List<Despesa> lista = new ArrayList<>();

        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM T_DESPESA");
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
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

            lista.add(despesa);
        }

        stm.close();
        return lista;
    }

    public List<Despesa> pesquisarDespesasPorCliente(int idCliente) throws SQLException {

        List<Despesa> lista = new ArrayList<>();

        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM T_DESPESA WHERE ID_CLIENTE = ?");
        stm.setInt(1, idCliente);
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
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

            lista.add(despesa);
        }

        stm.close();
        return lista;
    }

    public void atualizarDespesa(Despesa despesa) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("UPDATE T_DESPESA SET VL_DESPESA = ?, CATEGORIA_DESPESA = ?, DT_VENCIMENTO = ?, OB_DESPESA = ?, ST_DESPESA = ? WHERE ID_DESPESA = ?");

        stm.setDouble(1, despesa.getValor());
        stm.setString(2, despesa.getCategoria());
        stm.setDate(3, Date.valueOf(despesa.getVencimento()));
        stm.setString(4, despesa.getDescricao());
        stm.setString(5, despesa.getStatus());
        stm.setInt(6, despesa.getId());

        int linhas = stm.executeUpdate();
        stm.close();

        if (linhas == 0) {
            throw new EntidadeNaoEncontradaException("Erro ao atualizar: nenhuma despesa foi encontrada com o ID informado.");
        }
    }

    public void deletarDespesa(int idDespesa) throws SQLException, EntidadeNaoEncontradaException {
       PreparedStatement stm = conexao.prepareStatement("DELETE FROM T_DESPESA WHERE ID_DESPESA = ?");
            stm.setInt(1, idDespesa);
            int linha = stm.executeUpdate();

            if (linha == 0) {
                throw new EntidadeNaoEncontradaException("Nenhuma despesa foi encontrada com o ID informado.");
            }

    }

    public double calcularDespesasPorCliente(int idCliente) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("SELECT SUM(VL_DESPESA) AS TOTAL FROM T_DESPESA WHERE ID_CLIENTE = ?");
        stm.setInt(1, idCliente);
        ResultSet rs = stm.executeQuery();

        if(rs.next()) {
            return rs.getDouble("TOTAL");
        } else {
            return 0;
        }
    }


}

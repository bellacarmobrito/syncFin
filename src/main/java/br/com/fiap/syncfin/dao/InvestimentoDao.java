package br.com.fiap.syncfin.dao;

import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.factory.ConnectionFactory;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;
import br.com.fiap.syncfin.model.Investimento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestimentoDao {

    private Connection conexao;

    public InvestimentoDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void fecharConexao() throws SQLException {
        conexao.close();
    }

    public void cadastrarInvestimento(Investimento investimento) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("INSERT INTO T_INVESTIMENTO (ID_CLIENTE, ID_CONTA, TIPO_INVESTIMENTO, VL_INVESTIMENTO, DT_INVESTIMENTO, RECORRENCIA, ST_INVESTIMENTO, RENDIMENTO, DT_VENCIMENTO) VALUES (?,?,?,?,?,?,?,?,?)");
        stm.setInt(1, investimento.getContaBancaria().getCliente().getIdCliente());
        stm.setInt(2, investimento.getContaBancaria().getIdConta());
        stm.setString(3, investimento.getTipoInvestimento());
        stm.setDouble(4, investimento.getValor());
        stm.setDate(5, Date.valueOf(investimento.getDataInvestimento()));
        stm.setString(6, investimento.getRecorrencia());
        stm.setString(7, investimento.getStatus());
        stm.setDouble(8, investimento.getRendimento());
        stm.setDate(9, Date.valueOf(investimento.getDataVencimento()));
        stm.executeUpdate();
    }

    public Investimento pesquisarInvestimentoPorId (int idInvestimento) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM T_INVESTIMENTO WHERE ID_INVESTIMENTO = ?");
        stm.setInt(1, idInvestimento);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {

            Cadastro cliente = new Cadastro();
            cliente.setIdCliente(rs.getInt("ID_CLIENTE"));

            ContaBancaria conta = new ContaBancaria();
            conta.setIdConta(rs.getInt("ID_CONTA"));
            conta.setCliente(cliente);

            Investimento investimento = new Investimento();
            investimento.setContaBancaria(conta);
            investimento.setId(rs.getInt("ID_INVESTIMENTO"));
            investimento.setValor(rs.getDouble("VL_INVESTIMENTO"));
            investimento.setTipoInvestimento(rs.getString("TIPO_INVESTIMENTO"));
            investimento.setDataInvestimento((rs.getDate("DT_INVESTIMENTO").toLocalDate()));
            investimento.setStatus(rs.getString("ST_INVESTIMENTO"));
            investimento.setRendimento(rs.getDouble("RENDIMENTO"));

            Date vencimento = rs.getDate("DT_VENCIMENTO");
            if (vencimento != null) {
                investimento.setDataVencimento(vencimento.toLocalDate());
            }

            String recorrencia = rs.getString("RECORRENCIA");
            if (recorrencia != null) {
                investimento.setRecorrencia(recorrencia);
            }

            stm.close();
            return investimento;
        } else {
            stm.close();
            throw new EntidadeNaoEncontradaException("Investimento não encontrado para o ID informado.");
        }
    }

    public List<Investimento> getAllInvestimentos() throws SQLException {
        List<Investimento> lista = new ArrayList<>();

        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM T_INVESTIMENTO");
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {

            Cadastro cliente = new Cadastro();
            cliente.setIdCliente(rs.getInt("ID_CLIENTE"));

            ContaBancaria conta = new ContaBancaria();
            conta.setIdConta(rs.getInt("ID_CONTA"));
            conta.setCliente(cliente);

            Investimento investimento = new Investimento();
            investimento.setContaBancaria(conta);
            investimento.setId(rs.getInt("ID_INVESTIMENTO"));
            investimento.setValor(rs.getDouble("VL_INVESTIMENTO"));
            investimento.setTipoInvestimento(rs.getString("TIPO_INVESTIMENTO"));
            investimento.setDataInvestimento((rs.getDate("DT_INVESTIMENTO").toLocalDate()));
            investimento.setStatus(rs.getString("ST_INVESTIMENTO"));
            investimento.setRendimento(rs.getDouble("RENDIMENTO"));

            Date vencimento = rs.getDate("DT_VENCIMENTO");
            if (vencimento != null) {
                investimento.setDataVencimento(vencimento.toLocalDate());
            }

            String recorrencia = rs.getString("RECORRENCIA");
            if (recorrencia != null) {
                investimento.setRecorrencia(recorrencia);
            }

            lista.add(investimento);
        }
        stm.close();
        return lista;
    }

    public List<Investimento> pesquisarInvestimentosPorCliente(int idCliente) throws SQLException {
        List<Investimento> lista = new ArrayList<>();

        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM T_INVESTIMENTO WHERE ID_CLIENTE = ?");
        stm.setInt(1, idCliente);
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {

            Cadastro cliente = new Cadastro();
            cliente.setIdCliente(rs.getInt("ID_CLIENTE"));

            ContaBancaria conta = new ContaBancaria();
            conta.setIdConta(rs.getInt("ID_CONTA"));
            conta.setCliente(cliente);

            Investimento investimento = new Investimento();
            investimento.setContaBancaria(conta);
            investimento.setId(rs.getInt("ID_INVESTIMENTO"));
            investimento.setValor(rs.getDouble("VL_INVESTIMENTO"));
            investimento.setTipoInvestimento(rs.getString("TIPO_INVESTIMENTO"));
            investimento.setDataInvestimento((rs.getDate("DT_INVESTIMENTO").toLocalDate()));
            investimento.setStatus(rs.getString("ST_INVESTIMENTO"));
            investimento.setRendimento(rs.getDouble("RENDIMENTO"));

            Date vencimento = rs.getDate("DT_VENCIMENTO");
            if (vencimento != null) {
                investimento.setDataVencimento(vencimento.toLocalDate());
            }

            String recorrencia = rs.getString("RECORRENCIA");
            if (recorrencia != null) {
                investimento.setRecorrencia(recorrencia);
            }

            lista.add(investimento);
        }
        stm.close();
        return lista;
    }

    public void atualizarInvestimento (Investimento investimento) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("UPDATE T_INVESTIMENTO SET TIPO_INVESTIMENTO = ?, VL_INVESTIMENTO = ?, DT_INVESTIMENTO = ?, RECORRENCIA = ?, ST_INVESTIMENTO = ?, RENDIMENTO = ?, DT_VENCIMENTO = ? WHERE ID_INVESTIMENTO = ?");
        stm.setString(1, investimento.getTipoInvestimento());
        stm.setDouble(2, investimento.getValor());
        stm.setDate(3, Date.valueOf(investimento.getDataInvestimento()));
        stm.setString(4, investimento.getRecorrencia());
        stm.setString(5, investimento.getStatus());
        stm.setDouble(6, investimento.getRendimento());
        stm.setDate(7, Date.valueOf(investimento.getDataVencimento()));
        stm.setInt(8, investimento.getId());

        int linhas = stm.executeUpdate();
        stm.close();

        if (linhas == 0) {
            throw new EntidadeNaoEncontradaException("Erro ao atualizar: nenhum investimento foi encontrado com o ID informado.");
        }
    }

    public void deletarInvestimento(int idInvestimento) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("DELETE FROM T_INVESTIMENTO WHERE ID_INVESTIMENTO = ?");
        stm.setInt(1, idInvestimento);
        int linha = stm.executeUpdate();

        if (linha == 0) {
            throw new EntidadeNaoEncontradaException("Nenhum investimento foi encontrado com o ID informado.");
        }
    }

    public double calcularInvestimentosPorCliente(int idCliente) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("SELECT SUM(VL_INVESTIMENTO) AS TOTAL FROM T_INVESTIMENTO WHERE ID_CLIENTE = ?");
        stm.setInt(1, idCliente);
        ResultSet rs = stm.executeQuery();

        if(rs.next()) {
            return rs.getDouble("TOTAL");
        } else {
            return 0;
        }
    }
}

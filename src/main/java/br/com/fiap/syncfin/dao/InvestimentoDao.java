package br.com.fiap.syncfin.dao;

import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;
import br.com.fiap.syncfin.model.Investimento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestimentoDao extends BaseDao {

    public InvestimentoDao() throws SQLException {
        super();
    }

    private Investimento mapInvestimento(ResultSet rs) throws SQLException {

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
        return investimento;
    }


    public void cadastrarInvestimento(Investimento investimento) throws SQLException {

        String sql = "INSERT INTO T_INVESTIMENTO (ID_CLIENTE, ID_CONTA, TIPO_INVESTIMENTO, VL_INVESTIMENTO, DT_INVESTIMENTO, RECORRENCIA, ST_INVESTIMENTO, RENDIMENTO, DT_VENCIMENTO) VALUES (?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, investimento.getContaBancaria().getCliente().getIdCliente());
            stm.setInt(2, investimento.getContaBancaria().getIdConta());
            stm.setString(3, investimento.getTipoInvestimento());
            stm.setDouble(4, investimento.getValor());
            stm.setDate(5, Date.valueOf(investimento.getDataInvestimento()));
            stm.setString(6, investimento.getRecorrencia());
            stm.setString(7, investimento.getStatus());
            stm.setDouble(8, investimento.getRendimento());

            if (investimento.getDataVencimento() != null) {
                stm.setDate(9, Date.valueOf(investimento.getDataVencimento()));
            } else {
                stm.setNull(9, Types.DATE);
            }
            stm.executeUpdate();
        }
    }

    public Investimento pesquisarInvestimentoPorId(int idInvestimento) throws SQLException, EntidadeNaoEncontradaException {

        String sql = "SELECT * FROM T_INVESTIMENTO WHERE ID_INVESTIMENTO = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idInvestimento);

            try (ResultSet rs = stm.executeQuery()) {
                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("Investimento não encontrado pelo ID informado.");
                }
                return mapInvestimento(rs);
            }
        }
    }

    public List<Investimento> pesquisarInvestimentosPorCliente(int idCliente) throws SQLException {

        String sql = "SELECT * FROM T_INVESTIMENTO WHERE ID_CLIENTE = ?";
        List<Investimento> investimentos = new ArrayList<>();

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idCliente);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    investimentos.add(mapInvestimento(rs));
                }
            }
        }
        return investimentos;
    }

    public Investimento pesquisarInvestimentoPorIdDoCliente(int idCliente, int idInvestimento) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_INVESTIMENTO WHERE ID_CLIENTE = ? AND ID_INVESTIMENTO = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idCliente);
            stm.setInt(2, idInvestimento);

            try (ResultSet rs = stm.executeQuery()) {
                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("Investimento não encontrado ou acesso negado");
                }
                return mapInvestimento(rs);
            }

        }
    }

    public void atualizarInvestimento(Investimento investimento) throws SQLException, EntidadeNaoEncontradaException {

        String sql = "UPDATE T_INVESTIMENTO SET TIPO_INVESTIMENTO = ?, VL_INVESTIMENTO = ?, DT_INVESTIMENTO = ?, RECORRENCIA = ?, ST_INVESTIMENTO = ?, RENDIMENTO = ?, DT_VENCIMENTO = ? WHERE ID_INVESTIMENTO = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {

            stm.setString(1, investimento.getTipoInvestimento());
            stm.setDouble(2, investimento.getValor());
            stm.setDate(3, Date.valueOf(investimento.getDataInvestimento()));
            stm.setString(4, investimento.getRecorrencia());
            stm.setString(5, investimento.getStatus());
            stm.setDouble(6, investimento.getRendimento());

            if (investimento.getDataVencimento() != null) {
                stm.setDate(7, Date.valueOf(investimento.getDataVencimento()));
            } else {
                stm.setNull(7, Types.DATE);
            }

            stm.setInt(8, investimento.getId());

            int linhas = stm.executeUpdate();

            if (linhas == 0) {
                throw new EntidadeNaoEncontradaException("Erro ao atualizar: nenhum investimento foi encontrado com o ID informado.");
            }
        }
    }

    public void atualizarInvestimentoDoCliente(Investimento investimento, int idCliente) throws SQLException, EntidadeNaoEncontradaException {

        String sql = "UPDATE T_INVESTIMENTO SET TIPO_INVESTIMENTO = ?, VL_INVESTIMENTO = ?, DT_INVESTIMENTO = ?, RECORRENCIA = ?, ST_INVESTIMENTO = ?, RENDIMENTO = ?, DT_VENCIMENTO = ? WHERE ID_INVESTIMENTO = ? AND ID_CLIENTE = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {

            stm.setString(1, investimento.getTipoInvestimento());
            stm.setDouble(2, investimento.getValor());
            stm.setDate(3, Date.valueOf(investimento.getDataInvestimento()));
            stm.setString(4, investimento.getRecorrencia());
            stm.setString(5, investimento.getStatus());
            stm.setDouble(6, investimento.getRendimento());

            if (investimento.getDataVencimento() != null) {
                stm.setDate(7, Date.valueOf(investimento.getDataVencimento()));
            } else {
                stm.setNull(7, Types.DATE);
            }

            stm.setInt(8, investimento.getId());
            stm.setInt(9, idCliente);

            int linhas = stm.executeUpdate();

            if (linhas == 0) {
                throw new EntidadeNaoEncontradaException("Erro ao atualizar: nenhum investimento foi encontrado com o ID informado.");
            }
        }
    }

    public void deletarInvestimento(int idInvestimento) throws SQLException, EntidadeNaoEncontradaException {

        String sql = "DELETE FROM T_INVESTIMENTO WHERE ID_INVESTIMENTO = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idInvestimento);

            int linha = stm.executeUpdate();

            if (linha == 0) {
                throw new EntidadeNaoEncontradaException("Nenhum investimento foi encontrado com o ID informado.");
            }
        }
    }

    public void deletarInvestimentoDoCliente(int idInvestimento, int idCliente) throws SQLException, EntidadeNaoEncontradaException {

        String sql = "DELETE FROM T_INVESTIMENTO WHERE ID_INVESTIMENTO = ? AND ID_CLIENTE = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idInvestimento);
            stm.setInt(2, idCliente);

            int linha = stm.executeUpdate();

            if (linha == 0) {
                throw new EntidadeNaoEncontradaException("Nenhum investimento foi encontrado com o ID informado.");
            }
        }
    }

    public double calcularInvestimentosPorCliente(int idCliente) throws SQLException {

        String sql = "SELECT SUM(VL_INVESTIMENTO) AS TOTAL FROM T_INVESTIMENTO WHERE ID_CLIENTE = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idCliente);

            try (ResultSet rs = stm.executeQuery()) {
                return rs.next() ? rs.getDouble("TOTAL") : 0;
            }
        }
    }
}

package br.com.fiap.syncfin.dao;

import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaBancariaDao extends BaseDao {

    public ContaBancariaDao() throws SQLException {
        super();
    }

    private ContaBancaria mapConta(ResultSet rs) throws SQLException {
        Cadastro cliente = new Cadastro();
        cliente.setIdCliente(rs.getInt("ID_CLIENTE"));

        ContaBancaria conta = new ContaBancaria(
                cliente,
                rs.getString("NM_INSTITUICAO"),
                rs.getString("AGENCIA"),
                rs.getString("NR_CONTA"),
                rs.getString("TIPO_CONTA"),
                rs.getDouble("SALDO_ATUAL")
        );
        conta.setIdConta(rs.getInt("ID_CONTA"));
        return conta;
    }

    public int cadastrarConta(ContaBancaria conta) throws SQLException {

        int idGerado;

        try (Statement stmtSeq = conexao.createStatement();
             ResultSet rs = stmtSeq.executeQuery("SELECT SEQ_T_CONTA_BANCARIA.NEXTVAL FROM DUAL")) {

            if (!rs.next()) {
                throw new SQLException("Não foi possível gerar ID da conta bancária");
            }
            idGerado = rs.getInt(1);

            String sql = "INSERT INTO T_CONTA_BANCARIA (ID_CONTA, ID_CLIENTE, NM_INSTITUICAO, AGENCIA, NR_CONTA, TIPO_CONTA, SALDO_ATUAL) VALUES (?,?,?,?,?,?,?)";

            try (PreparedStatement stm = conexao.prepareStatement(sql)) {
                stm.setInt(1, idGerado);
                stm.setInt(2, conta.getCliente().getIdCliente());
                stm.setString(3, conta.getNomeInstituicao());
                stm.setString(4, conta.getAgencia());
                stm.setString(5, conta.getNumeroConta());
                stm.setString(6, conta.getTipoConta());
                stm.setDouble(7, conta.getSaldo());
                stm.executeUpdate();

                return idGerado;
            }
        }
    }

    public ContaBancaria pesquisarContaPorId(int idConta) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM T_CONTA_BANCARIA WHERE ID_CONTA = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idConta);

            try (ResultSet rs = stm.executeQuery()) {
                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("Não há conta cadastrada com o ID informado.");
                }
                return mapConta(rs);
            }
        }
    }

    public ContaBancaria pesquisarContaPorIdCliente(int idCliente) throws SQLException, EntidadeNaoEncontradaException {

        String sql = "SELECT * FROM T_CONTA_BANCARIA WHERE ID_CLIENTE = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idCliente);

            try (ResultSet rs = stm.executeQuery()) {
                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("Não há conta cadastrada para o cliente informado.");
                }
                return mapConta(rs);
            }
        }
    }

    public List<ContaBancaria> getAllContas() throws SQLException {

        List<ContaBancaria> contas = new ArrayList<>();
        String sql = "SELECT * FROM T_CONTA_BANCARIA";

        try (PreparedStatement stm = conexao.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                contas.add(mapConta(rs));
            }
            return contas;
        }
    }

    public List<ContaBancaria> pesquisarContasPorCliente(int idCliente) throws SQLException {

        List<ContaBancaria> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_CONTA_BANCARIA WHERE ID_CLIENTE = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idCliente);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapConta(rs));
                }
                return lista;
            }
        }
    }

    public void atualizarConta(ContaBancaria conta) throws SQLException {

        String sql = "UPDATE T_CONTA_BANCARIA SET NM_INSTITUICAO = ?, AGENCIA = ?, NR_CONTA = ?, TIPO_CONTA = ?, SALDO_ATUAL = ? WHERE ID_CONTA = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setString(1, conta.getNomeInstituicao());
            stm.setString(2, conta.getAgencia());
            stm.setString(3, conta.getNumeroConta());
            stm.setString(4, conta.getTipoConta());
            stm.setDouble(5, conta.getSaldo());
            stm.setInt(6, conta.getIdConta());

            int linhasAfetadas = stm.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new SQLException("Nenhuma conta foi atualizada. Verifique o ID da conta.");
            }
        }
    }

    public void removerConta(int idConta) throws SQLException, EntidadeNaoEncontradaException {

        String sql = "DELETE FROM T_CONTA_BANCARIA WHERE ID_CONTA = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idConta);
            int linha = stm.executeUpdate();

            if (linha == 0) {
                throw new EntidadeNaoEncontradaException("Conta não localizada.");
            }
        }
    }

    public double calcularSaldoPorCliente(int idCliente) throws SQLException {

        String sql = "SELECT SUM(SALDO_ATUAL) AS TOTAL FROM T_CONTA_BANCARIA WHERE ID_CLIENTE = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idCliente);

            try (ResultSet rs = stm.executeQuery()) {
                return rs.next() ? rs.getDouble("TOTAL") : 0;
            }
        }

    }
}

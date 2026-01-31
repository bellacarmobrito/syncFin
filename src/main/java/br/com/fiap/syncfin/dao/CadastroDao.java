package br.com.fiap.syncfin.dao;

import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.factory.ConnectionFactory;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.util.CriptografiaUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CadastroDao {

    private Connection conexao;

    public CadastroDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public int cadastrar(Cadastro cadastro) throws SQLException {

        Statement stmtSeq = conexao.createStatement();
        ResultSet rs = stmtSeq.executeQuery("SELECT SEQ_T_CLIENTE.NEXTVAL FROM DUAL");

        int idGerado = -1;
        if (rs.next()) {
            idGerado = rs.getInt(1);
        }

        PreparedStatement stm = conexao.prepareStatement("INSERT INTO T_CLIENTE (ID_CLIENTE, NM_CLIENTE, NR_CELULAR, NR_CPF, EMAIL, SENHA, ST_CONTA) \n" +
                "VALUES (?, ?,?,?,?,?,?)");
        stm.setInt(1, idGerado);
        stm.setString(2, cadastro.getNomeCliente());
        stm.setString(3, cadastro.getCelular());
        stm.setString(4, cadastro.getCpf());
        stm.setString(5, cadastro.getEmail());
        stm.setString(6, cadastro.getSenha());
        stm.setString(7, cadastro.isStatusConta() ? "Ativa" : "Inativa");
        stm.executeUpdate();

        return idGerado;
    }

    public void fecharConexao() throws SQLException {
        conexao.close();
    }

    private Cadastro parseCliente(ResultSet result) throws SQLException {
        int idCliente = result.getInt("ID_CLIENTE");
        String nome = result.getString("NM_CLIENTE");
        String celular = result.getString("NR_CELULAR");
        String cpf = result.getString("NR_CPF");
        String email = result.getString("EMAIL");
        String senha = result.getString("SENHA");
        String status = result.getString("ST_CONTA");
        Timestamp dataCadastro = result.getTimestamp("DT_CADASTRO");
        return new Cadastro(idCliente, nome, celular, cpf, email, senha, dataCadastro.toLocalDateTime(), status );
    }


    public Cadastro pesquisar(int id ) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM T_CLIENTE WHERE ID_CLIENTE = ?");
        stm.setInt(1, id);
        ResultSet result = stm.executeQuery();

        if (!result.next())
            throw new EntidadeNaoEncontradaException("Cliente não localizado");

        return parseCliente(result);
    }

    public List<Cadastro> getAll() throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM T_CLIENTE");
        ResultSet result = stm.executeQuery();
        List<Cadastro> lista = new ArrayList<>();

        while(result.next()) {
            lista.add(parseCliente(result));
        }
        return lista;
    }

    public void atualizar(Cadastro cadastro) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("UPDATE T_CLIENTE SET NM_CLIENTE = ?, NR_CELULAR = ?, NR_CPF = ?, EMAIL = ?, SENHA = ? WHERE ID_CLIENTE = ?");
        stm.setString(1, cadastro.getNomeCliente());
        stm.setString(2, cadastro.getCelular());
        stm.setString(3, cadastro.getCpf());
        stm.setString(4, cadastro.getEmail());
        stm.setString(5, cadastro.getSenha());
        stm.setInt(6, cadastro.getIdCliente());
        stm.executeUpdate();
    }

    public void inativarCadastro(int id) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("UPDATE T_CLIENTE SET ST_CONTA = 'Inativa' WHERE ID_CLIENTE = ?");
        stm.setInt(1, id);
        int linha = stm.executeUpdate();
        if (linha == 0) {
            throw new EntidadeNaoEncontradaException("Cliente não localizado.");
        }
    }

    public Cadastro autenticarUsuario(String email, String senhaDigitada) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM T_CLIENTE WHERE EMAIL = ?");
        stm.setString(1, email);

        ResultSet rs = stm.executeQuery();
        if (!rs.next()) return null;
        Cadastro cliente = parseCliente(rs);

        String senhaHash;

        try{
            senhaHash = CriptografiaUtils.criptografar(senhaDigitada);

        } catch (Exception e){
            throw new SQLException("Falha ao criptografar senha digitada.", e);
        }

        return senhaHash.equals(cliente.getSenha()) ? cliente : null;
    }

    public Cadastro pesquisarPorEmail(String email) throws SQLException, EntidadeNaoEncontradaException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM T_CLIENTE WHERE EMAIL = ?");
        stm.setString(1, email);
        ResultSet result = stm.executeQuery();

        if (!result.next()) {
            throw new EntidadeNaoEncontradaException("Cliente não localizado por e-mail");
        }

        return parseCliente(result);
    }



}

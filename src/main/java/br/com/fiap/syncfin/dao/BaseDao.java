package br.com.fiap.syncfin.dao;

import br.com.fiap.syncfin.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDao implements AutoCloseable{

    protected Connection conexao;

    protected BaseDao() throws SQLException {
        this.conexao = ConnectionFactory.getConnection();
    }

    @Override
    public void close() {
        try{
            if(conexao != null && !conexao.isClosed()){
                conexao.close();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}

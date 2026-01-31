package br.com.fiap.syncfin.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = System.getenv("DB_URL");

    private static final String USUARIO = System.getenv("DB_USER");

    private static final String SENHA = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não  encontrado");
        }

        if (URL == null || USUARIO == null || SENHA == null) {
            throw new SQLException("DB_URL/DB_USER/DB_PASSWORD não configuradas no ambiente.");
        }


        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}

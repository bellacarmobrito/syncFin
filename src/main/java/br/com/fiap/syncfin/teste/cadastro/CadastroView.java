package br.com.fiap.syncfin.teste.cadastro;

import br.com.fiap.syncfin.dao.CadastroDao;
import br.com.fiap.syncfin.dao.EnderecoDao;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.Endereco;

import java.sql.SQLException;

public class CadastroView {

    public static void main(String[] args) {

        try{
            CadastroDao cadastrodao = new CadastroDao();
            EnderecoDao enderecoDao = new EnderecoDao();

            Endereco endereco = new Endereco("Jesus Walks", 123, "Centro", "12345-678", "São Paulo", "SP");
            Cadastro cadastro = new Cadastro("Kanye West", endereco,  "1194586644", "37019268532", "isa_gcb@hotmail.com","Tabela01", "Ativa");

            int idCliente = cadastrodao.cadastrar(cadastro);
            enderecoDao.cadastrarEndereco(endereco, idCliente);

            cadastrodao.fecharConexao();
            enderecoDao.fecharConexao();

            System.out.println("Cadastro Realizado!");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}

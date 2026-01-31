package br.com.fiap.syncfin.teste.endereco;

import br.com.fiap.syncfin.dao.EnderecoDao;
import br.com.fiap.syncfin.model.Endereco;

import java.sql.SQLException;

public class atualizarEnderecoView {

    public static void main(String[] args) {

        try {
            EnderecoDao dao = new EnderecoDao();
            Endereco novoEndereco = new Endereco();
            novoEndereco.setLogradouro("Rua Martin de Sá");
            novoEndereco.setNumero(357);
            novoEndereco.setBairro("Vila Rica");
            novoEndereco.setCep("0986560");
            novoEndereco.setCidade("Belo Horizonte");
            novoEndereco.setEstado("MG");
            dao.atualizarEndereco(novoEndereco);
            dao.fecharConexao();

            System.out.println("Endereço atualizado com sucesso!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}

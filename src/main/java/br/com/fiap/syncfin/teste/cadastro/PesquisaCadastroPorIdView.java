package br.com.fiap.syncfin.teste.cadastro;

import br.com.fiap.syncfin.dao.CadastroDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Cadastro;

import java.sql.SQLException;

public class PesquisaCadastroPorIdView {

    public static void main(String[] args) {

        try {
            CadastroDao dao = new CadastroDao();
            Cadastro cadastro = dao.pesquisar(8);

            System.out.println("Detalhamento do Cadastro");
            System.out.println("-------------------------------------------");
            System.out.println("ID: " + cadastro.getIdCliente());
            System.out.println("Nome: " + cadastro.getNomeCliente());
            System.out.println("Celular: " + cadastro.getCelular());
            System.out.println("CPF: " + cadastro.getCpf());
            System.out.println("E-mail: " + cadastro.getEmail());
            System.out.println("Senha: " + cadastro.getSenha());
            System.out.println("Data de Cadastro: " + cadastro.getDataCadastro());
            System.out.println("Status da Conta: " + (cadastro.isStatusConta() ? "Ativa" : "Inativa"));

            dao.fecharConexao();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("O código não existe na tabela");
        }

    }
}

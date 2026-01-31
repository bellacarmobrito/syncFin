package br.com.fiap.syncfin.teste.cadastro;

import br.com.fiap.syncfin.dao.CadastroDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Cadastro;

import java.sql.SQLException;

public class AtualizacaoCadastroView {

    public static void main(String[] args) {

        try {
            CadastroDao dao = new CadastroDao();
            Cadastro cadastro = dao.pesquisar(5);

            cadastro.setNomeCliente("Kimberly Clark");
            cadastro.setCelular("11958986589");
            cadastro.setCpf("81783130067");
            cadastro.setEmail("kimberly.clark@email.com");
            cadastro.setSenha("j<*UB0)356q8");

            dao.atualizar(cadastro);
            System.out.println("Cadastro atualizado com sucesso!");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Cadastro não localizado");
        }
    }
}

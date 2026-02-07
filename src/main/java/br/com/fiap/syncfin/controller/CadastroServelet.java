package br.com.fiap.syncfin.controller;

import br.com.fiap.syncfin.dao.CadastroDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.util.CriptografiaUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cadastro")
public class CadastroServelet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String acao = req.getParameter("acao");

        switch (acao) {
            case "cadastrar":
                try {
                    cadastrar(req, resp);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "editar":
                editar(req, resp);
                break;
            case "excluir":
                excluir(req, resp);

        }
    }

    private void cadastrar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, NoSuchAlgorithmException {
        String nomeCliente = req.getParameter("nomeCliente");
        String telefone = req.getParameter("telefone");
        String cpf = req.getParameter("cpf");
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");
        String status = req.getParameter("status");

        senha = CriptografiaUtils.criptografar(senha);

        Cadastro cadastro = new Cadastro(nomeCliente, telefone, cpf, email, senha, status);

        try (CadastroDao dao = new CadastroDao()) {
            dao.cadastrar(cadastro);
            req.setAttribute("mensagem", "Cadastro realizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro  ao cadastrar");
        }
        req.getRequestDispatcher("cadastro-cliente.jsp").forward(req, resp);
    }

    public void editar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (CadastroDao dao = new CadastroDao()) {

            int idCliente = Integer.parseInt(req.getParameter("codigo"));

            String nomeCliente = req.getParameter("nomeCliente");
            String telefone = req.getParameter("telefone");
            String cpf = req.getParameter("cpf");
            String email = req.getParameter("email");
            String senha = req.getParameter("senha");

            Cadastro cadastro = new Cadastro();
            cadastro.setIdCliente(idCliente);
            cadastro.setNomeCliente(nomeCliente);
            cadastro.setCelular(telefone);
            cadastro.setCpf(cpf);
            cadastro.setEmail(email);

            if (senha != null && !senha.isBlank()) {
                cadastro.setSenha(CriptografiaUtils.criptografar(senha));
                dao.atualizar(cadastro);
            } else {
                dao.atualizarSemSenha(cadastro);
            }

            HttpSession session = req.getSession();
            session.setAttribute("cliente", cadastro);
            req.setAttribute("mensagem", "Cadastro atualizado com sucesso!");

            resp.sendRedirect("cadastro?acao=listar");

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao atualizar");
            req.getRequestDispatcher("editar-cadastro.jsp").forward(req, resp);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void excluir(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int codigo = Integer.parseInt(req.getParameter("codigoExcluir"));

        try (CadastroDao dao = new CadastroDao()) {
            dao.inativarCadastro(codigo);
            req.setAttribute("mensagem", "cadastro desativado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (EntidadeNaoEncontradaException e) {
            req.setAttribute("erro", "Erro ao desativar cadastro");
        }
        listar(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String acao = req.getParameter("acao");

        switch (acao) {
            case "listar":
                listar(req, resp);
                break;
            case "abrir-form-edicao":
                abrirForm(req, resp);
                break;
        }
    }

    private void abrirForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (CadastroDao dao = new CadastroDao()) {

            int id = Integer.parseInt(req.getParameter("codigo"));
            Cadastro cadastro = dao.pesquisar(id);
            req.setAttribute("cadastro", cadastro);
            req.getRequestDispatcher("editar-cadastro.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (EntidadeNaoEncontradaException e) {
            throw new RuntimeException(e);
        }
    }

    private void listar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Cadastro> clientes;

        try (CadastroDao dao = new CadastroDao()) {
            clientes = dao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("clientes", clientes);
        req.getRequestDispatcher("visualizar-cadastro.jsp").forward(req, resp);
    }
}

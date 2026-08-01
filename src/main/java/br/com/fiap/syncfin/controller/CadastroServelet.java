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
import java.sql.SQLException;

@WebServlet("/cadastro")
public class CadastroServelet extends HttpServlet {

    private Cadastro getClienteLogado(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        if (session == null) {
            resp.sendRedirect("index.jsp");
            return null;
        }

        Cadastro cliente = (Cadastro) session.getAttribute("cliente");

        if (cliente == null) {
            resp.sendRedirect("index.jsp");
            return null;
        }
        return cliente;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String acao = req.getParameter("acao");

        switch (acao) {
            case "cadastrar":
                cadastrar(req, resp);
                break;
            case "editar":
                editar(req, resp);
                break;
            case "excluir":
                excluir(req, resp);

        }
    }

    private void cadastrar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nomeCliente = req.getParameter("nomeCliente");
        String telefone = req.getParameter("telefone");
        String cpf = req.getParameter("cpf");
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        senha = CriptografiaUtils.criptografar(senha);

        Cadastro cadastro = new Cadastro(nomeCliente, telefone, cpf, email, senha, null);

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

        Cadastro clienteLogado = getClienteLogado(req, resp);
        if (clienteLogado == null) return;

        int idCliente = Integer.parseInt(req.getParameter("codigo"));

        if (idCliente != clienteLogado.getIdCliente()) {
            HttpSession session = req.getSession(false);
            if (session != null) session.setAttribute("erro", "Cadastro não localizado ou acesso negado.");
            resp.sendRedirect("home");
            return;
        }

        try (CadastroDao dao = new CadastroDao()) {

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
        }
    }

    private void excluir(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cadastro clienteLogado = getClienteLogado(req, resp);
        if (clienteLogado == null) return;

        int codigo = Integer.parseInt(req.getParameter("codigoExcluir"));

        if (codigo != clienteLogado.getIdCliente()) {
            req.setAttribute("erro", "Cadastro não localizado ou acesso negado.");
            listar(req, resp);
            return;
        }

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

        Cadastro clienteLogado = getClienteLogado(req, resp);
        if (clienteLogado == null) return;

        int id = Integer.parseInt(req.getParameter("codigo"));

        if (id != clienteLogado.getIdCliente()) {
            HttpSession session = req.getSession(false);
            if (session != null) session.setAttribute("erro", "Cadastro não localizado ou acesso negado.");
            resp.sendRedirect("home");
            return;
        }

        try (CadastroDao dao = new CadastroDao()) {
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

        Cadastro clienteLogado = getClienteLogado(req, resp);
        if (clienteLogado == null) return;

        req.getRequestDispatcher("visualizar-cadastro.jsp").forward(req, resp);
    }
}

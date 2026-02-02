package br.com.fiap.syncfin.controller;

import br.com.fiap.syncfin.dao.ContaBancariaDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/conta-bancaria")
public class ContaBancariaServlet extends HttpServlet {

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


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");

        if ("editar".equals(acao)) {
            abrirFormEdicao(req, resp);
        } else if ("listar".equals(acao)) {
            listarContas(req, resp);
        }
    }

    private void abrirFormEdicao(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;

        int idConta;
        try {
            idConta = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            HttpSession session = req.getSession(false);
            if (session != null) session.setAttribute("erro", "ID da conta inválido.");
            resp.sendRedirect("conta-bancaria?acao=listar");
            return;
        }

        try (ContaBancariaDao dao = new ContaBancariaDao()) {
            ContaBancaria conta = dao.pesquisarContaPorIdDoCliente(idConta, cliente.getIdCliente());

            req.setAttribute("conta", conta);
            req.getRequestDispatcher("editar-conta.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao abrir o formulário");
            req.getRequestDispatcher("home.jsp").forward(req, resp);
        }

    }

    private void cadastrarConta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;

        HttpSession session = req.getSession(false);

        try (ContaBancariaDao dao = new ContaBancariaDao()) {
            String instituicao = req.getParameter("instituicao");
            String agencia = req.getParameter("agencia");
            String numeroConta = req.getParameter("numeroConta");
            String tipo = req.getParameter("tipoConta");
            double saldo = Double.parseDouble(req.getParameter("saldo"));

            ContaBancaria conta = new ContaBancaria(cliente, instituicao, agencia, numeroConta, tipo, saldo);
            int idConta = dao.cadastrarConta(conta);

            conta.setIdConta(idConta);
            session.setAttribute("conta", conta);
            session.removeAttribute("mensagemConta");

            req.setAttribute("mensagem", "Conta cadastrada com sucesso!");
            req.getRequestDispatcher("cadastro-conta.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao cadastrar conta");
            req.getRequestDispatcher("cadastro-conta.jsp").forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");

        if ("cadastrar".equals(acao)) {
            cadastrarConta(req, resp);
        } else if ("atualizar".equals(acao)) {
            atualizarConta(req, resp);
        } else if ("excluir".equals(acao)) {
            excluirConta(req, resp);
        }
    }

    private void atualizarConta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;

        int idConta;
        try {
            idConta = Integer.parseInt(req.getParameter("idConta"));
        } catch (NumberFormatException e) {
            HttpSession session = req.getSession(false);
            if (session != null) session.setAttribute("erro", "ID da conta inválido.");
            resp.sendRedirect("conta-bancaria?acao=listar");
            return;
        }

        try (ContaBancariaDao dao = new ContaBancariaDao()) {

            String instituicao = req.getParameter("instituicao");
            String agencia = req.getParameter("agencia");
            String numeroConta = req.getParameter("numeroConta");
            String tipo = req.getParameter("tipoConta");
            double saldo = Double.parseDouble(req.getParameter("saldo"));

            ContaBancaria conta = new ContaBancaria();
            conta.setIdConta(idConta);
            conta.setCliente(cliente);
            conta.setNomeInstituicao(instituicao);
            conta.setAgencia(agencia);
            conta.setNumeroConta(numeroConta);
            conta.setTipoConta(tipo);
            conta.setSaldo(saldo);

            dao.atualizarContaDoCliente(conta, cliente.getIdCliente());

            HttpSession session = req.getSession(false);

            if (session != null) {
                ContaBancaria contaSessao = (ContaBancaria) session.getAttribute("conta");
                if (contaSessao != null && contaSessao.getIdConta() == idConta) {
                    session.setAttribute("conta", conta);
                }
            }

            req.setAttribute("conta", conta);
            req.setAttribute("mensagem", "Conta atualizada com sucesso!");
            req.getRequestDispatcher("editar-conta.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao atualizar conta");
            req.getRequestDispatcher("editar-conta.jsp").forward(req, resp);
        }
    }

    private void excluirConta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;

        int idConta;

        try {
            idConta = Integer.parseInt(req.getParameter("codigoExcluir"));
        } catch (NumberFormatException e) {
            req.setAttribute("erro", "ID da conta inválido");
            listarContas(req, resp);
            return;
        }

        try (ContaBancariaDao dao = new ContaBancariaDao()) {
            dao.removerContaDoCliente(idConta, cliente.getIdCliente());
            req.setAttribute("mensagem", "Conta excluída com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao excluir conta. Verifique se não há receitas ou despesas associadas.");
        }
        listarContas(req, resp);
    }

    private void listarContas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;

        try (ContaBancariaDao dao = new ContaBancariaDao()) {
            List<ContaBancaria> contas = dao.pesquisarContasPorCliente(cliente.getIdCliente());
            req.setAttribute("contas", contas);

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao listar contas bancárias.");
        }
        req.getRequestDispatcher("lista-conta.jsp").forward(req, resp);
    }
}

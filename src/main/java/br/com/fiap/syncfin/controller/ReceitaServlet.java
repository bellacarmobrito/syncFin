package br.com.fiap.syncfin.controller;

import br.com.fiap.syncfin.dao.ReceitaDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;
import br.com.fiap.syncfin.model.Receita;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/receita")
public class ReceitaServlet extends HttpServlet {

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
            listarReceitas(req, resp);
        }
    }

    private void abrirFormEdicao(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;

        int idReceita;
        try {
            idReceita = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            HttpSession session = req.getSession(false);
            if (session != null) session.setAttribute("erro", "ID de receita inválido.");
            resp.sendRedirect("receita?acao=listar");
            return;
        }

        try (ReceitaDao receitaDao = new ReceitaDao()) {
            Receita receita = receitaDao.pesquisarReceitaPorIdDoCliente(cliente.getIdCliente(), idReceita);
            req.setAttribute("receita", receita);
            req.getRequestDispatcher("editar-receita.jsp").forward(req, resp);

        } catch (EntidadeNaoEncontradaException e) {
            HttpSession session = req.getSession(false);
            if (session != null) session.setAttribute("erro", e.getMessage());
            resp.sendRedirect("receita?acao=listar");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao abrir formulário");
            req.getRequestDispatcher("home.jsp").forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");

        if ("cadastrar".equals(acao)) {
            cadastrarReceita(req, resp);
        } else if ("atualizar".equals(acao)) {
            atualizarReceita(req, resp);
        } else if ("excluir".equals(acao)) {
            excluirReceita(req, resp);
        }
    }

    private void listarReceitas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;

        try (ReceitaDao receitaDao = new ReceitaDao()) {
            List<Receita> lista = receitaDao.pesquisarReceitasPorCliente(cliente.getIdCliente());
            req.setAttribute("receitas", lista);

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao listar receitas");
        }
        req.getRequestDispatcher("lista-receita.jsp").forward(req, resp);
    }

    private void atualizarReceita(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;

        int idReceita;
        try {
            idReceita = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            HttpSession session = req.getSession(false);
            if (session != null) session.setAttribute("erro", "ID de receita inválido.");
            resp.sendRedirect("receitaa?acao=listar");
            return;
        }

        try (ReceitaDao receitaDao = new ReceitaDao()) {
            String categoria = req.getParameter("categoria");
            String descricao = req.getParameter("descricao");
            String status = req.getParameter("status");
            LocalDate recebimento = LocalDate.parse(req.getParameter("dataRecebimento"));

            double valor = Double.parseDouble(req.getParameter("valor"));

            if (valor <= 0) {
                req.setAttribute("erro", "O valor da receita deve ser maior que zero.");
                Receita atual = receitaDao.pesquisarReceitaPorIdDoCliente(cliente.getIdCliente(), idReceita);
                req.setAttribute("receita", atual);
                req.getRequestDispatcher("editar-receita.jsp").forward(req, resp);
                return;
            }

            Receita receita = new Receita();
            receita.setId(idReceita);
            receita.setCategoria(categoria);
            receita.setDescricao(descricao);
            receita.setStatus(status);
            receita.setDataRecebimento(recebimento);
            receita.setValor(valor);

            receitaDao.atualizarReceitaDoCliente(receita, cliente.getIdCliente());

            req.setAttribute("receita", receita);
            req.setAttribute("mensagem", "Receita atualizada com sucesso!");

        } catch (EntidadeNaoEncontradaException e) {
            req.setAttribute("erro", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao atualizar receita.");
        }

        req.getRequestDispatcher("editar-receita.jsp").forward(req, resp);
    }

    private void cadastrarReceita(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;
        HttpSession session = req.getSession(false);
        ContaBancaria conta = (ContaBancaria) session.getAttribute("conta");

        if (conta == null) {
            resp.sendRedirect("erro-conta-obrigatoria.jsp?origem=receita");
            return;
        }

        try (ReceitaDao receitaDao = new ReceitaDao()) {
            double valor = Double.parseDouble(req.getParameter("valor"));
            String categoria = req.getParameter("categoria");
            LocalDate recebimento = LocalDate.parse(req.getParameter("dataRecebimento"));
            String descricao = req.getParameter("descricao");
            String status = req.getParameter("status");

            if (valor <= 0) {
                req.setAttribute("erro", "Valor da receita deve ser maior que zero.");
                req.getRequestDispatcher("cadastro-receita.jsp").forward(req, resp);
                return;
            }

            conta.setCliente(cliente);

            Receita receita = new Receita();
            receita.setContaBancaria(conta);
            receita.setValor(valor);
            receita.setCategoria(categoria);
            receita.setDataRecebimento(recebimento);
            receita.setDescricao(descricao);
            receita.setStatus(status);

            receitaDao.cadastrarReceita(receita);
            req.setAttribute("mensagem", "Receita cadastrada com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao cadastrar receita");
        }
        req.getRequestDispatcher("cadastro-receita.jsp").forward(req, resp);
    }

    private void excluirReceita(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;

        int idReceita;
        try {
            idReceita = Integer.parseInt(req.getParameter("codigoExcluir"));
        } catch (NumberFormatException e) {
            req.setAttribute("erro", "ID de receita inválido.");
            listarReceitas(req, resp);
            return;
        }

        try (ReceitaDao receitaDao = new ReceitaDao()) {

            Receita receita = receitaDao.pesquisarReceitaPorIdDoCliente(cliente.getIdCliente(), idReceita);

            if ("Recebido".equalsIgnoreCase(receita.getStatus())) {
                req.setAttribute("erro", "Receitas com status 'Recebido' não podem ser excluídas.");
            } else {
                receitaDao.deletarReceitaDoCliente(cliente.getIdCliente(), idReceita);
                req.setAttribute("mensagem", "Receita excluída com sucesso!");
            }

            List<Receita> receitas = receitaDao.pesquisarReceitasPorCliente(cliente.getIdCliente());
            req.setAttribute("receitas", receitas);

        } catch (EntidadeNaoEncontradaException e) {
            req.setAttribute("erro", "Receita não localizada ou acesso negado.");
            listarReceitas(req, resp);
            return;

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao excluir receita");
        }
        req.getRequestDispatcher("lista-receita.jsp").forward(req, resp);
    }
}


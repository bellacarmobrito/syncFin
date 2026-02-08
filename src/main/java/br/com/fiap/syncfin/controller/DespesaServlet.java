package br.com.fiap.syncfin.controller;

import br.com.fiap.syncfin.dao.DespesaDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;
import br.com.fiap.syncfin.model.Despesa;
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

@WebServlet("/despesa")
public class DespesaServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");

        if ("editar".equals(acao)) {
            abrirFormEdicao(req, resp);
        } else if ("listar".equals(acao)) {
            listarDespesas(req, resp);
        }
    }


    private void abrirFormEdicao(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;

        int idDespesa;
        try {
            idDespesa = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            HttpSession session = req.getSession(false);
            if (session != null) session.setAttribute("erro", "ID de despesa inválido.");
            resp.sendRedirect("despesa?acao=listar");
            return;
        }

        try (DespesaDao despesaDao = new DespesaDao()) {
            Despesa despesa = despesaDao.pesquisarDespesaPorIdDoCliente(cliente.getIdCliente(), idDespesa);
            req.setAttribute("despesa", despesa);
            req.getRequestDispatcher("editar-despesa.jsp").forward(req, resp);

        } catch (EntidadeNaoEncontradaException e) {
            HttpSession session = req.getSession(false);
            if (session != null) session.setAttribute("erro", e.getMessage());
            resp.sendRedirect("despesa?acao=listar");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao abrir o formulário");
            req.getRequestDispatcher("home.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");

        if ("cadastrar".equals(acao)) {
            cadastrarDespesa(req, resp);
        } else if ("atualizar".equals(acao)) {
            atualizar(req, resp);
        } else if ("excluir".equals(acao)) {
            excluirDespesa(req, resp);
        }
    }

    private void listarDespesas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;

        try (DespesaDao despesaDao = new DespesaDao()) {
            List<Despesa> lista = despesaDao.pesquisarDespesasPorCliente(cliente.getIdCliente());
            req.setAttribute("despesas", lista);

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao listar despesas");
        }
        req.getRequestDispatcher("lista-despesa.jsp").forward(req, resp);
    }

    private void atualizar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;

        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            HttpSession session = req.getSession(false);
            if (session != null) session.setAttribute("erro", "ID de despesa inválido.");
            resp.sendRedirect("despesa?acao=listar");
            return;
        }

        try (DespesaDao despesaDao = new DespesaDao()) {
            String categoria = req.getParameter("categoria");
            String descricao = req.getParameter("descricao");
            String status = req.getParameter("status");
            LocalDate vencimento = LocalDate.parse(req.getParameter("vencimento"));
            double valor = Double.parseDouble(req.getParameter("valor"));

            if (valor <= 0) {
                req.setAttribute("erro", "O valor da despesa deve ser maior que zero.");
                Despesa atual = despesaDao.pesquisarDespesaPorIdDoCliente(cliente.getIdCliente(), id);
                req.setAttribute("despesa", atual);
                req.getRequestDispatcher("editar-despesa.jsp").forward(req, resp);
                return;
            }

            Despesa despesa = new Despesa();
            despesa.setId(id);
            despesa.setCategoria(categoria);
            despesa.setDescricao(descricao);
            despesa.setValor(valor);
            despesa.setVencimento(vencimento);
            despesa.setStatus(status);

            despesaDao.atualizarDespesaDoCliente(despesa, cliente.getIdCliente());

            req.setAttribute("despesa", despesa);
            req.setAttribute("mensagem", "Despesa atualizada com sucesso!");

        } catch (EntidadeNaoEncontradaException e) {
            req.setAttribute("erro", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao atualizar despesa.");
        }

        req.getRequestDispatcher("editar-despesa.jsp").forward(req, resp);
    }

    private void cadastrarDespesa(HttpServletRequest req, HttpServletResponse resp) throws
            IOException, ServletException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;
        HttpSession session = req.getSession(false);
        ContaBancaria conta = (ContaBancaria) session.getAttribute("conta");

        if (conta == null) {
            resp.sendRedirect("erro-conta-obrigatoria.jsp?origem=despesa");
            return;
        }

        try (DespesaDao despesaDao = new DespesaDao()) {
            double valor = Double.parseDouble(req.getParameter("valor"));
            String categoria = req.getParameter("categoria");
            LocalDate vencimento = LocalDate.parse(req.getParameter("vencimento"));
            String descricao = req.getParameter("descricao");
            String status = req.getParameter("status");

            if (valor <= 0) {
                req.setAttribute("erro", "Valor da despesa deve ser maior que zero.");
                req.getRequestDispatcher("cadastro-despesa.jsp").forward(req, resp);
                return;
            }

            conta.setCliente(cliente);

            Despesa despesa = new Despesa();
            despesa.setContaBancaria(conta);
            despesa.setValor(valor);
            despesa.setCategoria(categoria);
            despesa.setVencimento(vencimento);
            despesa.setDescricao(descricao);
            despesa.setStatus(status);

            despesaDao.cadastrarDespesa(despesa);
            req.setAttribute("mensagem", "Despesa cadastrada com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao cadastrar despesa");
        }
        req.getRequestDispatcher("cadastro-despesa.jsp").forward(req, resp);
    }

    private void excluirDespesa(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;


        int idDespesa;
        try {
            idDespesa = Integer.parseInt(req.getParameter("codigoExcluir"));
        } catch (NumberFormatException e) {
            req.setAttribute("erro", "ID de despesa inválido.");
            listarDespesas(req, resp);
            return;
        }

        try (DespesaDao despesaDao = new DespesaDao()) {

            Despesa despesa = despesaDao.pesquisarDespesaPorIdDoCliente(idDespesa, cliente.getIdCliente());

            if ("Pago".equalsIgnoreCase(despesa.getStatus())) {
                req.setAttribute("erro", "Despesas com status 'Pago' não podem ser excluídas.");
            } else {
                despesaDao.deletarDespesaDoCliente(cliente.getIdCliente(), idDespesa);
                req.setAttribute("mensagem", "Despesa excluída com sucesso!");
            }

            List<Despesa> despesas = despesaDao.pesquisarDespesasPorCliente(cliente.getIdCliente());
            req.setAttribute("despesas", despesas);

        } catch (EntidadeNaoEncontradaException e) {
            req.setAttribute("erro", "Despesa não localizada ou acesso negado.");
            listarDespesas(req, resp);
            return;

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao excluir despesa");
        }
        req.getRequestDispatcher("lista-despesa.jsp").forward(req, resp);
    }

}

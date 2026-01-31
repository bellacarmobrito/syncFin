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

        DespesaDao dao = null;

        try {
            dao = new DespesaDao();
            int idDespesa = Integer.parseInt(req.getParameter("id"));
            Despesa despesa = dao.pesquisarDespesaPorId(idDespesa);
            req.setAttribute("despesa", despesa);
            req.getRequestDispatcher("editar-despesa.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao abrir o formulário");
            req.getRequestDispatcher("home.jsp").forward(req, resp);
        } finally {
            fecharDao(dao);
        }
    }

    private void fecharDao(DespesaDao dao) {
        try {
            if (dao != null) {
                dao.fecharConexao();
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

        DespesaDao dao = null;

        try {
            dao = new DespesaDao();
            HttpSession session = req.getSession();
            Cadastro cliente = (Cadastro) session.getAttribute("cliente");

            List<Despesa> lista = dao.pesquisarDespesasPorCliente(cliente.getIdCliente());
            req.setAttribute("despesas", lista);

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao listar despesas");
        } finally {
            fecharDao(dao);
        }
        req.getRequestDispatcher("lista-despesa.jsp").forward(req, resp);
    }

    private void atualizar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DespesaDao dao = null;

        try {
            dao = new DespesaDao();
            int id = Integer.parseInt(req.getParameter("id"));
            String categoria = req.getParameter("categoria");
            String descricao = req.getParameter("descricao");
            String status = req.getParameter("status");
            LocalDate vencimento = LocalDate.parse(req.getParameter("vencimento"));

            double valor = Double.parseDouble(req.getParameter("valor"));

            if (valor <= 0) {
                req.setAttribute("erro", "O valor da despesa deve ser maior que zero.");
                abrirFormEdicao(req, resp);
                return;
            }

            Despesa despesa = new Despesa();
            despesa.setId(id);
            despesa.setCategoria(categoria);
            despesa.setDescricao(descricao);
            despesa.setValor(valor);
            despesa.setVencimento(vencimento);
            despesa.setStatus(status);

            dao.atualizarDespesa(despesa);

            req.setAttribute("despesa", despesa);
            req.setAttribute("mensagem", "Despesa atualizada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao atualizar despesa.");
        } finally {
            fecharDao(dao);
        }

        req.getRequestDispatcher("editar-despesa.jsp").forward(req, resp);
    }

    private void cadastrarDespesa(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();

        Cadastro cliente = (Cadastro) session.getAttribute("cliente");
        ContaBancaria conta = (ContaBancaria) session.getAttribute("conta");

        if (cliente == null || conta == null) {
            resp.sendRedirect("erro-conta-obrigatoria.jsp?origem=despesa");
            return;
        }

        DespesaDao dao = null;

        try {
            dao = new DespesaDao();
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

            Despesa despesa = new Despesa();
            despesa.setContaBancaria(conta);
            despesa.setValor(valor);
            despesa.setCategoria(categoria);
            despesa.setVencimento(vencimento);
            despesa.setDescricao(descricao);
            despesa.setStatus(status);

            dao.cadastrarDespesa(despesa);
            req.setAttribute("mensagem", "Despesa cadastrada com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao cadastrar despesa");
        } finally {
            fecharDao(dao);
        }
        req.getRequestDispatcher("cadastro-despesa.jsp").forward(req, resp);
    }

    private void excluirDespesa(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cadastro cliente = (Cadastro) session.getAttribute("cliente");

        DespesaDao dao = null;

        try {
            dao = new DespesaDao();
            int idDespesa = Integer.parseInt(req.getParameter("codigoExcluir"));
            Despesa despesa = dao.pesquisarDespesaPorId(idDespesa);

            if (despesa != null) {
                if("Pago".equalsIgnoreCase(despesa.getStatus())) {
                req.setAttribute("erro", "Despesas com status 'Pago' não podem ser excluídas.");
                } else {
                    dao.deletarDespesa(idDespesa);
                    req.setAttribute("mensagem", "Despesa excluída com sucesso!");
                }
            }

            List<Despesa> despesas = dao.pesquisarDespesasPorCliente(cliente.getIdCliente());
            req.setAttribute("despesas", despesas);
        } catch (EntidadeNaoEncontradaException e) {
            req.setAttribute("erro", "Despesa não localizada. Tente novamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao excluir despesa");
        } finally {
            fecharDao(dao);
        }
        req.getRequestDispatcher("lista-despesa.jsp").forward(req, resp);
    }

}

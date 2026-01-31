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

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");

        if ("editar".equals(acao)) {
            abrirFormEdicao(req, resp);
        } else if ("listar".equals(acao)) {
            listarReceitas(req, resp);
        }
    }

    private void abrirFormEdicao(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ReceitaDao dao = null;

        try {
            dao = new ReceitaDao();
            int idReceita = Integer.parseInt(req.getParameter("id"));
            Receita receita = dao.pesquisarReceitaPorId(idReceita);
            req.setAttribute("receita", receita);
            req.getRequestDispatcher("editar-receita.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao abrir formulário");
            req.getRequestDispatcher("home.jsp").forward(req, resp);
        } finally{
            fecharDao(dao);
        }
    }

    private void fecharDao(ReceitaDao dao) {
        try {
            if (dao != null) {
                dao.fecharConexao();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");

        if ("cadastrar".equals(acao)) {
            cadastrarReceita(req, resp);
        } else if ("atualizar".equals(acao)){
            atualizarReceita(req, resp);
        } else if ("excluir".equals(acao)){
            excluirReceita(req, resp);
        }
    }

    private void listarReceitas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ReceitaDao dao = null;

        try {
            dao = new ReceitaDao();
            HttpSession session = req.getSession();
            Cadastro cliente = (Cadastro) session.getAttribute("cliente");

            List<Receita> lista = dao.pesquisarReceitasPorCliente(cliente.getIdCliente());
            req.setAttribute("receitas", lista);

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao listar receitas");
        } finally{
            fecharDao(dao);
        }
        req.getRequestDispatcher("lista-receita.jsp").forward(req, resp);
    }

    private void atualizarReceita(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ReceitaDao dao = null;

        try {
            dao = new ReceitaDao();
            int id = Integer.parseInt(req.getParameter("id"));
            String categoria = req.getParameter("categoria");
            String descricao = req.getParameter("descricao");
            String status = req.getParameter("status");
            LocalDate recebimento = LocalDate.parse(req.getParameter("dataRecebimento"));

            double valor = Double.parseDouble(req.getParameter("valor"));

            if (valor <= 0) {
                req.setAttribute("erro", "O valor da receita deve ser maior que zero.");
                abrirFormEdicao(req, resp);
                return;
            }

            Receita receita = new Receita();
            receita.setId(id);
            receita.setCategoria(categoria);
            receita.setDescricao(descricao);
            receita.setStatus(status);
            receita.setDataRecebimento(recebimento);
            receita.setValor(valor);

            dao.atualizarReceita(receita);
            req.setAttribute("receita", receita);
            req.setAttribute("mensagem", "Receita atualizada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao atualizar receita.");
        } finally{
            fecharDao(dao);
        }

        req.getRequestDispatcher("editar-receita.jsp").forward(req, resp);
    }

    private void cadastrarReceita(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();

        Cadastro cliente = (Cadastro) session.getAttribute("cliente");
        ContaBancaria conta = (ContaBancaria) session.getAttribute("conta");

        if (cliente == null || conta == null) {
            resp.sendRedirect("erro-conta-obrigatoria.jsp?origem=receita");
            return;
        }

        ReceitaDao dao = null;

        try {
            dao = new ReceitaDao();
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

            Receita receita = new Receita();
            receita.setContaBancaria(conta);
            receita.setValor(valor);
            receita.setCategoria(categoria);
            receita.setDataRecebimento(recebimento);
            receita.setDescricao(descricao);
            receita.setStatus(status);

            dao.cadastrarReceita(receita);
            req.setAttribute("mensagem", "Receita cadastrada com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao cadastrar receita");
        } finally{
            fecharDao(dao);
        }
        req.getRequestDispatcher("cadastro-receita.jsp").forward(req, resp);

    }

    private void excluirReceita(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cadastro cliente = (Cadastro) session.getAttribute("cliente");

        ReceitaDao dao = null;

        try {
            dao = new ReceitaDao();
            int idReceita = Integer.parseInt(req.getParameter("codigoExcluir"));
            Receita receita = dao.pesquisarReceitaPorId(idReceita);

             if (receita != null) {
                 if ("Recebido".equalsIgnoreCase(receita.getStatus())){
                     req.setAttribute("erro", "Receitas com status 'Recebido' não podem ser excluídas.");
                 } else {
                     dao.deletarReceita(idReceita);
                     req.setAttribute("mensagem", "Receita excluída com sucesso!");
                 }
             }

            List<Receita> receitas = dao.pesquisarReceitasPorCliente(cliente.getIdCliente());
            req.setAttribute("receitas", receitas);
        } catch (EntidadeNaoEncontradaException e) {
            req.setAttribute("erro", "Receita não localizada. Tente novamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao excluir receita");
        } finally{
            fecharDao(dao);
        }

        req.getRequestDispatcher("lista-receita.jsp").forward(req, resp);
    }

}


package br.com.fiap.syncfin.controller;

import br.com.fiap.syncfin.dao.InvestimentoDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.Cadastro;
import br.com.fiap.syncfin.model.ContaBancaria;
import br.com.fiap.syncfin.model.Investimento;
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

@WebServlet("/investimento")
public class InvestimentoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");

        if("editar".equals(acao)){
            abrirFormEdicao(req, resp);
        } else if ("listar".equals(acao)){
            listarInvestimentos(req, resp);
        }
    }

    private void abrirFormEdicao(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        InvestimentoDao dao = null;

        try {
            dao = new InvestimentoDao();
            int idInvestimento = Integer.parseInt(req.getParameter("id"));
            Investimento investimento = dao.pesquisarInvestimentoPorId(idInvestimento);
            req.setAttribute("investimento", investimento);
            req.getRequestDispatcher("editar-investimento.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao abrir o formulário");
            req.getRequestDispatcher("home.jsp").forward(req, resp);
        } finally {
            fecharDao(dao);
        }
    }

    private void fecharDao(InvestimentoDao dao) {
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
            cadastrarInvestimento(req, resp);
        } else if ("atualizar".equals(acao)){
            atualizarInvestimento(req, resp);
        } else if ("excluir".equals(acao)){
           excluirInvestimento(req, resp);
        }
    }

    private void listarInvestimentos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

       InvestimentoDao dao = null;

        try {
            dao = new InvestimentoDao();
            HttpSession session = req.getSession();
            Cadastro cliente = (Cadastro) session.getAttribute("cliente");

            List<Investimento> lista = dao.pesquisarInvestimentosPorCliente(cliente.getIdCliente());
            req.setAttribute("investimentos", lista);

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao listar investimentos");
        } finally {
            fecharDao(dao);
        }

        req.getRequestDispatcher("lista-investimento.jsp").forward(req, resp);
    }

    private void atualizarInvestimento(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        InvestimentoDao dao = null;

        try{
            dao = new InvestimentoDao();
            int id = Integer.parseInt(req.getParameter("id"));
            double valor = Double.parseDouble(req.getParameter("valor"));
            String status = req.getParameter("status");
            String tipoInvestimento = req.getParameter("tipoInvestimento");
            LocalDate dataInvestimento = LocalDate.parse(req.getParameter("dataInvestimento"));
            LocalDate vencimento = LocalDate.parse(req.getParameter("dataVencimento"));
            double rendimento = Double.parseDouble(req.getParameter("rendimento"));
            String recorrencia = req.getParameter("recorrencia");

            if (valor <= 0) {
                req.setAttribute("erro", "Valor do investimento deve ser maior que zero.");
                req.getRequestDispatcher("cadastro-investimento.jsp").forward(req, resp);
                return;
            }

            Investimento investimento = new Investimento();
            investimento.setId(id);
            investimento.setValor(valor);
            investimento.setStatus(status);
            investimento.setTipoInvestimento(tipoInvestimento);
            investimento.setDataInvestimento(dataInvestimento);
            investimento.setDataVencimento(vencimento);
            investimento.setRendimento(rendimento);
            investimento.setRecorrencia(recorrencia);

            dao.atualizarInvestimento(investimento);
            req.setAttribute("mensagem", "Investimento atualizado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao atualizar investimento");
        } finally {
            fecharDao(dao);
        }
        req.getRequestDispatcher("editar-investimento.jsp").forward(req, resp);
    }

    private void cadastrarInvestimento(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();

        Cadastro cliente = (Cadastro) session.getAttribute("cliente");
        ContaBancaria conta = (ContaBancaria) session.getAttribute("conta");

        if (cliente == null || conta == null) {
            resp.sendRedirect("erro-conta-obrigatoria.jsp?origem=investimento");
            return;
        }

        InvestimentoDao dao = null;

        try{
            dao = new InvestimentoDao();
            double valor = Double.parseDouble(req.getParameter("valor"));
            String status = req.getParameter("status");
            String tipoInvestimento = req.getParameter("tipoInvestimento");
            LocalDate dataInvestimento = LocalDate.parse(req.getParameter("dataInvestimento"));
            LocalDate vencimento = LocalDate.parse(req.getParameter("dataVencimento"));
            double rendimento = Double.parseDouble(req.getParameter("rendimento"));
            String recorrencia = req.getParameter("recorrencia");

            if (valor <= 0) {
                req.setAttribute("erro", "Valor do investimento deve ser maior que zero.");
                req.getRequestDispatcher("cadastro-investimento.jsp").forward(req, resp);
                return;
            }

            Investimento investimento = new Investimento();
            investimento.setContaBancaria(conta);
            investimento.setValor(valor);
            investimento.setStatus(status);
            investimento.setTipoInvestimento(tipoInvestimento);
            investimento.setDataInvestimento(dataInvestimento);
            investimento.setDataVencimento(vencimento);
            investimento.setRendimento(rendimento);
            investimento.setRecorrencia(recorrencia);

            dao.cadastrarInvestimento(investimento);
            req.setAttribute("mensagem", "Investimento cadastrado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao cadastrar investimento");
        } finally {
            fecharDao(dao);
        }
        req.getRequestDispatcher("cadastro-investimento.jsp").forward(req, resp);
    }

    private void excluirInvestimento(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cadastro cliente = (Cadastro) session.getAttribute("cliente");

        InvestimentoDao dao = null;

        try {
            dao = new InvestimentoDao();
            int idInvestimento = Integer.parseInt(req.getParameter("codigoExcluir"));
            Investimento investimento = dao.pesquisarInvestimentoPorId(idInvestimento);

            if (investimento != null) {
                if ("Ativo".equalsIgnoreCase(investimento.getStatus())){
                    req.setAttribute("erro", "Investimentos com status Ativo não podem ser excluídos.");
                } else {
                    dao.deletarInvestimento(idInvestimento);
                    req.setAttribute("mensagem", "Investimento excluído com sucesso!");
                }
            }

            List<Investimento> investimentos = dao.pesquisarInvestimentosPorCliente(cliente.getIdCliente());
            req.setAttribute("investimentos", investimentos);
        } catch (EntidadeNaoEncontradaException e) {
            req.setAttribute("erro", "Investimento não localizado. Tente novamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao excluir investimento");
        } finally {
            fecharDao(dao);
        }
        req.getRequestDispatcher("lista-investimento.jsp").forward(req, resp);
    }
}

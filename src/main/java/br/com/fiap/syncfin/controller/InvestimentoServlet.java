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
            listarInvestimentos(req, resp);
        }
    }

    private void abrirFormEdicao(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;

        int idInvestimento;

        try {
            idInvestimento = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            HttpSession session = req.getSession(false);
            if (session != null) session.setAttribute("erro", "ID de investimento inválido.");
            resp.sendRedirect("investimento?acao=listar");
            return;
        }

        try (InvestimentoDao investimentoDao = new InvestimentoDao()) {
            Investimento investimento = investimentoDao.pesquisarInvestimentoPorIdDoCliente(cliente.getIdCliente(), idInvestimento);
            req.setAttribute("investimento", investimento);
            req.getRequestDispatcher("editar-investimento.jsp").forward(req, resp);

        } catch (EntidadeNaoEncontradaException e) {
            HttpSession session = req.getSession(false);
            if (session != null) session.setAttribute("erro", e.getMessage());
            resp.sendRedirect("investimento?acao=listar");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao abrir o formulário");
            req.getRequestDispatcher("home.jsp").forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");

        if ("cadastrar".equals(acao)) {
            cadastrarInvestimento(req, resp);
        } else if ("atualizar".equals(acao)) {
            atualizarInvestimento(req, resp);
        } else if ("excluir".equals(acao)) {
            excluirInvestimento(req, resp);
        }
    }

    private void listarInvestimentos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;

        try (InvestimentoDao investimentoDao = new InvestimentoDao()) {
            List<Investimento> lista = investimentoDao.pesquisarInvestimentosPorCliente(cliente.getIdCliente());
            req.setAttribute("investimentos", lista);

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao listar investimentos");
        }

        req.getRequestDispatcher("lista-investimento.jsp").forward(req, resp);
    }

    private void atualizarInvestimento(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;

        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            HttpSession session = req.getSession(false);
            if (session != null) session.setAttribute("erro", "ID de investimento inválido.");
            resp.sendRedirect("investimento?acao=listar");
            return;
        }

        try (InvestimentoDao investimentoDao = new InvestimentoDao()) {
            double valor = Double.parseDouble(req.getParameter("valor"));
            String status = req.getParameter("status");
            String tipoInvestimento = req.getParameter("tipoInvestimento");
            LocalDate dataInvestimento = LocalDate.parse(req.getParameter("dataInvestimento"));
            String vencStr = req.getParameter("dataVencimento");
            LocalDate vencimento = (vencStr == null || vencStr.isBlank()) ? null : LocalDate.parse(vencStr);
            double rendimento = Double.parseDouble(req.getParameter("rendimento"));
            String recorrencia = req.getParameter("recorrencia");

            if (valor <= 0) {
                req.setAttribute("erro", "Valor do investimento deve ser maior que zero.");
                Investimento atual = investimentoDao.pesquisarInvestimentoPorIdDoCliente(cliente.getIdCliente(), id);
                req.setAttribute("investimento", atual);
                req.getRequestDispatcher("editar-investimento.jsp").forward(req, resp);
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

            investimentoDao.atualizarInvestimentoDoCliente(investimento, cliente.getIdCliente());

            req.setAttribute("investimento", investimento);
            req.setAttribute("mensagem", "Investimento atualizado com sucesso!");

        } catch (EntidadeNaoEncontradaException e) {
            req.setAttribute("erro", e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao atualizar investimento");
        }

        req.getRequestDispatcher("editar-investimento.jsp").forward(req, resp);
    }

    private void cadastrarInvestimento(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;
        HttpSession session = req.getSession(false);
        ContaBancaria conta = (ContaBancaria) session.getAttribute("conta");

        if (conta == null) {
            resp.sendRedirect("erro-conta-obrigatoria.jsp?origem=investimento");
            return;
        }

        try (InvestimentoDao investimentoDao = new InvestimentoDao()) {
            double valor = Double.parseDouble(req.getParameter("valor"));
            String status = req.getParameter("status");
            String tipoInvestimento = req.getParameter("tipoInvestimento");
            LocalDate dataInvestimento = LocalDate.parse(req.getParameter("dataInvestimento"));
            String vencStr = req.getParameter("dataVencimento");
            LocalDate vencimento = (vencStr == null || vencStr.isBlank()) ? null : LocalDate.parse(vencStr);
            double rendimento = Double.parseDouble(req.getParameter("rendimento"));
            String recorrencia = req.getParameter("recorrencia");

            if (valor <= 0) {
                req.setAttribute("erro", "Valor do investimento deve ser maior que zero.");
                req.getRequestDispatcher("cadastro-investimento.jsp").forward(req, resp);
                return;
            }

            conta.setCliente(cliente);

            Investimento investimento = new Investimento();
            investimento.setContaBancaria(conta);
            investimento.setValor(valor);
            investimento.setStatus(status);
            investimento.setTipoInvestimento(tipoInvestimento);
            investimento.setDataInvestimento(dataInvestimento);
            investimento.setDataVencimento(vencimento);
            investimento.setRendimento(rendimento);
            investimento.setRecorrencia(recorrencia);

            investimentoDao.cadastrarInvestimento(investimento);
            req.setAttribute("mensagem", "Investimento cadastrado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao cadastrar investimento");
        }
        req.getRequestDispatcher("cadastro-investimento.jsp").forward(req, resp);
    }

    private void excluirInvestimento(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cadastro cliente = getClienteLogado(req, resp);
        if (cliente == null) return;

        int idInvestimento;
        try {
            idInvestimento = Integer.parseInt(req.getParameter("codigoExcluir"));
        } catch (NumberFormatException e) {
            req.setAttribute("erro", "ID de investimento inválido.");
            listarInvestimentos(req, resp);
            return;
        }

        try (InvestimentoDao investimentoDao = new InvestimentoDao()) {
            Investimento investimento = investimentoDao.pesquisarInvestimentoPorIdDoCliente(cliente.getIdCliente(), idInvestimento);

            if ("Ativo".equalsIgnoreCase(investimento.getStatus())) {
                req.setAttribute("erro", "Investimentos com status Ativo não podem ser excluídos.");
            } else {
                investimentoDao.deletarInvestimentoDoCliente(idInvestimento, cliente.getIdCliente());
                req.setAttribute("mensagem", "Investimento excluído com sucesso!");
            }

            List<Investimento> investimentos = investimentoDao.pesquisarInvestimentosPorCliente(cliente.getIdCliente());
            req.setAttribute("investimentos", investimentos);

        } catch (EntidadeNaoEncontradaException e) {
            req.setAttribute("erro", "Investimento não localizado. Tente novamente.");
            listarInvestimentos(req, resp);
            return;

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao excluir investimento");
            listarInvestimentos(req, resp);
            return;
        }
        req.getRequestDispatcher("lista-investimento.jsp").forward(req, resp);
    }
}

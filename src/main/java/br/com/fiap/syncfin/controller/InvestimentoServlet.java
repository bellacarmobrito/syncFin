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

import br.com.fiap.syncfin.util.ValidationUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import static br.com.fiap.syncfin.util.SessionUtils.getClienteLogado;

@WebServlet("/investimento")
public class InvestimentoServlet extends HttpServlet {

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

        Investimento investimento = new Investimento();
        investimento.setId(id);
        investimento.setStatus(req.getParameter("status"));
        investimento.setTipoInvestimento(req.getParameter("tipoInvestimento"));
        investimento.setRecorrencia(req.getParameter("recorrencia"));

        if (investimento.getStatus() == null || !Investimento.STATUS_VALIDOS.contains(investimento.getStatus())
                || ValidationUtils.algumEmBranco(investimento.getTipoInvestimento(), investimento.getRecorrencia())) {
            req.setAttribute("erro", "Tipo de investimento e recorrência são obrigatórios; status deve ser Ativo ou Resgatado.");
            req.setAttribute("investimento", investimento);
            req.getRequestDispatcher("editar-investimento.jsp").forward(req, resp);
            return;
        }

        try {
            investimento.setValor(Double.parseDouble(req.getParameter("valor")));
            investimento.setRendimento(Double.parseDouble(req.getParameter("rendimento")));
        } catch (NumberFormatException e) {
            req.setAttribute("erro", "Valor ou rendimento inválido.");
            req.setAttribute("investimento", investimento);
            req.getRequestDispatcher("editar-investimento.jsp").forward(req, resp);
            return;
        }

        try {
            investimento.setDataInvestimento(LocalDate.parse(req.getParameter("dataInvestimento")));
            String vencStr = req.getParameter("dataVencimento");
            investimento.setDataVencimento((vencStr == null || vencStr.isBlank()) ? null : LocalDate.parse(vencStr));
        } catch (DateTimeParseException e) {
            req.setAttribute("erro", "Data de investimento ou vencimento inválida.");
            req.setAttribute("investimento", investimento);
            req.getRequestDispatcher("editar-investimento.jsp").forward(req, resp);
            return;
        }

        if (investimento.getValor() <= 0) {
            req.setAttribute("erro", "Valor do investimento deve ser maior que zero.");
            req.setAttribute("investimento", investimento);
            req.getRequestDispatcher("editar-investimento.jsp").forward(req, resp);
            return;
        }

        try (InvestimentoDao investimentoDao = new InvestimentoDao()) {
            investimentoDao.atualizarInvestimentoDoCliente(investimento, cliente.getIdCliente());
            req.setAttribute("mensagem", "Investimento atualizado com sucesso!");

        } catch (EntidadeNaoEncontradaException e) {
            req.setAttribute("erro", e.getMessage());

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao atualizar investimento. Tente novamente.");
        }

        req.setAttribute("investimento", investimento);
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

        String status = req.getParameter("status");
        String tipoInvestimento = req.getParameter("tipoInvestimento");
        String recorrencia = req.getParameter("recorrencia");

        if (status == null || !Investimento.STATUS_VALIDOS.contains(status)
                || ValidationUtils.algumEmBranco(tipoInvestimento, recorrencia)) {
            req.setAttribute("erro", "Tipo de investimento e recorrência são obrigatórios; status deve ser Ativo ou Resgatado.");
            req.getRequestDispatcher("cadastro-investimento.jsp").forward(req, resp);
            return;
        }

        double valor;
        double rendimento;
        try {
            valor = Double.parseDouble(req.getParameter("valor"));
            rendimento = Double.parseDouble(req.getParameter("rendimento"));
        } catch (NumberFormatException e) {
            req.setAttribute("erro", "Valor ou rendimento inválido.");
            req.getRequestDispatcher("cadastro-investimento.jsp").forward(req, resp);
            return;
        }

        LocalDate dataInvestimento;
        LocalDate vencimento;
        try {
            dataInvestimento = LocalDate.parse(req.getParameter("dataInvestimento"));
            String vencStr = req.getParameter("dataVencimento");
            vencimento = (vencStr == null || vencStr.isBlank()) ? null : LocalDate.parse(vencStr);
        } catch (DateTimeParseException e) {
            req.setAttribute("erro", "Data de investimento ou vencimento inválida.");
            req.getRequestDispatcher("cadastro-investimento.jsp").forward(req, resp);
            return;
        }

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

        try (InvestimentoDao investimentoDao = new InvestimentoDao()) {
            investimento.setId(investimentoDao.cadastrarInvestimento(investimento));
            req.setAttribute("mensagem", "Investimento cadastrado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao cadastrar investimento. Tente novamente.");
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

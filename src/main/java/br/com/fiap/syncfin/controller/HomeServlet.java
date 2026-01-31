package br.com.fiap.syncfin.controller;

import br.com.fiap.syncfin.dao.ContaBancariaDao;
import br.com.fiap.syncfin.dao.DespesaDao;
import br.com.fiap.syncfin.dao.InvestimentoDao;
import br.com.fiap.syncfin.dao.ReceitaDao;
import br.com.fiap.syncfin.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cadastro cliente = (Cadastro) session.getAttribute("cliente");

        if (cliente == null) {
            resp.sendRedirect("index.jsp");
            return;
        }

        int idCliente = cliente.getIdCliente();

        ContaBancariaDao contaDao = null;
        ReceitaDao receitaDao = null;
        DespesaDao despesaDao = null;
        InvestimentoDao investimentoDao = null;


        try {
            contaDao = new ContaBancariaDao();
            receitaDao = new ReceitaDao();
            despesaDao = new DespesaDao();
            investimentoDao = new InvestimentoDao();

            double saldoTotal = contaDao.calcularSaldoPorCliente(idCliente);
            double totalReceitas = receitaDao.calcularReceitasPorCliente(idCliente);
            double totalDespesas = despesaDao.calcularDespesasPorCliente(idCliente);
            double totalInvestimentos = investimentoDao.calcularInvestimentosPorCliente(idCliente);

            List<Receita> receitas = receitaDao.pesquisarReceitasPorCliente(idCliente);
            List<Despesa> despesas = despesaDao.pesquisarDespesasPorCliente(idCliente);
            List<Investimento> investimentos = investimentoDao.pesquisarInvestimentosPorCliente(idCliente);
            List<ContaBancaria> contas = contaDao.pesquisarContasPorCliente(idCliente);

            req.setAttribute("receitas", receitas);
            req.setAttribute("despesas", despesas);
            req.setAttribute("investimentos", investimentos);

            req.setAttribute("saldoTotal", saldoTotal);
            req.setAttribute("contas", contas);
            req.setAttribute("totalReceitas", totalReceitas);
            req.setAttribute("totalDespesas", totalDespesas);
            req.setAttribute("totalInvestimentos", totalInvestimentos);


        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao carregar informações.");
        } finally {
            fecharDao(contaDao, receitaDao, despesaDao, investimentoDao);
        }

        req.getRequestDispatcher("home.jsp").forward(req, resp);

    }

    private static void fecharDao(ContaBancariaDao contaDao, ReceitaDao receitaDao, DespesaDao despesaDao, InvestimentoDao investimentoDao) {
        try {
            if (contaDao != null) contaDao.fecharConexao();
            if (receitaDao != null) receitaDao.fecharConexao();
            if (despesaDao != null) despesaDao.fecharConexao();
            if (investimentoDao != null) investimentoDao.fecharConexao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package br.com.fiap.syncfin.controller;

import br.com.fiap.syncfin.dao.CadastroDao;
import br.com.fiap.syncfin.dao.ContaBancariaDao;
import br.com.fiap.syncfin.exception.EntidadeNaoEncontradaException;
import br.com.fiap.syncfin.model.ContaBancaria;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        CadastroDao dao = null;
        ContaBancariaDao contaDao = null;

        try {
            dao = new CadastroDao();

            var cliente = dao.autenticarUsuario(email, senha);

            if (cliente != null) {

                HttpSession session = request.getSession();

                session.setAttribute("cliente", cliente);

               contaDao = new ContaBancariaDao();
                try {
                    ContaBancaria conta = contaDao.pesquisarContaPorIdCliente(cliente.getIdCliente());
                    session.setAttribute("conta", conta);
                    session.removeAttribute("mensagemConta");
                } catch (EntidadeNaoEncontradaException e) {
                    session.setAttribute("conta", null);
                    session.setAttribute("mensagemConta", "Você ainda não possui conta bancária cadastrada. Clique abaixo para criar sua conta e acessar todos os recursos.");
                }
                response.sendRedirect("home");
                return;

            }

            request.setAttribute("erro", "Usuário e/ou senha inválidos");
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            try {
                if (dao != null) dao.fecharConexao();
                if (contaDao != null) contaDao.fecharConexao();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

}

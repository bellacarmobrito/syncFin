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

        email = (email == null) ? null : email.trim();
        senha = (senha == null) ? null : senha.trim();

        if (email == null || email.isBlank() || senha == null || senha.isBlank()) {
            request.setAttribute("erro", "Por favor informe e-mail e senha.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        try (CadastroDao dao = new CadastroDao();
             ContaBancariaDao contaDao = new ContaBancariaDao()) {

            var cliente = dao.autenticarUsuario(email, senha);

            if (cliente == null) {
                request.setAttribute("erro", "Usuário e/ou senha inválidos");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }

            HttpSession session = request.getSession(true);
            session.setAttribute("cliente", cliente);

            try {
                ContaBancaria conta = contaDao.pesquisarContaPorIdCliente(cliente.getIdCliente());
                session.setAttribute("conta", conta);
                session.removeAttribute("mensagemConta");
            } catch (EntidadeNaoEncontradaException e) {
                session.setAttribute("conta", null);
                session.setAttribute("mensagemConta", "Você ainda não possui conta bancária cadastrada. Clique abaixo para criar sua conta e acessar todos os recursos.");
            }
            response.sendRedirect("home");

        } catch (SQLException e) {
            e.printStackTrace();

            request.setAttribute("erro","Erro ao tentar realizar o login. Tente novamente.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

}

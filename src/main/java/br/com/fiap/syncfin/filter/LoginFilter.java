package br.com.fiap.syncfin.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession(false);

        String url = req.getRequestURI();
        String acao = req.getParameter("acao");

        boolean usuarioLogado = (session != null && session.getAttribute("cliente") != null);

        boolean urlLiberada =
                url.endsWith("login") ||
                        url.contains("index.jsp") ||
                        url.contains("cadastro-cliente.jsp") ||
                        (url.contains("cadastro") && "cadastrar".equals(acao)) ||
                        url.contains("resources");

        if (!usuarioLogado && !urlLiberada) {
            req.setAttribute("erro", "Entre com o usuário e senha!");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
            return;
        }

        filterChain.doFilter(req, resp);

    }
}

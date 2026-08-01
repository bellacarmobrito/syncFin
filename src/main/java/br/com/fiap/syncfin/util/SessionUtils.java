package br.com.fiap.syncfin.util;

import br.com.fiap.syncfin.model.Cadastro;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class SessionUtils {

    public static Cadastro getClienteLogado(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
}

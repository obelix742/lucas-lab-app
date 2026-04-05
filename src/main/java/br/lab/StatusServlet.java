package br.lab;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.time.LocalDateTime;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;

@WebServlet("/status")
public class StatusServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String host = InetAddress.getLocalHost().getHostName();

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<h1>Lab Caixa</h1>");
        out.println("<p>Servidor: " + host + "</p>");
        out.println("<p>Data/Hora: " + LocalDateTime.now() + "</p>");
    }
}

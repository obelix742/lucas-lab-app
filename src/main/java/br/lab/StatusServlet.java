package br.lab;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;

import javax.naming.InitialContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

@WebServlet("/status")
public class StatusServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String host = "erro";
        String dbStatus = "ERRO";
        String dbTime = "-";
        String error = "";

        try {
            host = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            host = "erro-host";
        }

        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:/jdbc/AppDS");

            Connection conn = ds.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select now()");

            if (rs.next()) {
                dbTime = rs.getString(1);
            }

            dbStatus = "OK";

            rs.close();
            st.close();
            conn.close();

        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<h1>Lab Caixa</h1>");
        out.println("<p><b>Servidor:</b> " + host + "</p>");
        out.println("<p><b>Hora App:</b> " + LocalDateTime.now() + "</p>");

        if ("OK".equals(dbStatus)) {
            out.println("<p><b>Banco:</b> <span style='color:green'>CONECTADO</span></p>");
            out.println("<p><b>Hora DB:</b> " + dbTime + "</p>");
        } else {
            out.println("<p><b>Banco:</b> <span style='color:red'>ERRO</span></p>");
            out.println("<p>" + error + "</p>");
        }
    }
}

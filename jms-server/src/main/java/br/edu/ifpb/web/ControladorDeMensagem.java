package br.edu.ifpb.web;

import br.edu.ifpb.infra.jms.mensagem.ProdutorDeMensagem;
import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ricardo Job
 */
@WebServlet(name = "ControladorDeMensagem", urlPatterns = {"/mensagem"})
public class ControladorDeMensagem extends HttpServlet {

    @Inject
    private ProdutorDeMensagem service;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mensagem = request.getParameter("mensagem");
        service.send(mensagem);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Listando as mensagens</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>" + service.lerMensagem() + "</h3>");
            out.println("</body>");
            out.println("</html>");
        }
    }

}

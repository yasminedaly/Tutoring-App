package web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

import CRUD.Session_Crud;
import java.sql.DriverManager;

@WebServlet(name = "DeleteSessionServlet", urlPatterns = {"/DeleteSession"})
public class Delete_Session extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        deletesession(request, response);
    }
    private void deletesession(HttpServletRequest request, HttpServletResponse response) {

        String sessionIdStr = request.getParameter("sessionid");
        System.out.println("Session ID: " + sessionIdStr);
        try {
                Session_Crud.deleteSessionFromDatabase(Integer.parseInt(sessionIdStr));
                response.sendRedirect("sessions.jsp?email=" + request.getParameter("email") + "&message=Session deleted successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

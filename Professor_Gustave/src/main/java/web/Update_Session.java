package web;

import CRUD.Session_Crud;
import classes.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet(name = "UpdateSessionServlet", urlPatterns = {"/UpdateSession"})
public class Update_Session extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateSession(request, response);
    }

    private void updateSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        int sessionId = Integer.parseInt(request.getParameter("id"));
        String subject = request.getParameter("subject");
        double rate = Double.parseDouble(request.getParameter("rate"));
        String date = request.getParameter("sessionDate");
        int NBplaces = Integer.parseInt(request.getParameter("NBplaces"));
        try {
            Session_Crud.UpdateSession(sessionId,rate,date,NBplaces,subject);
            System.out.println("Session ID: " + sessionId);
            response.sendRedirect("sessions.jsp?email=" + email);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Registration failed: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

    }



}

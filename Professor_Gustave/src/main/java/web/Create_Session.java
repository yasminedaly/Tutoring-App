package web;

import CRUD.Session_Crud;
import CRUD.User_Crud;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet(name = "CreateSessionServlet", urlPatterns = {"/CreateSession"})
public class Create_Session  extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        createSession(request, response);
    }
    private void createSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extract form parameters
        String subject = request.getParameter("subject");
        double rate = Double.parseDouble(request.getParameter("rate"));
        String sessionDate = request.getParameter("sessionDate");
        int nbPlaces = Integer.parseInt(request.getParameter("nbPlaces"));
        String email = request.getParameter("email");

        try {
            if (email != null && !email.isEmpty()) {
                int tutorId = User_Crud.getUserId(email);
                Session_Crud.addSessionToDatabase(subject, rate, sessionDate, nbPlaces, tutorId);
                response.sendRedirect("sessions.jsp?email=" + URLEncoder.encode(email, "UTF-8"));
            } else {
                throw new IllegalArgumentException("Email not provided");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Registration failed: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}

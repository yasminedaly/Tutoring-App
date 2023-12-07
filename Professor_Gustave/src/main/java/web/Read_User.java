package web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import CRUD.User_Crud;
import classes.User;

@WebServlet( name="ReadUserServlet", urlPatterns = {"/ReadUser"})
public class Read_User extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        readUser(request, response);
    }

    private void readUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = User_Crud.readUserFromDatabase(email, password);
            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("profile.jsp").forward(request, response);
                //response.sendRedirect("profile.html");
            } else {
                response.sendRedirect("sign-in.html");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception to the console
           request.setAttribute("errorMessage", "Registration failed: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

}

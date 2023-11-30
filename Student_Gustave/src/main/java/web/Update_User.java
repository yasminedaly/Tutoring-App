package web;

import CRUD.User_Crud;
import classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet( name="UpdateUserServlet", urlPatterns = {"/UpdateUser"})
public class Update_User extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateUser(request, response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        int age = Integer.parseInt(request.getParameter("age"));
        String phoneNumber = request.getParameter("phoneNumber");
        String bio = request.getParameter("bio");
        String email = request.getParameter("email");
        try {
            User user = User_Crud.updateUserInDatabase(firstName, lastName, age, email, phoneNumber, bio);
            request.setAttribute("user", user);
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            // Removed the response.sendRedirect() line
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception to the console
            request.setAttribute("errorMessage", "Update failed: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

}

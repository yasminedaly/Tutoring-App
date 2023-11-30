package web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import CRUD.User_Crud;

@WebServlet(name = "CreateUserServlet", urlPatterns = {"/CreateUser"})
public class Create_User extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        createUser(request, response);
    }

    private void createUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extract form parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        int age = Integer.parseInt(request.getParameter("age"));
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("password");
        String bio = request.getParameter("bio");
        String role = request.getParameter("role");

        try {
            User_Crud.addUserToDatabase(firstName, lastName, age, email, phoneNumber, password, bio, role);
            response.sendRedirect("sign-in.html"); // Redirect to a success page
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception to the console
            request.setAttribute("errorMessage", "Registration failed: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response); // Forward to an error page
        }
    }
}

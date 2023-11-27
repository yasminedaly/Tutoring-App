package web;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import CRUD.*;

@WebServlet(name = "UserServlet", urlPatterns = {"/Professor_Server/UserServlet"})
public class UserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "create":
                createUser(request, response);
                break;
            case "update":
                updateUser(request, response);
                break;
            // Other cases for read and delete
        }
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

        // You should add validation here

        // If validation passes, add the user to the database
        try {
            User_Crud.addUserToDatabase(firstName, lastName, age, email, phoneNumber, password, bio, role);
            // Redirect or forward to a success page
            response.sendRedirect("sign-in.html"); // Change "success.html" to the path of your success page
        } catch (Exception e) {
            // Handle exceptions, perhaps forward to an error page
            request.setAttribute("errorMessage", "Registration failed");
            request.getRequestDispatcher("error.jsp").forward(request, response); // Change "error.jsp" to the path of your error page
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) {
        // Implementation for updating a user
    }

    // Other methods for read and delete
}

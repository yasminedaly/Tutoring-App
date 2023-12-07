package web;

import classes.Session;
import interfaces.TutoringServices;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/ReadSessions")
public class ReadSessionsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get the registry from the server
            Registry registry = LocateRegistry.getRegistry("localhost", 1100);

            // Lookup the remote service
            TutoringServices service = (TutoringServices) registry.lookup("TutoringService");

            // Call the getAllSessions method and store the result
            List<Session> allSessions = service.getAllSessions();


            request.setAttribute("sessions", allSessions); // Make sure allSessions is not null or empty
            RequestDispatcher dispatcher = request.getRequestDispatcher("sessions.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Failed to retrieve sessions", e);
        }
    }
}

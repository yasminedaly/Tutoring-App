package web;

import classes.Session;
import interfaces.TutoringServices;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/SearchSessions")
public class SearchSessionServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SearchSessionServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String subject = request.getParameter("subject"); // Get the search query from the request

            // Get the registry from the server
            Registry registry = LocateRegistry.getRegistry("localhost", 1100);

            // Lookup the remote service
            TutoringServices service = (TutoringServices) registry.lookup("TutoringService");

            // Call the searchSessions method and store the result
            List<Session> foundSessions = service.searchSessions(subject);

            // Log the found sessions
            logger.log(Level.INFO, "Found sessions: " + foundSessions);

            request.setAttribute("sessions", foundSessions); // Set the found sessions as a request attribute
            RequestDispatcher dispatcher = request.getRequestDispatcher("sessions.jsp");
            dispatcher.forward(request, response); // Forward to the JSP page with results

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to search for sessions", e);
            throw new ServletException("Failed to search for sessions", e);
        }
    }
}

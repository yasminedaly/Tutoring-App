package web;

import interfaces.TutoringServices;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/BookSessions")
public class BookSessionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subject = request.getParameter("subject");
        float rate = Float.parseFloat(request.getParameter("rate"));
        String sessionDate = request.getParameter("date");
        int nbPlaces = Integer.parseInt(request.getParameter("nbPlaces"));
        String email = request.getParameter("email");
    // Change according to your application's logic

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1100);
            TutoringServices service = (TutoringServices) registry.lookup("TutoringService");

            // Retrieve session and student IDs
            int sessionId = service.getSessionId(subject, rate, sessionDate, nbPlaces);
            int studentId = service.getUserId(email);

            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.INFO, "Session ID: " + sessionId + " Student ID: " + studentId);
            logger.log(Level.INFO, "Subject: " + subject + " rate: " + rate + " Date: " + sessionDate+ " nbPlaces: " + nbPlaces);

            if (sessionId > 0 && studentId > 0) {
                // Attempt to book the session
                boolean success = service.bookSession(sessionId, studentId);

                if (success) {
                    // Redirect to a success page
                    response.sendRedirect("/ReadSessions?email="+email);
                } else {
                    // Handle booking failure, e.g., redirect to a waitlist page
                    response.sendRedirect("/ReadSessions?email="+email);
                }
            } else {
                // Handle invalid session or student ID
                response.sendRedirect("/ReadSessions?email="+email);
            }

        } catch (Exception e) {
            e.printStackTrace(); // This line will print the stack trace to the console.

            // Alternatively, using Java's built-in logging
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Error booking session", e);

            throw new ServletException("Booking session failed", e);
        }
    }
}
package web;

import classes.EmailUtil;
import interfaces.BankService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


@WebServlet(urlPatterns = {"/Payment"})
public class PaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long cardNumber = Long.parseLong(request.getParameter("cardNumber"));
        System.out.printf("cardNumber"+cardNumber);

        String carHolder = request.getParameter("cardHolder");
        int cvv = Integer.parseInt(request.getParameter("cvv"));
        String cardType = request.getParameter("cardType");
        String subject = request.getParameter("subject");
        float rate = Float.parseFloat(request.getParameter("rate"));
        String sessionDate = request.getParameter("date");
        int nbPlaces = Integer.parseInt(request.getParameter("nbPlaces"));
        String email = request.getParameter("email");

        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        BankService service = null;
        try {
            service = (BankService) registry.lookup("BankService");
            Boolean approval = service.processPayment(cardNumber, carHolder, cvv, cardType, rate);
            if (approval) {
                EmailUtil.sendEmail(email, "Payment approved", "Hello, your transaction was successful for the session for the subject " + subject + "on Eiffel Tutoring Solution");
                response.sendRedirect("BookSessions?rate=" + rate
                        + "&date=" + sessionDate
                        + "&subject=" + subject
                        + "&email=" + email
                        + "&nbPlaces=" + nbPlaces);

            } else {
                EmailUtil.sendEmail(email, "Payment not approved", "Hello, your transaction failed for the session for the subject " + subject + "on Eiffel Tutoring Solution");
                response.sendRedirect( "ReadSessions?email=" + email);
            }

        } catch (NotBoundException e) {
            request.setAttribute("errorMessage", "Registration failed: " + e.getMessage());
        }


    }

    }

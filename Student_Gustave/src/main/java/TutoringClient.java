import classes.Session;
import interfaces.TutoringServices;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class TutoringClient {
    public static void main(String[] args) {
        try {
            // Get the registry from the server
            Registry registry = LocateRegistry.getRegistry("localhost", 1100); // Replace "localhost" with the server's IP address if needed

            // Lookup the remote service
            TutoringServices service = (TutoringServices) registry.lookup("TutoringService");

            // Call the getAllSessions method and store the result
            List<Session> allSessions = service.getAllSessions();

            // Use the list of sessions as needed, here we're just printing them
            for (Session session : allSessions) {
                System.out.println("Session ID: " + session.getSessionId());
                // Print other details of the session as needed
            }

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

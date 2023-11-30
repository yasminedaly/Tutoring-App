import classes.Session;
import classes.Subject;
import interfaces.TutoringServices;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class TutoringClient {
    public static void main(String[] args) {
        try {
            // Get the registry from the server
            Registry registry = LocateRegistry.getRegistry("localhost", 1099); // Replace "localhost" with the server's IP address if needed

            // Lookup the remote service
            TutoringServices service = (TutoringServices) registry.lookup("TutoringService");

            // Invoke the remote method
            List<Session> sessions = service.searchSessions(Subject.BIOLOGY);
            for (Session session : sessions) {
                System.out.println(session.getSessionId());
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

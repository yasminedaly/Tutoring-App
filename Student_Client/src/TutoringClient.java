import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class TutoringClient {
    public static void main(String[] args) {
        try {
            // Get the registry
            TutoringServicesImpl tutoringService = new TutoringServicesImpl();
            Registry registry = LocateRegistry.getRegistry(1099); // 'host' is the server's IP address
            // Lookup the remote service
            TutoringServices service = (TutoringServices) registry.lookup("TutoringService");
            List<Session> l =service.searchSessions(Subject.MATHEMATICS.toString());
            //System.out.println(Subject.BIOLOGY.name());
            //System.out.println(Subject.BIOLOGY.name().toString());

            int i=0;
            for (Session s : l) {
                i++;

            }
            System.out.println(i);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

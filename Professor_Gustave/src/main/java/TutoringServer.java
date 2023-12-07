import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import CRUD.Session_Crud;
import classes.*;
import interfaces.*;
import services.*;

public class TutoringServer {
    public static void main(String[] args) {
        try {
            // Instantiate your service implementation
            TutoringServicesImpl tutoringService = new TutoringServicesImpl();
            // Create the registry on a specific port
            Registry registry = LocateRegistry.createRegistry(1100); // Default RMI port

            // Bind the remote object's stub in the registry
            registry.rebind("TutoringService", tutoringService);
            System.out.println("Server is ready and services are registered.");

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

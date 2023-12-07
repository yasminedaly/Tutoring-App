import services.BankServiceImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BankServer {
    public static void main(String[] args) {
        try {
            // Instantiate your service implementation
            BankServiceImpl bankService = new BankServiceImpl();
            // Create the registry on a specific port
            Registry registry = LocateRegistry.createRegistry(1099); // Default RMI port

            // Bind the remote object's stub in the registry
            registry.rebind("BankService", bankService);
            System.out.println("Server is ready and services are registered.");

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

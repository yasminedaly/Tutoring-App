import interfaces.BankService;

import javax.rmi.ssl.SslRMIServerSocketFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIServerSocketFactory;

public class BankClient {

    public static void main(String[] args) {
        try {

            // Get the registry from the server
            Registry registry = LocateRegistry.getRegistry("localhost", 1099); // Replace "localhost" with the server's IP address if needed

            // Lookup the remote service
            BankService service = (BankService) registry.lookup("BankService");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

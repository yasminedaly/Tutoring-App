import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class TutoringClient {
    public static void main(String[] args) {
            // Get the registry
            try {
                TutoringServicesImpl tutoringService = new TutoringServicesImpl();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            Registry registry = null; // 'host' is the server's IP address
            try {
                registry = LocateRegistry.getRegistry(1099);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            // Lookup the remote service
            TutoringServices service = null;
            try {
                service = (TutoringServices) registry.lookup("TutoringService");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            }
            try {
                List<Session> l =service.searchSessions(Subject.MATHEMATICS.toString());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            //System.out.println(Subject.BIOLOGY.name());
            //System.out.println(Subject.BIOLOGY.name().toString());

            //int i=0;
            //for (Session s : l) {
              //  i++;
            //}
            //System.out.println(i);
        //} catch (Exception e) {
          //  System.err.println("Client exception: " + e.toString());
            //e.printStackTrace();
        //}
            // book a session
        boolean booked = false;
        try {
             booked = service.bookSession(1, 1);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        System.err.println(booked ? "Session booked" : "Session not booked");


    }}

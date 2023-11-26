import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class TutoringServer {
    public static void main(String[] args) {
        try {
            Professor p =new Professor();
            //ProfessorServicesImpl piml= new ProfessorServicesImpl();
            Waitlist waitlist =new Waitlist();
            Session session = new Session(3, Subject.BIOLOGY, 1,"1/11/2023",3, waitlist);
            p.sessionList.add(session);
            //piml.addSession(session);
            TutoringServicesImpl tutoringService = new TutoringServicesImpl();
            tutoringService.sessions=p.sessionList;
            // Create the remote object
            //TutoringServices tutoringService = new TutoringServicesImpl();
            ProfessorServices professorServices = new ProfessorServicesImpl();
            // Create the registry on a specific port
            Registry registry = LocateRegistry.createRegistry(1099); // Default RMI port
            // Bind the remote object's stub in the registry under a specific name
            registry.rebind("TutoringService", tutoringService);
            System.out.println("Server is ready and services are registered.");
            List<Session> l =tutoringService.searchSessions(Subject.BIOLOGY.toString());
            for (Session s : l) {
                System.out.println(s.getSessionId());
            }



        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

import java.rmi.RemoteException;
import java.util.List;

public class ProfesorServices_test {
    public static void main(String[] args) throws RemoteException {
        ProfessorServices professorServices = new ProfessorServicesImpl();
        //Session session = new Session();
        //session.setSubject(Subject.MATHEMATICS);
        //session.setRate(100);
        //session.setSessionDate("2021-05-05");
        //session.setNbPlaces(10);
        //professorServices.addSession(session);
        List<Session> sessions=professorServices.getsession(8);
        int i=0;
        for (Session s : sessions) {
            i++;

        }
        System.out.println(i);




    }
}

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface TutoringServices extends Remote {

    List<Session> searchSessions(String subject) throws RemoteException;

    boolean bookSession(int sessionId, int studentId) throws RemoteException;
}

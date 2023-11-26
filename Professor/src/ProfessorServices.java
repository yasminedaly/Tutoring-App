import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ProfessorServices extends Remote {
    void addSession(Session session) throws RemoteException;
    void updateSession(Session session) throws RemoteException;
    void deleteSession(int sessionId) throws RemoteException;
    List<Session> getsession(int tutorId) throws RemoteException;
}

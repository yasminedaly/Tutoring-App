package interfaces;

import classes.Session;
import classes.Subject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface TutoringServices extends Remote {

    List<Session> searchSessions(Subject subject) throws RemoteException;

    boolean bookSession(int sessionId, int studentId) throws RemoteException;
}

package interfaces;

import classes.Session;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface TutoringServices extends Remote {

    public List<Session> getAllSessions() throws RemoteException;
    List<Session> searchSessions(String subject) throws RemoteException;

    boolean bookSession(int sessionId, int studentId) throws RemoteException;

    int getSessionId(String subject, float rate, String sessionDate, int nbPlaces) throws RemoteException;

    int getUserId(String email) throws RemoteException;

}

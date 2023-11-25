package services;

import classes.Session;
import classes.Subject;
import classes.Waitlist;
import interfaces.TutoringServices;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TutoringServicesImpl extends UnicastRemoteObject implements TutoringServices {

    private final List<Session> sessions;
    private final Waitlist waitlist;

    protected TutoringServicesImpl() throws RemoteException {
        super();
        this.sessions = new ArrayList<>();
        this.waitlist = new Waitlist();
        // populate the sessions list with initial data if needed
    }

    @Override
    public List<Session> searchSessions(Subject subject) {
        return sessions.stream()
                .filter(session -> session.getSubject() == subject)
                .collect(Collectors.toList());
    }

    @Override
    public boolean bookSession(int sessionId, int studentId) {
        for (Session session : sessions) {
            if (session.getSessionId() == sessionId) {
                if (session.addStudent(studentId)) {
                    return true;
                } else {
                    waitlist.addToWaitlist(sessionId, studentId);
                    return false; // classes.Session is full, student added to waitlist
                }
            }
        }
        return false; // classes.Session not found
    }
}

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TutoringServicesImpl extends UnicastRemoteObject implements TutoringServices {
    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:GustaveApp.sqlite";

    public List<Session> sessions;
    public final Waitlist waitlist;

    protected TutoringServicesImpl() throws RemoteException {
        super();

        this.sessions = new ArrayList<>();
        this.waitlist = new Waitlist();
        // populate the sessions list with initial data if needed
    }

    @Override
    public List<Session> searchSessions(String subject) {
        String sql = " select * from Session where subject = ?";
        List<Session> sessions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subject);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                Session session = new Session();
                //session.setSessionId(rs.getInt("sessionId"));
                session.setSubject(Subject.valueOf(rs.getString("subject")));
                session.setRate((float) rs.getDouble("rate"));
                session.setSessionDate(rs.getDate("sessionDate").toString());
                session.setNbPlaces(rs.getInt("nbPlaces"));
                session.setTutorId(5);
                sessions.add(session);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //return sessions.stream()
        //      .filter(session -> session.getSubject() == subject)
        //    .collect(Collectors.toList());
        return sessions;
    }
    @Override
    public boolean bookSession(int sessionId, int studentId) {
        for (Session session : sessions) {
            if (session.getSessionId() == sessionId) {
                if (session.addStudent(studentId)) {
                    return true;
                } else {
                    waitlist.addToWaitlist(sessionId, studentId);
                    return false; // Session is full, student added to waitlist
                }
            }
        }
        return false; // Session not found
    }
}

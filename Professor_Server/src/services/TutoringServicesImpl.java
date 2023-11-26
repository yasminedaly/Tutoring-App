package services;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import classes.*;
import interfaces.*;


public class TutoringServicesImpl extends UnicastRemoteObject implements TutoringServices {
    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:GustaveApp.sqlite";

    public List<Session> sessions;
    public final Waitlist waitlist;

    public TutoringServicesImpl() throws RemoteException {
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
        //get the nb of places in the session
        String sql = "select nbPlaces from Session where sessionId = ?";
        int nbPlaces = 0;
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);

             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sessionId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                nbPlaces = rs.getInt("nbPlaces");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //count the number of students in the session
        String sql2 = "select count(*) as number from SessionStudent where sessionId = ?";
        int nbStudents = 0;
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);

             PreparedStatement pstmt = conn.prepareStatement(sql2)) {
            pstmt.setInt(1, sessionId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                nbStudents = rs.getInt("number");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //check if the session is full
        if (nbStudents < nbPlaces) {
            //add the student to the session
            String sql3 = "INSERT INTO SessionStudent (sessionId, studentId) VALUES (?, ?)";
            try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);

                 PreparedStatement pstmt = conn.prepareStatement(sql3)) {
                pstmt.setInt(1, sessionId);
                pstmt.setInt(2, studentId);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            //add the student to the waitlist
            String sql4 = "INSERT INTO Waitlist (sessionId, studentId) VALUES (?, ?)";
            try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);

                 PreparedStatement pstmt = conn.prepareStatement(sql4)) {
                pstmt.setInt(1, sessionId);
                pstmt.setInt(2, studentId);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}

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
    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:C:/Users/Yasmine/IdeaProjects/Professor_Gustave/GustaveApp.sqlite";

    public List<Session> sessions;
    public final Waitlist waitlist;

    public TutoringServicesImpl() throws RemoteException {
        super();

        this.sessions = new ArrayList<>();
        this.waitlist = new Waitlist();
        // populate the sessions list with initial data if needed
    }


    public List<Session> getAllSessions() throws RemoteException {
        List<Session> sessions = new ArrayList<>();
        String sql = "SELECT * FROM Session";

        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = null;
            try {
                rs = pstmt.executeQuery();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            while (rs.next()) {
                // Assuming you have a constructor in your Session class that matches the database structure
                Session session = new Session(rs.getInt("SessionId"), Subject.valueOf(rs.getString("subject")),
                        rs.getFloat("rate"), rs.getString("sessionDate"),
                        rs.getInt("nbPlaces"));
                sessions.add(session);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sessions;
    }
    @Override
    public List<Session> searchSessions(String subject) throws RemoteException {
        List<Session> sessions = new ArrayList<>();
        String sql = "SELECT * FROM Session WHERE subject = ?"; // Add WHERE clause to filter by subject

        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, subject); // Set the subject parameter in the SQL query
            ResultSet rs = pstmt.executeQuery(); // Removed the null initialization for cleaner code

            while (rs.next()) {
                // Assuming you have a constructor in your Session class that matches the database structure
                Session session = new Session(rs.getInt("SessionId"), Subject.valueOf(rs.getString("subject")),
                        rs.getFloat("rate"), rs.getString("sessionDate"),
                        rs.getInt("nbPlaces"));
                sessions.add(session);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider proper logging
            throw new RemoteException("Database access error", e);
        }
        return sessions;
    }

    @Override
    public boolean bookSession(int sessionId, int studentId) throws RemoteException{
        // Check the number of available places in the session
        String sqlSelect = "SELECT nbPlaces FROM Session WHERE sessionId = ?";
        int nbPlaces = 0;
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect)) {
            pstmtSelect.setInt(1, sessionId);
            ResultSet rs = pstmtSelect.executeQuery();
            if (rs.next()) {
                nbPlaces = rs.getInt("nbPlaces");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        if (nbPlaces >= 1) {
            // Decrease number of places by 1 and add the student to the session
            String sqlUpdateSession = "UPDATE Session SET nbPlaces = nbPlaces - 1 WHERE sessionId = ?";
            String sqlInsertStudent = "INSERT INTO SessionStudent (sessionId, studentId) VALUES (?, ?)";
            try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING)) {
                // Start transaction
                conn.setAutoCommit(false);

                try (PreparedStatement pstmtUpdateSession = conn.prepareStatement(sqlUpdateSession);
                     PreparedStatement pstmtInsertStudent = conn.prepareStatement(sqlInsertStudent)) {

                    // Update session places
                    pstmtUpdateSession.setInt(1, sessionId);
                    pstmtUpdateSession.executeUpdate();

                    // Add student to session
                    pstmtInsertStudent.setInt(1, sessionId);
                    pstmtInsertStudent.setInt(2, studentId);
                    pstmtInsertStudent.executeUpdate();

                    // Commit transaction
                    conn.commit();
                    return true;
                } catch (SQLException e) {
                    // If there is an exception, roll back transaction
                    conn.rollback();
                    e.printStackTrace();
                    return false;
                } finally {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            // Add the student to the waitlist
            String sqlInsertWaitlist = "INSERT INTO Waitlist (sessionId, studentId) VALUES (?, ?)";
            try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
                 PreparedStatement pstmtInsertWaitlist = conn.prepareStatement(sqlInsertWaitlist)) {
                pstmtInsertWaitlist.setInt(1, sessionId);
                pstmtInsertWaitlist.setInt(2, studentId);
                pstmtInsertWaitlist.executeUpdate();
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }


    public int getSessionId(String subject, float rate, String sessionDate, int nbPlaces) throws RemoteException {
        String sql = "SELECT sessionId FROM Session WHERE subject = ? AND rate = ? AND sessionDate = ? AND nbPlaces = ?";
        int sessionId = 0;

        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subject);
            pstmt.setDouble(2, rate);
            pstmt.setString(3, sessionDate);  // Ensure this is in "YYYY-MM-DD" format
            pstmt.setInt(4, nbPlaces);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    sessionId = rs.getInt("sessionId");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sessionId;
    }

    @Override
    public int getUserId(String email) throws RemoteException {
        String sql = "SELECT student_id FROM student WHERE email = ?";
        int userId = -1;  // Default to -1 to indicate not found

        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getInt("student_id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userId;
    }

}

package CRUD;
import classes.EmailUtil;
import classes.Subject;

import java.sql.*;
import java.util.List;

public class Session_Crud {
    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:C:/Users/Yasmine/IdeaProjects/Professor_Gustave/GustaveApp.sqlite";
    static {
        try {
            // This will load the SQLite driver, each DB has its own driver
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void addSessionToDatabase(String subject,double rate,String sessionDate,int nbPlaces,int tutorId) throws SQLException {
        // SQL statement to insert a new user
        String sqlInsert = "INSERT INTO Session (subject,rate,sessionDate,nbPlaces,tutorId) VALUES (?, ?, ?, ?,?)";

        // Try-with-resources statement will auto close resources after try block
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            pstmt.setString(1, subject);
            pstmt.setDouble(2, rate);
            pstmt.setString(3, sessionDate);
            pstmt.setInt(4, nbPlaces);
            pstmt.setInt(5, tutorId);
            pstmt.executeUpdate();
        }
        String sql="select SessionId from Session where subject=? and rate=? and sessionDate=? and nbPlaces=? and tutorId=?";
        int sessionId=0;
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subject);
            pstmt.setDouble(2, rate);
            pstmt.setString(3, sessionDate);
            pstmt.setInt(4, nbPlaces);
            pstmt.setInt(5, tutorId);
            ResultSet rs=pstmt.executeQuery();
            sessionId=rs.getInt("SessionId");

        }
        String sqlInsert2 = "INSERT INTO SessionProfessor (sessionId,professorId) VALUES (?, ?)";
        Connection conn2 = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
        PreparedStatement pstmt2 = conn2.prepareStatement(sqlInsert2) ;
        pstmt2.setInt(1, sessionId);
        pstmt2.setInt(2, tutorId);
        pstmt2.executeUpdate();

    }
    public static int getSessionId(String subject, double rate, String sessionDate, int nbPlaces, int tutorId) throws SQLException {
        String sql = "SELECT sessionId FROM Session WHERE subject = ? AND rate = ? AND sessionDate = ? AND nbPlaces = ? AND tutorId = ?";
        int sessionId = 0;

        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subject);
            pstmt.setDouble(2, rate);
            pstmt.setString(3, sessionDate);  // Ensure this is in "YYYY-MM-DD" format
            pstmt.setInt(4, nbPlaces);
            pstmt.setInt(5, tutorId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    sessionId = rs.getInt("sessionId");
                }
            }
        }
        return sessionId;
    }



    public static int getNbStudentBooked(int sessionId) throws SQLException {
        String sql="select count(*) from SessionStudent where sessionId=?";
        int nbStudent=0;
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sessionId);
            ResultSet rs=pstmt.executeQuery();
            nbStudent=rs.getInt("count(*)");
        }
        return nbStudent;
    }
    public static int getNbStudentsWaitList(int sessionId) throws SQLException {
        String sql="select count(*) from Waitlist where sessionId=?";
        int nbStudent=0;
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sessionId);
            ResultSet rs=pstmt.executeQuery();
            nbStudent=rs.getInt("count(*)");
        }
        return nbStudent;
    }
    public static void  deleteSessionFromDatabase(int sessionId) throws SQLException {
        String sqlDelete = "DELETE FROM Session WHERE sessionId = ?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
            pstmt.setInt(1, sessionId);
            pstmt.executeUpdate();
        }
        String sqlDelete2 = "DELETE FROM SessionProfessor WHERE sessionId = ?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sqlDelete2)) {
            pstmt.setInt(1, sessionId);

            pstmt.executeUpdate();
        }
        String sqlDelete3 = "DELETE FROM SessionStudent WHERE sessionId = ?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sqlDelete3)) {
            pstmt.setInt(1, sessionId);

            pstmt.executeUpdate();
        }
    }
    public static void UpdateSession(int sessionId, double rate, String date, int nbPlaces, String subject) {
        String sql = "UPDATE Session SET subject = ?, rate = ?, sessionDate = ?, nbPlaces = ? WHERE sessionId = ?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subject);
            pstmt.setDouble(2, rate); // Set rate
            pstmt.setString(3, date); // Set date
            pstmt.setInt(4, nbPlaces); // Set nbPlaces
            pstmt.setInt(5, sessionId); // WHERE clause to identify the record to update

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected); // For debugging
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void  Update_WaitList(int sessionId) throws SQLException {
        String sql="select * from Waitlist where sessionId=?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sessionId);
            ResultSet rs=pstmt.executeQuery();
            while(rs.next()){
                int studentId=rs.getInt("studentId");
                String sqlInsert = "INSERT INTO SessionStudent (sessionId,studentId) VALUES (?, ?)";
                Connection conn2 = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
                PreparedStatement pstmt2 = conn2.prepareStatement(sqlInsert) ;
                pstmt2.setInt(1, sessionId);
                pstmt2.setInt(2, studentId);
                pstmt2.executeUpdate();
                String sqlDelete = "DELETE FROM Waitlist WHERE sessionId = ? AND studentId = ?";
                Connection conn3 = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
                PreparedStatement pstmt3 = conn3.prepareStatement(sqlDelete) ;
                pstmt3.setInt(1, sessionId);
                pstmt3.setInt(2, studentId);
                pstmt3.executeUpdate();
            }
        }
    }
    public static void update_waitlist(int id) throws SQLException {
        String selectSql = "SELECT studentId FROM waitlist WHERE sessionId = ? LIMIT 1";

        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

            conn.setAutoCommit(false); // Start transaction

            // Select the student
            selectStmt.setInt(1, id);
            int studentId = 0;
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    studentId = rs.getInt("studentId");
                }
            }

            // Insert into sessionstudent
            String insertSql = "INSERT INTO sessionstudent (sessionId, studentId) VALUES (?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, id);
                insertStmt.setInt(2, studentId);
                insertStmt.executeUpdate();
            }

            // Delete from waitlist
            String deleteSql = "DELETE FROM waitlist WHERE sessionId = ? AND studentId = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, id);
                deleteStmt.setInt(2, studentId);
                deleteStmt.executeUpdate();
            }
            String sql7="select subject from session where sessionId=?";
            String subject="";
            try (PreparedStatement pstmt = conn.prepareStatement(sql7)) {
                pstmt.setInt(1, id);
                ResultSet rs=pstmt.executeQuery();
                subject=rs.getString("subject");
            }
            conn.commit();
            String emailSql = "SELECT email FROM Student WHERE student_id = ?";
            try (PreparedStatement emailStmt = conn.prepareStatement(emailSql)) {
                emailStmt.setInt(1, studentId);
                try (ResultSet rs = emailStmt.executeQuery()) {
                    if (rs.next()) {
                        String email = rs.getString("email");
                        EmailUtil.sendEmail(email, "Session Update", "You have been added to a session for " + subject);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}

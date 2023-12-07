package CRUD;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import classes.*;
public class Professor_Crud {
    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:GustaveApp.sqlite";
    public static void addProfessorToDatabase(String firstName,String lastName,int age,String email,String phoneNumber,String password,String bio) throws SQLException {
        // SQL statement to insert a new professor
        String strSelect = "INSERT INTO professor (firstName, lastName, age, email, phoneNumber, password, bio) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Establish a connection to the database
        Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
        PreparedStatement pstmt = conn.prepareStatement(strSelect);
        pstmt.setString(1,firstName);
        pstmt.setString(2,lastName);
        pstmt.setInt(3, age);
        pstmt.setString(4, email);
        pstmt.setString(5, phoneNumber);
        pstmt.setString(6, password);
        pstmt.setString(7, bio);
        pstmt.executeUpdate();


    }
    public static void deleteProfessor_by_Firstname_and_Lastname(String firstName, String lastName) {
        String sql = "DELETE FROM professor WHERE firstName = ? AND lastName = ?";

        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateProfessorBio(String firstName, String lastName, String newBio) {
        String sql = "UPDATE professor SET bio = ? WHERE firstName = ? AND lastName = ?";

        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newBio);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<Session> getSessions(int professorId) {
        String sql = "SELECT * FROM session WHERE tutorId = ?";
        List<Session> sessions = null;

        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, professorId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int sessionId = rs.getInt("sessionId");
                String subject = rs.getString("subject");
                float rate = rs.getFloat("rate");
                String sessionDate = rs.getString("sessionDate");
                int nbPlaces = rs.getInt("nbPlaces");
                //int tutorId = rs.getInt("professorId");

                Session session = new Session(sessionId, Subject.valueOf(subject), rate, sessionDate, nbPlaces);
                sessions.add(session);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}

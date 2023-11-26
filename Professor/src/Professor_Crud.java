import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
public class Professor_Crud {
    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:GustaveApp.sqlite";
    public static void addProfessorToDatabase(Professor professor) throws SQLException {
        // SQL statement to insert a new professor
        String strSelect = "INSERT INTO professor (firstName, lastName, age, email, phoneNumber, password, bio) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Establish a connection to the database
        Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
        PreparedStatement pstmt = conn.prepareStatement(strSelect);
        pstmt.setString(1,professor.getFirstName());
        pstmt.setString(2, professor.getLastName());
        pstmt.setInt(3, professor.getAge());
        pstmt.setString(4, professor.getEmail());
        pstmt.setString(5, professor.getPhoneNumber());
        pstmt.setString(6, professor.getPassword());
        pstmt.setString(7, professor.getBio());
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
}
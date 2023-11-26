import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Student_CRUD {
    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:C:/Users/rabeb/OneDrive/Desktop/Professor/GustaveApp.sqlite";
    public static void addStudentToDatabase(Student student) throws SQLException {
        String strSelect = "INSERT INTO student (firstName, lastName, age, email, phoneNumber, password, bio) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Establish a connection to the database
        Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
        PreparedStatement pstmt = conn.prepareStatement(strSelect);
        pstmt.setString(1,student.getFirstName());
        pstmt.setString(2, student.getLastName());
        pstmt.setInt(3, student.getAge());
        pstmt.setString(4, student.getEmail());
        pstmt.setString(5, student.getPhoneNumber());
        pstmt.setString(6, student.getPassword());
        pstmt.setString(7, student.getBio());
        pstmt.executeUpdate();

    }
    public static void deleteProfessor_by_Firstname_and_Lastname(String firstName, String lastName) {
        String sql = "DELETE FROM student WHERE firstName = ? AND lastName = ?";

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
        String sql = "UPDATE student SET bio = ? WHERE firstName = ? AND lastName = ?";

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

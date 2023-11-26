import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
public class DatabaseUtils {

    // You need to provide the path to your SQLite database file
    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:GustaveApp.sqlite";

    // Method to add a professor to the database
    public static void addProfessorToDatabase(Professor professor) throws SQLException {
        // SQL statement to insert a new professor
        String strSelect = "INSERT INTO professor (firstName, lastName, age, email, phoneNumber, password, bio) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Establish a connection to the database
        Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
        PreparedStatement pstmt = conn.prepareStatement(strSelect);
        pstmt.setString(1, "John");
        pstmt.setString(2, "Doe");
        pstmt.setInt(3, 30);
        pstmt.setString(4, "john.doe@example.com");
        pstmt.setString(5, "1234567890");
        pstmt.setString(6, "password123");
        pstmt.setString(7, "Bio information");
        //ResultSet rset = pstmt.executeQuery(strSelect);
        pstmt.executeUpdate();


    }
}

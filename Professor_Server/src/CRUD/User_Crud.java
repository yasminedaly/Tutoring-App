package CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User_Crud {

    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:GustaveApp.sqlite";
    public static void addUserToDatabase(String firstName,String lastName,int age,String email,String phoneNumber,String password,String bio, String role) throws SQLException {
        // SQL statement to insert a new professor
        String strSelect = "INSERT INTO User (firstName, lastName, age, email, phoneNumber, password, bio, role) VALUES (?, ?, ?, ?, ?, ?, ?,?)";

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
        pstmt.setString(8, role);
        pstmt.executeUpdate();


    }
}

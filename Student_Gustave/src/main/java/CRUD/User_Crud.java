package CRUD;

import classes.Role;
import classes.User;

import java.sql.*;

public class User_Crud {
    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:/C:/Users/Yasmine/IdeaProjects/Professor_Gustave/GustaveApp.sqlite";

    static {
        try {
            // This will load the SQLite driver, each DB has its own driver
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void addUserToDatabase(String firstName, String lastName, int age, String email,
                                         String phoneNumber, String password, String bio, String role) throws SQLException {
        // SQL statement to insert a new user
        String sqlInsert = "INSERT INTO User (firstName, lastName, age, email, phoneNumber, password, bio, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Try-with-resources statement will auto close resources after try block
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, age);
            pstmt.setString(4, email);
            pstmt.setString(5, phoneNumber);
            pstmt.setString(6, password);
            pstmt.setString(7, bio);
            pstmt.setString(8, role);
            pstmt.executeUpdate();
        }
        // if role is professor add to professor table
        if (role.equals("Professor")) {
            String sqlInsert2 = "INSERT INTO Professor (firstName, lastName, age, email, phoneNumber, password, bio) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
                 PreparedStatement pstmt = conn.prepareStatement(sqlInsert2)) {
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setInt(3, age);
                pstmt.setString(4, email);
                pstmt.setString(5, phoneNumber);
                pstmt.setString(6, password);
                pstmt.setString(7, bio);
                pstmt.executeUpdate();
            }
        }

    }
    // check if user in database
    //public static boolean readUserFromDatabase(String email, String password) throws SQLException {
        // SQL statement to read a user
      //  String sqlSelect = "SELECT * FROM User WHERE email = ? AND password = ?";

        // Try-with-resources statement will auto close resources after try block
        //try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
          //   PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {

           // pstmt.setString(1, email);
            //pstmt.setString(2, password);

            // Execute the query and get the result set directly
            //try (ResultSet rs = pstmt.executeQuery()) {
              //  return rs.next();
            //}
        //}

    //}
    public static User readUserFromDatabase(String email, String password) throws SQLException {
        String sqlSelect = "SELECT * FROM User WHERE email = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
              PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {

              pstmt.setString(1, email);
             pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setEmail(rs.getString("email"));
                    user.setPhoneNumber(rs.getString("phoneNumber"));
                    user.setBio(rs.getString("bio"));
                    user.setAge(rs.getInt("age"));
                    user.setPassword(rs.getString("password"));
                    // Add other fields as needed
                    return user;
                }
                return null;
            }
        }



    }


    public static User updateUserInDatabase(String firstName, String lastName, int age, String email, String phoneNumber, String bio) {

        String sql = "UPDATE User SET firstName = ?, lastName = ?, age = ?, phoneNumber = ?, bio = ? WHERE email = ?";
        User user = new User();
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, age);
            pstmt.setString(4, phoneNumber);
            pstmt.setString(5, bio);
            pstmt.setString(6, email);
            pstmt.executeUpdate();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAge(age);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setBio(bio);
            String sqlSelect = "SELECT password,role FROM User WHERE email = ?";
            PreparedStatement pstmt2 = conn.prepareStatement(sqlSelect);
            pstmt2.setString(1, email);
            ResultSet rs = pstmt2.executeQuery();
            if (rs.next()) {
                user.setPassword(rs.getString("password"));
                user.setRole(Role.valueOf(rs.getString("role")));
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

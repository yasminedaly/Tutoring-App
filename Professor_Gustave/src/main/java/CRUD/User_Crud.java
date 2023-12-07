package CRUD;

import classes.Role;
import classes.Session;
import classes.Subject;
import classes.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class User_Crud {
    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:C:/Users/Yasmine/IdeaProjects/Professor_Gustave/GustaveApp.sqlite";

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
        else if (role.equals("Student")) {
            String sqlInsert3 = "INSERT INTO student (firstName, lastName, age, email, phoneNumber, password, bio) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
                 PreparedStatement pstmt = conn.prepareStatement(sqlInsert3)) {
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
    public static int getUserId(String email) throws SQLException {
        String sqlSelect = "SELECT professor_id FROM professor WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("professor_id");
                }
                return 0;
            }
        }
    }
    public static int getSessionsNumber(String email) throws SQLException {
        int userId=0;
        String sql="select professor_id from professor where email=?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                userId= rs.getInt("professor_id");
            }
        }
        String sqlSelect = "SELECT COUNT(*) FROM Session WHERE tutorId = ?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("COUNT(*)");
                }
                return 0;
            }
        }
    }
    public static List<Session> getSessions(String email) throws SQLException {
        int userId=0;
        String sql="select professor_id from professor where email=?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                userId= rs.getInt("professor_id");
            }
        }
        String sqlSelect = "SELECT * FROM Session WHERE tutorId = ?";
        List<Session> sessions = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sqlSelect)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Assuming you have a constructor or setter methods to set these properties
                    Session session = new Session();
                    session.setSessionDate(rs.getString("sessionDate"));
                    session.setSubject(Subject.valueOf(rs.getString("subject")));
                    session.setRate((float) rs.getDouble("rate"));
                    session.setNbPlaces(rs.getInt("nbPlaces"));
                    sessions.add(session);
                }
            }
        }

        return sessions.isEmpty() ? null : sessions;
    }

    public static int getTutorId(String email) {
        String sql = "SELECT professor_id FROM Professor WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("professor_id");
                }
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getTutorName(String email) {
        String sql = "SELECT firstName, lastName FROM Professor WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("firstName") + " " + rs.getString("lastName");
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getTutorBio(String email) {
        String sql = "SELECT bio FROM Professor WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("bio");
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getTutorPhoneNumber(String email) {
        String sql = "SELECT phoneNumber FROM Professor WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("phoneNumber");
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getTutorEmail(String email) {
        String sql = "SELECT email FROM Professor WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("email");
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getTutorPassword(String email) {
        String sql = "SELECT password FROM Professor WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password");
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int getTutorAge(String email) {
        String sql = "SELECT age FROM Professor WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("age");
                }
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getTutorFirstName(String email) {
        String sql = "SELECT firstName FROM Professor WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("firstName");
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getTutorLastName(String email) {
        String sql = "SELECT lastName FROM Professor WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("lastName");
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

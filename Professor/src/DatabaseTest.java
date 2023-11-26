import java.sql.SQLException;
public class DatabaseTest {

    public static void main(String[] args) {
        try {
            // Create a Professor object with test data
            Professor testProfessor = new Professor();
            testProfessor.setFirstName("John");
            testProfessor.setLastName("Doe");
            testProfessor.setAge(40);
            testProfessor.setEmail("johndoe@example.com");
            testProfessor.setPhoneNumber("1234567890");
            testProfessor.setPassword("securepassword");
            testProfessor.setBio("A brief bio of John Doe");

            // Directly call the static method on the DatabaseUtils class
            DatabaseUtils.addProfessorToDatabase(testProfessor);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Test failed: " + e.getMessage());
        }
    }
}

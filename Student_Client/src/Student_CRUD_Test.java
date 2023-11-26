import java.sql.SQLException;

public class Student_CRUD_Test {
    public static void main(String[] args) throws SQLException {
        //to add a professor
        Student teststudent = new Student();
        teststudent.setFirstName("rabeb");
        teststudent.setLastName("sdiri");
        teststudent.setAge(40);
        teststudent.setEmail("johndoe@example.com");
        teststudent.setPhoneNumber("1234567890");
        teststudent.setPassword("securepassword");
        teststudent.setBio("A brief bio of rabeb");
        Student_CRUD.addStudentToDatabase(teststudent);
        //********************
        // to delete a professor
        Student_CRUD.deleteProfessor_by_Firstname_and_Lastname("rabeb", "sdiri");
        //********************
        // to update a professor
        //Student_CRUD.updateProfessorBio("rabeb", "sdiri", "new bio");
    }
}

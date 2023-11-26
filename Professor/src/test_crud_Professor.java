import java.sql.SQLException;

public class test_crud_Professor {
    public static void main(String[] args) {

            //to add a professor
            //Professor testProfessor = new Professor();
            //testProfessor.setFirstName("rabeb");
            //testProfessor.setLastName("sdiri");
            //testProfessor.setAge(40);
            //testProfessor.setEmail("johndoe@example.com");
            //testProfessor.setPhoneNumber("1234567890");
            //testProfessor.setPassword("securepassword");
            //testProfessor.setBio("A brief bio of John Doe");
            //Professor_Crud.addProfessorToDatabase(testProfessor);
            //********************
            // to delete a professor
            //Professor_Crud.deleteProfessor_by_Firstname_and_Lastname("rabeb", "sdiri");
            //********************
            // to update a professor
            Professor_Crud.updateProfessorBio("John", "Doe", "new bio");

    }
}

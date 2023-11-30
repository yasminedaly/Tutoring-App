package CRUD;
import java.sql.SQLException;
import classes.*;

public class test_crud_Professor {
    public static void main(String[] args) {

            //to add a professor
            Professor testProfessor = new Professor();
            //testProfessor.setFirstName("rabeb");
            //testProfessor.setLastName("sdiri");
            //testProfessor.setAge(40);
            //testProfessor.setEmail("johndoe@example.com");
            //testProfessor.setPhoneNumber("1234567890");
            //testProfessor.setPassword("securepassword");
            //testProfessor.setBio("A brief bio of John Doe");
        try {
            Professor_Crud.addProfessorToDatabase("Yasmine","Daly",23,"yasmine@gmail.com","51878765","HELLOWORLD","Hi ppl");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //********************
            // to delete a professor
            //Professor_Crud.deleteProfessor_by_Firstname_and_Lastname("rabeb", "sdiri");
            //********************
            // to update a professor
            //Professor_Crud.updateProfessorBio("John", "Doe", "new bio");

    }
}

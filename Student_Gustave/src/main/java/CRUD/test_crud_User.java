package CRUD;

import classes.User;

import java.sql.SQLException;

public class test_crud_User {

    public static void main(String[] args) {

        User testProfessor = new User();

        try {
            User_Crud.addUserToDatabase("Yasmine","Daly",23,"yasmine@gmail.com","51878765","HELLOWORLD","Hi ppl","Student");
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

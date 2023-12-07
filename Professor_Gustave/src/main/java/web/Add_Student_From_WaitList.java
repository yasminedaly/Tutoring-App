package web;

import CRUD.Session_Crud;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Update_WaitList", urlPatterns = {"/Update_WaitList"})
public class Add_Student_From_WaitList extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Add_Student_From_WaitList(request, response);
    }
    private void  Add_Student_From_WaitList(HttpServletRequest request, HttpServletResponse response)
    {
        int sessionId = Integer.parseInt(request.getParameter("id"));
        try {
            System.out.println("Session ID: " + sessionId);
            Session_Crud.update_waitlist(sessionId);
            System.out.println("Student added successfully");
            response.sendRedirect("sessions.jsp?email=" + request.getParameter("email"));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

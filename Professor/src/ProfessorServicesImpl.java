import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
public class ProfessorServicesImpl extends UnicastRemoteObject implements ProfessorServices {
    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:GustaveApp.sqlite";

    public final List<Session> sessions;
    Professor professor = new Professor();
    protected ProfessorServicesImpl() throws RemoteException {
        super();
        this.sessions = new ArrayList<>();
        // populate the sessions list with initial data if needed
    }

    @Override
    public void addSession(Session session) {

        String sql = "INSERT INTO Session (subject, rate, sessionDate, nbPlaces, tutorId) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, session.getSubject().toString());
            pstmt.setDouble(2, session.getRate());
            pstmt.setDate(3, java.sql.Date.valueOf(session.getSessionDate()));
            pstmt.setInt(4, session.getNbPlaces());
            //retrive the profesor id from the database


            //pstmt.setInt(5, 10);//to be changed
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void updateSession(Session session) throws RemoteException {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getSessionId() == session.getSessionId()) {
                sessions.set(i, session);
                return;
            }
        }
        throw new RemoteException("Session not found for update.");
    }

    @Override
    public void deleteSession(int sessionId) throws RemoteException {
        Iterator<Session> iterator = sessions.iterator();
        while (iterator.hasNext()) {
            Session session = iterator.next();
            if (session.getSessionId() == sessionId) {
                iterator.remove();
                return;
            }
        }
        throw new RemoteException("Session not found for deletion.");
    }
    public List<Session> getsession(int tutorid) throws RemoteException{
        String sql = " select * from Session where tutorId = ?";
        List<Session> sessions = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, tutorid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Session session = new Session();
                session.setSessionId(rs.getInt("sessionId"));
                session.setSubject(Subject.valueOf(rs.getString("subject")));
                session.setRate((float) rs.getDouble("rate"));
                session.setSessionDate(rs.getDate("sessionDate").toString());
                session.setNbPlaces(rs.getInt("nbPlaces"));
                session.setTutorId(5);//id to be changed
                sessions.add(session);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }
}

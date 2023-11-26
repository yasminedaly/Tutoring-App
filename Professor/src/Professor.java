import java.util.ArrayList;
import java.util.List;

public class Professor extends User {
    public List<Session> sessionList = new ArrayList<>();
    public Professor() {

        super();
        super.role = Role.Professor;
    }
}

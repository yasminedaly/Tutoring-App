package classes;

import java.io.Serializable;
import java.util.List;

public class Student extends User implements Serializable {
    public Student() {
        super();
    }

    private List<Session> Sessions;

    public void bookAppointment(Session appointment) {
        Sessions.add(appointment);
    }
}



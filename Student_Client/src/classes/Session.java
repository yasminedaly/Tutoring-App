package classes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Session implements Serializable {
    private int sessionId;
    private Subject subject; // Using classes.Subject enum
    private float rate;
    private String sessionDate;
    private Set<Integer> studentIds = new HashSet<>(); // Students booked for the session
    private int nbPlaces;
    private Waitlist waitlist; // Reference to the classes.Waitlist class

    public Session(int sessionId, Subject subject, float rate, String sessionDate, int nbPlaces, Waitlist waitlist) {
        this.sessionId = sessionId;
        this.subject = subject;
        this.rate = rate;
        this.sessionDate = sessionDate;
        this.nbPlaces = nbPlaces;
        this.waitlist = waitlist; // Initialize the classes.Waitlist reference
    }

    public boolean addStudent(int studentId) {
        if (studentIds.size() < nbPlaces) {
            studentIds.add(studentId);
            return true; // classes.Student added successfully
        } else {
            // classes.Session is full, add to waitlist using the classes.Waitlist reference
            waitlist.addToWaitlist(sessionId, studentId);
            return false; // classes.Student added to waitlist
        }
    }

    // Other getters and setters

    public int getSessionId() {
        return sessionId;
    }

    public Subject getSubject() {
        return subject;
    }

    public float getRate() {
        return rate;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public Set<Integer> getStudentIds() {
        return studentIds;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
    }

    public void setStudentIds(Set<Integer> studentIds) {
        this.studentIds = studentIds;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    // Getter for the waitlist reference
    public Waitlist getWaitlist() {
        return waitlist;
    }

    // Other methods for managing the waitlist can be added as needed
}

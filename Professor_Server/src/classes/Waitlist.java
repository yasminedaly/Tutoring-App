package classes;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Waitlist implements Serializable {
    // Mapping session IDs to a queue of student IDs who are waiting for a spot to become available
    private HashMap<Integer, Queue<Integer>> waitlistBySession;

    public Waitlist() {
        this.waitlistBySession = new HashMap<>();
    }

    // Adds a student to the waitlist for a specific session
    public void addToWaitlist(int sessionId, int studentId) {
        Queue<Integer> waitlist = waitlistBySession.computeIfAbsent(sessionId, k -> new LinkedList<>());
        if (!waitlist.contains(studentId)) {
            waitlist.add(studentId);
        }
    }

    // Removes a student from the waitlist for a specific session
    public void removeFromWaitlist(int sessionId, int studentId) {
        if (waitlistBySession.containsKey(sessionId)) {
            Queue<Integer> waitlist = waitlistBySession.get(sessionId);
            waitlist.remove(studentId);
        }
    }

    // Retrieves and removes the next student ID from the waitlist for a specific session
    public Integer pollFromWaitlist(int sessionId) {
        Queue<Integer> waitlist = waitlistBySession.getOrDefault(sessionId, new LinkedList<>());
        return waitlist.poll(); // Returns null if the waitlist is empty
    }

    // Check if there are any students in the waitlist for a specific session
    public boolean hasWaitlistedStudents(int sessionId) {
        Queue<Integer> waitlist = waitlistBySession.getOrDefault(sessionId, new LinkedList<>());
        return !waitlist.isEmpty();
    }

    // Notify the next student on the waitlist for a specific session
    // This should be called when a spot becomes available
    public Integer notifyNextStudent(int sessionId) {
        Integer nextStudentId = pollFromWaitlist(sessionId);
        if (nextStudentId != null) {
            // Logic to notify the student (e.g., sending an email or a message)
            // For now, we'll just return the student ID to indicate they've been notified
            return nextStudentId;
        }
        return null; // No student to notify
    }
}

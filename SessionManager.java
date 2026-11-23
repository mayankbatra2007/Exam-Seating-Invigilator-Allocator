// / This class manages exam sessions.

import java.util.ArrayList;

public class SessionManager {

    private ArrayList<ExamSession> sessions = new ArrayList<>();

    // Add an exam session
    public boolean addSession(ExamSession session) {

        for (ExamSession existing : sessions) {

            // Prevent duplicate subject/date/time entries
            if (existing.getSubject().equalsIgnoreCase(session.getSubject())
                    && existing.hasSameSlot(session)) {
                return false;
            }
        }

        sessions.add(session);
        return true;
    }

    // Display all exam sessions
    public void displaySessions() {

        if (sessions.isEmpty()) {
            System.out.println("No exam sessions available.");
            return;
        }

        for (ExamSession session : sessions) {
            session.displaySession();
        }
    }

    // Search sessions by subject
    public void searchBySubject(String subject) {

        boolean found = false;

        for (ExamSession session : sessions) {

            if (session.getSubject().equalsIgnoreCase(subject.trim())) {
                session.displaySession();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No session found for this subject.");
        }
    }

    // Generate a complete report using StringBuilder
    public String generateReport() {

        StringBuilder report = new StringBuilder();

        report.append("\n===== EXAM SESSION REPORT =====\n");

        for (ExamSession session : sessions) {
            report.append(session.getSessionReport()).append("\n");
        }

        return report.toString();
    }

    public int getSessionCount() {
        return sessions.size();
    }

    // Returns all exam sessions
    public ArrayList<ExamSession> getSessions() {
        return sessions;
    }

}

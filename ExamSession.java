// This class represents an exam.

public class ExamSession {

    private String subject;
    private String date;
    private String timeSlot;

    public ExamSession(String subject, String date, String timeSlot) {
        this.subject = subject.trim();
        this.date = date.trim();
        this.timeSlot = timeSlot.trim();
    }

    public String getSubject() {
        return subject;
    }

    public String getDate() {
        return date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    // Check whether two sessions have the same date and time
    public boolean hasSameSlot(ExamSession other) {
        return date.equalsIgnoreCase(other.date)
                && timeSlot.equalsIgnoreCase(other.timeSlot);
    }

    // Display session details
    public void displaySession() {
        System.out.printf("Subject: %-15s | Date: %-12s | Slot: %s%n",
                subject, date, timeSlot);
    }

    // Generate a formatted session report
    public String getSessionReport() {

        StringBuilder report = new StringBuilder();

        report.append("Subject: ").append(subject);
        report.append(" | Date: ").append(date);
        report.append(" | Time: ").append(timeSlot);

        return report.toString();
    }
}

// Concepts used
// - Classes
// - Objects
// - Constructors
// - Encapsulation
// - Methods
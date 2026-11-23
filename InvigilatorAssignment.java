
import java.util.ArrayList;
import java.util.List;

public class InvigilatorAssignment {

    private String roomId;
    private ExamSession examSession;
    private List<Invigilator> assignedInvigilators;

    public InvigilatorAssignment(String roomId, ExamSession examSession) {
        this.roomId = roomId;
        this.examSession = examSession;
        this.assignedInvigilators = new ArrayList<>();
    }

    // Add an invigilator to this room
    public void addInvigilator(Invigilator invigilator) {
        assignedInvigilators.add(invigilator);
    }

    public String getRoomId() {
        return roomId;
    }

    public ExamSession getExamSession() {
        return examSession;
    }

    public List<Invigilator> getAssignedInvigilators() {
        return assignedInvigilators;
    }

    // Display this room's allocation
    public void displayAssignment() {

        System.out.println("\nRoom: " + roomId);
        System.out.println("Subject: " + examSession.getSubject());
        System.out.println("Date: " + examSession.getDate());
        System.out.println("Time: " + examSession.getTimeSlot());

        System.out.println("Assigned Invigilators:");

        if (assignedInvigilators.isEmpty()) {
            System.out.println("No invigilators assigned.");
            return;
        }

        for (Invigilator invigilator : assignedInvigilators) {
            System.out.println("- " + invigilator.getName()
                    + " (" + invigilator.getType() + ")");
        }
    }
}

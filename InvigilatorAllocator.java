
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Comparator;

public class InvigilatorAllocator {

    // Stores assignments for each room
    private ArrayList<InvigilatorAssignment> assignments = new ArrayList<>();

    // Tracks invigilators assigned during a particular session slot
    private Map<String, List<Invigilator>> slotAssignments = new HashMap<>();

    // Calculates required invigilators
    public int calculateRequiredInvigilators(int studentCount) {

        // Ceiling division: (n + 24) / 25
        int ratio = (studentCount + 24) / 25;

        return Math.max(2, ratio);
    }

    // Allocates invigilators to a room
    public boolean allocate(
            Room room,
            ExamSession session,
            List<Invigilator> availableInvigilators,
            int studentCount) {

        int required = calculateRequiredInvigilators(studentCount);

        System.out.println("\nRoom: " + room.getRoomId());
        System.out.println("Required Invigilators: " + required);

        // Create a unique key for this exam date and time
        String slotKey = session.getDate() + "|" + session.getTimeSlot();

        List<Invigilator> alreadyAssigned =
                slotAssignments.getOrDefault(slotKey, new ArrayList<>());

        // PriorityQueue selects candidates with fewer duties first
        PriorityQueue<Invigilator> queue = new PriorityQueue<>(
                Comparator.comparingInt(Invigilator::getDutiesSoFar)
        );

        // Add eligible candidates
        for (Invigilator invigilator : availableInvigilators) {

            // Check maximum duty limit
            if (invigilator.getDutiesSoFar() >= invigilator.getMaxDuties()) {
                continue;
            }

            // Prevent time clashes
            if (alreadyAssigned.contains(invigilator)) {
                continue;
            }

            queue.add(invigilator);
        }

        // Store selected invigilators temporarily
        ArrayList<Invigilator> selected = new ArrayList<>();

        while (selected.size() < required && !queue.isEmpty()) {
            selected.add(queue.poll());
        }

        // If there aren't enough eligible invigilators, don't allocate
        if (selected.size() < required) {

            System.out.println("Not enough eligible invigilators!");
            return false;
        }

        // Create an assignment record
        InvigilatorAssignment assignment =
                new InvigilatorAssignment(room.getRoomId(), session);

        // Commit selected invigilators
        for (Invigilator invigilator : selected) {

            assignment.addInvigilator(invigilator);

            // Update duty count
            invigilator.incrementDuties();

            // Update slot allocation tracking
            alreadyAssigned.add(invigilator);
        }

        slotAssignments.put(slotKey, alreadyAssigned);
        assignments.add(assignment);

        System.out.println("Allocation successful!");

        assignment.displayAssignment();

        return true;
    }

    // Display all allocations
    public void displayAllAssignments() {

        if (assignments.isEmpty()) {
            System.out.println("No invigilator allocations yet.");
            return;
        }

        for (InvigilatorAssignment assignment : assignments) {
            assignment.displayAssignment();
        }
    }
}

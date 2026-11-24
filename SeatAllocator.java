import java.util.ArrayList;
import java.util.List;

public class SeatAllocator {

    // Allocate as many students as possible in one room.
    public List<Student> allocateSeats(
            Room room,
            List<Student> students) {

        String[][] seats = room.getSeats();

        // Clear previous seating arrangement.
        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[row].length; col++) {
                seats[row][col] = null;
            }
        }

        ArrayList<Student> allocatedStudents = new ArrayList<>();

        // Try to allocate every student in the given list.
        for (Student student : students) {

            boolean allocated = false;

            // Search for a suitable empty seat.
            for (int row = 0; row < seats.length; row++) {

                for (int col = 0; col < seats[row].length; col++) {

                    // Skip occupied seats.
                    if (seats[row][col] != null) {
                        continue;
                    }

                    // Check the four neighbouring seats.
                    if (isSafe(
                            row,
                            col,
                            student.getBranch(),
                            seats,
                            students)) {

                        // Allocate the student.
                        seats[row][col] = student.getRollNo();

                        allocatedStudents.add(student);

                        allocated = true;
                        break;
                    }
                }

                if (allocated) {
                    break;
                }
            }
        }

        return allocatedStudents;
    }

    // Check whether a seat is safe for the student's branch.
    private boolean isSafe(
            int row,
            int col,
            String branch,
            String[][] seats,
            List<Student> students) {

        // Check the seat above.
        if (row > 0 &&
                sameBranch(
                        seats[row - 1][col],
                        branch,
                        students)) {

            return false;
        }

        // Check the seat below.
        if (row + 1 < seats.length &&
                sameBranch(
                        seats[row + 1][col],
                        branch,
                        students)) {

            return false;
        }

        // Check the seat on the left.
        if (col > 0 &&
                sameBranch(
                        seats[row][col - 1],
                        branch,
                        students)) {

            return false;
        }

        // Check the seat on the right.
        if (col + 1 < seats[row].length &&
                sameBranch(
                        seats[row][col + 1],
                        branch,
                        students)) {

            return false;
        }

        return true;
    }

    // Find the branch of an already seated student.
    private boolean sameBranch(
            String rollNo,
            String branch,
            List<Student> students) {

        if (rollNo == null) {
            return false;
        }

        for (Student student : students) {

            if (student.getRollNo().equalsIgnoreCase(rollNo)) {

                return student.getBranch()
                        .equalsIgnoreCase(branch);
            }
        }

        return false;
    }
}
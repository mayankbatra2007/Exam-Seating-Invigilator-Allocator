
import java.util.List;

public class SeatAllocator {

    // Limits the search to avoid excessive computation
    private static final long MAX_SEARCH_NODES = 200_000;
    private long nodesVisited;

    // Attempts to seat students in one room
    public boolean allocateSeats(Room room, List<Student> students) {

        String[][] seats = room.getSeats();

        // Clear previous seating arrangement
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = null;
            }
        }

        nodesVisited = 0;

        // Check whether students exceed room capacity
        if (students.size() > room.getCapacity()) {
            return false;
        }

        boolean[] used = new boolean[students.size()];

        // Start the recursive backtracking process
        boolean result = solve(room, students, seats, used, 0);

        // Clear room if allocation failed
        if (!result) {
            for (int i = 0; i < seats.length; i++) {
                for (int j = 0; j < seats[i].length; j++) {
                    seats[i][j] = null;
                }
            }
        }

        return result;
    }

    // Recursive method to assign students to seats
    private boolean solve(
            Room room,
            List<Student> students,
            String[][] seats,
            boolean[] used,
            int position) {

        // All students have been seated successfully
        if (position == students.size()) {
            return true;
        }

        // Stop if the search limit is reached
        if (++nodesVisited > MAX_SEARCH_NODES) {
            return false;
        }

        int colCount = room.getColumns();
        int row = position / colCount;
        int col = position % colCount;

        // Try every unused student
        for (int i = 0; i < students.size(); i++) {

            if (used[i]) {
                continue;
            }

            Student student = students.get(i);

            // Check neighbouring branch constraints
            if (!isSafe(row, col, student.getBranch(), seats, students)) {
                continue;
            }

            // Assign student to current seat
            seats[row][col] = student.getRollNo();
            used[i] = true;

            // Recursively try the next seat
            if (solve(room, students, seats, used, position + 1)) {
                return true;
            }

            // Backtrack: undo this assignment
            seats[row][col] = null;
            used[i] = false;
        }

        return false;
    }

    // Checks the four neighbouring seats
    private boolean isSafe(
            int row,
            int col,
            String branch,
            String[][] seats,
            List<Student> students) {

        // Check above
        if (row > 0 &&
            sameBranch(seats[row - 1][col], branch, students)) {
            return false;
        }

        // Check below
        if (row + 1 < seats.length &&
            sameBranch(seats[row + 1][col], branch, students)) {
            return false;
        }

        // Check left
        if (col > 0 &&
            sameBranch(seats[row][col - 1], branch, students)) {
            return false;
        }

        // Check right
        if (col + 1 < seats[row].length &&
            sameBranch(seats[row][col + 1], branch, students)) {
            return false;
        }

        return true;
    }

    // Finds the branch of an already seated roll number
    private boolean sameBranch(
            String rollNo,
            String branch,
            List<Student> students) {

        if (rollNo == null) {
            return false;
        }

        for (Student student : students) {
            if (student.getRollNo().equals(rollNo)) {
                return student.getBranch().equalsIgnoreCase(branch);
            }
        }

        return false;
    }
}

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    static String projectName = "Exam Seating & Invigilator Allocator";

    static StudentManager studentManager = new StudentManager();
    static SessionManager sessionManager = new SessionManager();
    static SeatAllocator seatAllocator = new SeatAllocator();
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Invigilator> invigilators = new ArrayList<>();

    static InvigilatorAllocator invigilatorAllocator = new InvigilatorAllocator();

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        rooms.addAll(Room.loadRooms());
        try (Connection connection = DatabaseConnection.getConnection()) {

            System.out.println(
                    "MySQL connected successfully!");

        } catch (SQLException e) {

            System.out.println(
                    "Database connection failed!");

            e.printStackTrace();
        }

        System.out.println("==================================");
        System.out.println(projectName);
        System.out.println("==================================");

        int choice = 0;

        do {

            System.out.println("\n===== MAIN MENU =====");

            // ================= ADD =================

            System.out.println("\n----- ADD -----");
            System.out.println("1. Add Student");
            System.out.println("2. Add Room");
            System.out.println("3. Add Invigilator");
            System.out.println("4. Add Exam Session");

            // ================= VIEW =================

            System.out.println("\n----- VIEW -----");
            System.out.println("5. View Students");
            System.out.println("6. View Rooms");
            System.out.println("7. View Invigilators");
            System.out.println("8. View Exam Sessions");
            System.out.println("9. View Overflow Students");

            // ================= OTHER OPERATIONS =================

            System.out.println("\n----- OTHER OPERATIONS -----");
            System.out.println("10. Search Student");
            System.out.println("11. Remove Student");
            System.out.println("12. View Sorted Roll Numbers");
            System.out.println("13. View Unique Branches");
            System.out.println("14. Search Exam Session");
            System.out.println("15. Generate Session Report");

            // ================= ALLOCATION =================

            System.out.println("\n----- ALLOCATION -----");
            System.out.println("16. Allocate Seating");
            System.out.println("17. Display Seating Chart");
            System.out.println("18. Allocate Invigilators");
            System.out.println("19. View Invigilator Allocations");

            // ================= REPORTS =================

            System.out.println("\n----- REPORTS -----");
            System.out.println("20. System Dashboard");
            System.out.println("21. Seating Report");
            System.out.println("22. Invigilator Duty Report");

            System.out.println("\n23. Exit");

            System.out.print("Enter your choice: ");

            // Convert String input into Integer.
            Integer choiceInput;

            try {

                choiceInput = Integer.valueOf(sc.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number!");
                continue;
            }

            choice = choiceInput;

            switch (choice) {

                // ================= ADD =================

                case 1:
                    addStudent();
                    break;

                case 2:
                    addRoom();
                    break;

                case 3:
                    addInvigilator();
                    break;

                case 4:
                    addExamSession();
                    break;

                // ================= VIEW =================

                case 5:
                    studentManager.displayStudents();
                    break;

                case 6:
                    viewRooms();
                    break;

                case 7:
                    viewInvigilators();
                    break;

                case 8:
                    sessionManager.displaySessions();
                    break;

                case 9:
                    studentManager.displayOverflowStudents();
                    break;

                // ================= OTHER OPERATIONS =================

                case 10:
                    searchStudent();
                    break;

                case 11:
                    removeStudent();
                    break;

                case 12:
                    studentManager.displaySortedRollNumbers();
                    break;

                case 13:
                    studentManager.displayBranches();
                    break;

                case 14:
                    searchExamSession();
                    break;

                case 15:
                    System.out.println(sessionManager.generateReport());
                    break;

                // ================= ALLOCATION =================

                case 16:
                    allocateSeating();
                    break;

                case 17:
                    displaySeatingCharts();
                    break;

                case 18:
                    allocateInvigilators();
                    break;

                case 19:
                    invigilatorAllocator.displayAllAssignments();
                    break;

                // ================= REPORTS =================

                case 20:
                    displayDashboard();
                    break;

                case 21:
                    displaySeatingReport();
                    break;

                case 22:
                    displayInvigilatorDutyReport();
                    break;

                // ================= EXIT =================

                case 23:
                    System.out.println("Exiting project...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 23);

        sc.close();
    }

    // Add a student with validation
    static void addStudent() {

        System.out.print("Enter Roll Number: ");
        String rollNo = sc.nextLine().trim();

        if (!InputValidator.isNotEmpty(rollNo)
                || !InputValidator.isValidRollNo(rollNo)) {
            System.out.println("Invalid roll number!");
            return;
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();

        if (!InputValidator.isValidName(name)) {
            System.out.println("Invalid name! Use letters and spaces only.");
            return;
        }

        System.out.print("Enter Branch: ");
        String branch = InputValidator.normalize(sc.nextLine());

        if (!InputValidator.isNotEmpty(branch)) {
            System.out.println("Branch cannot be empty!");
            return;
        }

        System.out.print("Enter Subject: ");
        String subject = sc.nextLine().trim();

        if (!InputValidator.isNotEmpty(subject)) {
            System.out.println("Subject cannot be empty!");
            return;
        }

        Student student = new Student(rollNo, name, branch, subject);

        if (studentManager.addStudent(student)) {
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Duplicate roll number!");
        }
    }

    // Add a room
    static void addRoom() {

        System.out.print("Enter Room ID: ");
        String roomId = sc.nextLine().trim();

        for (Room room : rooms) {
            if (room.getRoomId().equalsIgnoreCase(roomId)) {
                System.out.println("Room ID already exists!");
                return;
            }
        }

        System.out.print("Enter rows: ");
        String rowInput = sc.nextLine();

        System.out.print("Enter columns: ");
        String columnInput = sc.nextLine();

        // Validate numbers before conversion
        if (!InputValidator.isPositiveNumber(rowInput)
                || !InputValidator.isPositiveNumber(columnInput)) {
            System.out.println("Rows and columns must be positive integers!");
            return;
        }

        // Wrapper conversion
        Integer rows = Integer.valueOf(rowInput);
        Integer columns = Integer.valueOf(columnInput);

        Room room = new Room(roomId, rows, columns);

        if (Room.saveRoom(room)) {
            rooms.add(room);
            System.out.println("Room added and saved successfully!");
        } else {
            System.out.println("Room was not added.");
        }
    }

    // Add an invigilator
    static void addInvigilator() {

        System.out.print("Enter name: ");
        String name = sc.nextLine().trim();

        if (!InputValidator.isValidName(name)) {
            System.out.println("Invalid name!");
            return;
        }

        System.out.println("Types: Senior Faculty, Faculty, Lab Assistant");
        System.out.print("Enter type: ");
        String type = sc.nextLine().trim();

        System.out.print("Enter duties completed: ");
        String dutyInput = sc.nextLine();

        if (!dutyInput.matches("\\d+")) {
            System.out.println("Duties must be a number!");
            return;
        }

        Integer duties = Integer.valueOf(dutyInput);

        if (duties < 0) {
            System.out.println("Duties cannot be negative!");
            return;
        }

        Invigilator invigilator = new Invigilator(name, type, duties);

        if (invigilator.getMaxDuties() == 0) {
            System.out.println("Invalid invigilator type!");
            return;
        }

        if (duties > invigilator.getMaxDuties()) {
            System.out.println("Warning: Duties exceed the limit!");
        }

        invigilators.add(invigilator);

        System.out.println("Invigilator added successfully!");
    }

    // Search student
    static void searchStudent() {

        System.out.print("Enter roll number: ");
        String rollNo = sc.nextLine().trim();

        Student student = studentManager.searchStudent(rollNo);

        if (student != null) {
            student.displayStudent();
        } else {
            System.out.println("Student not found.");
        }
    }

    // Remove student
    static void removeStudent() {

        System.out.print("Enter roll number: ");
        String rollNo = sc.nextLine().trim();

        if (studentManager.removeStudent(rollNo)) {
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    // Add exam session
    static void addExamSession() {

        System.out.print("Enter subject: ");
        String subject = sc.nextLine().trim();

        if (!InputValidator.isNotEmpty(subject)) {
            System.out.println("Subject cannot be empty!");
            return;
        }

        System.out.print("Enter date (DD-MM-YYYY): ");
        String date = sc.nextLine().trim();

        System.out.print("Enter time slot: ");
        String timeSlot = sc.nextLine().trim();

        if (!InputValidator.isNotEmpty(date)
                || !InputValidator.isNotEmpty(timeSlot)) {
            System.out.println("Date and time cannot be empty!");
            return;
        }

        ExamSession session = new ExamSession(subject, date, timeSlot);

        if (sessionManager.addSession(session)) {
            System.out.println("Exam session added successfully!");
        } else {
            System.out.println("Duplicate exam session!");
        }
    }

    // Search session by subject
    static void searchExamSession() {

        System.out.print("Enter subject: ");
        String subject = sc.nextLine().trim();

        sessionManager.searchBySubject(subject);
    }

    // Display rooms
    static void viewRooms() {

        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }

        for (Room room : rooms) {
            room.displayRoom();
        }
    }

    // Display invigilators
    static void viewInvigilators() {

        if (invigilators.isEmpty()) {
            System.out.println("No invigilators available.");
            return;
        }

        for (Invigilator invigilator : invigilators) {
            invigilator.displayInvigilator();
        }
    }

    /// Allocates students across available rooms.
    // Allocates seating for one selected exam session.
    static void allocateSeating() {

        // Get all available exam sessions.
        ArrayList<ExamSession> sessions = sessionManager.getSessions();

        if (sessions.isEmpty()) {
            System.out.println("No exam sessions available!");
            return;
        }

        // Display available exam sessions.
        System.out.println("\n===== AVAILABLE EXAM SESSIONS =====");

        for (int i = 0; i < sessions.size(); i++) {

            ExamSession session = sessions.get(i);

            System.out.println(
                    (i + 1) + ". "
                            + session.getSubject()
                            + " | Date: " + session.getDate()
                            + " | Time: " + session.getTimeSlot());
        }

        // Select the exam session.
        System.out.print("\nSelect exam session: ");

        int choice;

        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid session choice!");
            return;
        }

        if (choice < 1 || choice > sessions.size()) {
            System.out.println("Invalid session choice!");
            return;
        }

        ExamSession selectedSession = sessions.get(choice - 1);

        String selectedSubject = selectedSession.getSubject();

        // Get all students.
        ArrayList<Student> allStudents = studentManager.getStudents();

        if (allStudents.isEmpty()) {
            System.out.println("No students available!");
            return;
        }

        if (rooms.isEmpty()) {
            System.out.println("No rooms available!");
            return;
        }

        /*
         * IMPORTANT:
         * Only include students whose subject matches
         * the selected exam session.
         */
        ArrayList<Student> sessionStudents = new ArrayList<>();

        for (Student student : allStudents) {

            if (student.getSubject().equalsIgnoreCase(selectedSubject)) {
                sessionStudents.add(student);
            }
        }

        if (sessionStudents.isEmpty()) {
            System.out.println(
                    "No students registered for "
                            + selectedSubject + " exam!");
            return;
        }

        // Clear previous overflow records.
        studentManager.clearOverflowStudents();

        // Clear old seating arrangements before allocating a new session.
        for (Room room : rooms) {

            String[][] seats = room.getSeats();

            for (int row = 0; row < seats.length; row++) {
                for (int col = 0; col < seats[row].length; col++) {
                    seats[row][col] = null;
                }
            }
        }

        int studentIndex = 0;

        System.out.println(
                "\nAllocating seating for: " + selectedSubject);

        System.out.println(
                "Total students for this exam: " + sessionStudents.size());

        // Allocate only the selected subject's students.
        for (Room room : rooms) {

            if (studentIndex >= sessionStudents.size()) {
                break;
            }

            int groupSize = Math.min(
                    room.getCapacity(),
                    sessionStudents.size() - studentIndex);

            boolean allocated = false;

            // Try the largest group that fits this room.
            while (groupSize > 0) {

                ArrayList<Student> group = new ArrayList<>();

                for (int i = 0; i < groupSize; i++) {
                    group.add(sessionStudents.get(studentIndex + i));
                }

                ArrayList<Student> allocatedStudents = new ArrayList<>(
                        seatAllocator.allocateSeats(room, group));

                if (allocatedStudents.size() == group.size()) {

                    System.out.println(
                            "Students allocated successfully in "
                                    + room.getRoomId());

                    studentIndex += groupSize;
                    allocated = true;
                    break;
                }

                groupSize--;

                groupSize--;
            }

            if (!allocated) {

                System.out.println(
                        "Unable to allocate students in "
                                + room.getRoomId());
            }
        }

        // Add only the selected exam's remaining students to overflow.
        while (studentIndex < sessionStudents.size()) {

            Student student = sessionStudents.get(studentIndex);

            studentManager.addToOverflow(student);

            studentIndex++;
        }

        System.out.println("\n===== SEATING ALLOCATION REPORT =====");

        System.out.println("Exam Subject: " + selectedSubject);

        System.out.println(
                "Total Students: " + sessionStudents.size());

        System.out.println(
                "Successfully Seated: "
                        + (sessionStudents.size()
                                - studentManager.getOverflowCount()));

        System.out.println(
                "Overflow Students: "
                        + studentManager.getOverflowCount());

        System.out.println("\nSeating allocation process completed.");
    }

    // Displays the seating arrangement of all rooms
    static void displaySeatingCharts() {

        if (rooms.isEmpty()) {
            System.out.println("No rooms available!");
            return;
        }

        for (Room room : rooms) {

            System.out.println("\n================================");
            System.out.println("Seating Chart: " + room.getRoomId());
            System.out.println("================================");

            String[][] seats = room.getSeats();

            for (int i = 0; i < seats.length; i++) {

                for (int j = 0; j < seats[i].length; j++) {

                    if (seats[i][j] == null) {
                        System.out.print("[ EMPTY ] ");
                    } else {
                        System.out.print("[ " + seats[i][j] + " ] ");
                    }
                }

                System.out.println();
            }
        }
    }

    // Allocates invigilators for the selected exam session
    static void allocateInvigilators() {

        if (invigilators.isEmpty()) {
            System.out.println("No invigilators available!");
            return;
        }

        if (rooms.isEmpty()) {
            System.out.println("No rooms available!");
            return;
        }

        ArrayList<ExamSession> sessions = sessionManager.getSessions();

        if (sessions.isEmpty()) {
            System.out.println("No exam sessions available!");
            return;
        }

        // Display sessions with index numbers
        System.out.println("\n===== AVAILABLE EXAM SESSIONS =====");

        for (int i = 0; i < sessions.size(); i++) {
            ExamSession session = sessions.get(i);

            System.out.println((i + 1) + ". "
                    + session.getSubject() + " | "
                    + session.getDate() + " | "
                    + session.getTimeSlot());
        }

        System.out.print("Select exam session number: ");

        int sessionChoice;

        try {
            sessionChoice = Integer.valueOf(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid selection!");
            return;
        }

        if (sessionChoice < 1 || sessionChoice > sessions.size()) {
            System.out.println("Invalid session number!");
            return;
        }

        ExamSession selectedSession = sessions.get(sessionChoice - 1);

        AtomicBoolean allocatedAny = new AtomicBoolean(false);

        ArrayList<Thread> allocationThreads = new ArrayList<>();

        // Create one worker thread for each room that has students.
        for (Room room : rooms) {

            String[][] seats = room.getSeats();
            int studentCount = 0;

            // Count students in the room.
            for (int row = 0; row < seats.length; row++) {
                for (int col = 0; col < seats[row].length; col++) {

                    if (seats[row][col] != null) {
                        studentCount++;
                    }
                }
            }

            // Do not create a worker for an empty room.
            if (studentCount == 0) {
                continue;
            }

            final Room roomForTask = room;
            final int studentsForTask = studentCount;

            // PHASE 8: Define the task using Runnable.
            Runnable allocationTask = new Runnable() {

                @Override
                public void run() {

                    boolean success;

                    /*
                     * PHASE 9:
                     * Synchronization prevents multiple threads
                     * from accessing the allocator simultaneously.
                     */
                    synchronized (invigilatorAllocator) {

                        success = invigilatorAllocator.allocate(
                                roomForTask,
                                selectedSession,
                                invigilators,
                                studentsForTask);
                    }

                    // Thread-safe update of the shared result.
                    if (success) {
                        allocatedAny.set(true);
                    }
                }
            };

            // Create a separate worker thread for this room.
            Thread worker = new Thread(
                    allocationTask,
                    "Allocation-" + room.getRoomId());

            // Normal thread priority.
            worker.setPriority(Thread.NORM_PRIORITY);

            // Non-daemon thread.
            worker.setDaemon(false);

            allocationThreads.add(worker);

            // Start the worker thread.
            worker.start();
        }

        /*
         * PHASE 8:
         * The main thread waits until all allocation workers finish.
         */
        for (Thread worker : allocationThreads) {

            try {

                worker.join();

            } catch (InterruptedException e) {

                // Request interruption of all allocation workers.
                for (Thread runningWorker : allocationThreads) {
                    runningWorker.interrupt();
                }

                // Restore the main thread's interrupted status.
                Thread.currentThread().interrupt();

                System.out.println(
                        "Waiting for allocation was interrupted.");

                return;
            }
        }

        // Display the final allocation result.
        if (allocatedAny.get()) {

            System.out.println(
                    "Concurrent allocation process completed.");

        } else {

            System.out.println(
                    "No invigilator allocation was completed.");
        }
    }

    // Displays overall project statistics
    static void displayDashboard() {

        // Count registered students
        int totalStudents = studentManager.getStudents().size();

        // Count registered rooms
        int totalRooms = rooms.size();

        // Calculate total room capacity
        int totalCapacity = 0;

        for (Room room : rooms) {
            totalCapacity += room.getCapacity();
        }

        // Count exam sessions and invigilators
        int totalSessions = sessionManager.getSessions().size();
        int totalInvigilators = invigilators.size();

        // Display dashboard
        System.out.println("\n========== SYSTEM DASHBOARD ==========");

        System.out.printf("%-28s: %d%n",
                "Total Students", totalStudents);

        System.out.printf("%-28s: %d%n",
                "Total Rooms", totalRooms);

        System.out.printf("%-28s: %d%n",
                "Total Room Capacity", totalCapacity);

        System.out.printf("%-28s: %d%n",
                "Total Exam Sessions", totalSessions);

        System.out.printf("%-28s: %d%n",
                "Total Invigilators", totalInvigilators);

        System.out.println("======================================");
    }

    // Displays seating details and statistics for all rooms
    static void displaySeatingReport() {

        if (rooms.isEmpty()) {
            System.out.println("No rooms available!");
            return;
        }

        int totalCapacity = 0;
        int totalSeated = 0;

        System.out.println("\n========== SEATING REPORT ==========");

        for (Room room : rooms) {

            String[][] seats = room.getSeats();

            int occupied = 0;

            // Count occupied seats
            for (int row = 0; row < seats.length; row++) {

                for (int col = 0; col < seats[row].length; col++) {

                    if (seats[row][col] != null) {
                        occupied++;
                    }
                }
            }

            int capacity = room.getCapacity();
            int emptySeats = capacity - occupied;

            totalCapacity += capacity;
            totalSeated += occupied;

            // Display room information
            System.out.println("\n------------------------------------");

            System.out.println("Room ID       : " + room.getRoomId());

            System.out.println("Rows          : " + room.getRows());

            System.out.println("Columns       : " + room.getColumns());

            System.out.println("Capacity      : " + capacity);

            System.out.println("Students      : " + occupied);

            System.out.println("Empty Seats   : " + emptySeats);

            // Display seating chart
            System.out.println("\nSeating Chart:");

            for (int row = 0; row < seats.length; row++) {

                for (int col = 0; col < seats[row].length; col++) {

                    if (seats[row][col] == null) {

                        System.out.printf("[ %-10s ]", "EMPTY");

                    } else {

                        System.out.printf("[ %-10s ]", seats[row][col]);
                    }
                }

                System.out.println();
            }
        }

        // Calculate overall statistics
        int totalStudents = studentManager.getStudentCount();

        int overflowStudents = studentManager.getOverflowCount();

        int emptySeats = totalCapacity - totalSeated;

        System.out.println("\n========== OVERALL SUMMARY ==========");

        System.out.println("Registered Students : " + totalStudents);

        System.out.println("Total Room Capacity : " + totalCapacity);

        System.out.println("Students Seated     : " + totalSeated);

        System.out.println("Empty Seats         : " + emptySeats);

        System.out.println("Overflow Students   : " + overflowStudents);

        // Display overflow student details
        if (overflowStudents > 0) {

            System.out.println("\n========== OVERFLOW DETAILS ==========");

            studentManager.displayOverflowStudents();

        } else {

            System.out.println("\nNo overflow students.");
        }

        System.out.println("======================================");
    }

    // Displays duty information for all invigilators
    static void displayInvigilatorDutyReport() {

        if (invigilators.isEmpty()) {
            System.out.println("No invigilators available!");
            return;
        }

        int totalDuties = 0;
        int totalMaxDuties = 0;
        int totalRemainingDuties = 0;

        System.out.println("\n========== INVIGILATOR DUTY REPORT ==========");

        System.out.printf(
                "%-20s %-20s %-15s %-15s %-15s%n",
                "Name",
                "Type",
                "Completed",
                "Maximum",
                "Remaining");

        System.out.println(
                "--------------------------------------------------------------------------");

        for (Invigilator invigilator : invigilators) {

            int completed = invigilator.getDutiesSoFar();

            int maximum = invigilator.getMaxDuties();

            int remaining = Math.max(0, maximum - completed);

            totalDuties += completed;
            totalMaxDuties += maximum;
            totalRemainingDuties += remaining;

            System.out.printf(
                    "%-20s %-20s %-15d %-15d %-15d%n",
                    invigilator.getName(),
                    invigilator.getType(),
                    completed,
                    maximum,
                    remaining);
        }

        System.out.println(
                "--------------------------------------------------------------------------");

        System.out.println("\n========== OVERALL SUMMARY ==========");

        System.out.println("Total Invigilators      : " + invigilators.size());

        System.out.println("Total Completed Duties : " + totalDuties);

        System.out.println("Total Duty Capacity    : " + totalMaxDuties);

        System.out.println("Remaining Duty Capacity: " + totalRemainingDuties);

        System.out.println("=====================================");
    }

}
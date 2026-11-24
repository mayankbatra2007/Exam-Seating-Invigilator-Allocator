import java.util.ArrayList;
import java.util.Scanner;

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

        System.out.println("==================================");
        System.out.println(projectName);
        System.out.println("==================================");

        int choice = 0;

        do {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Add Student");
            System.out.println("2. Add Room");
            System.out.println("3. Add Invigilator");
            System.out.println("4. View Students");
            System.out.println("5. View Rooms");
            System.out.println("6. View Invigilators");
            System.out.println("7. Search Student");
            System.out.println("8. Remove Student");
            System.out.println("9. View Sorted Roll Numbers");
            System.out.println("10. View Unique Branches");
            System.out.println("11. View Overflow Students");
            System.out.println("12. Add Exam Session");
            System.out.println("13. View Exam Sessions");
            System.out.println("14. Search Exam Session");
            System.out.println("15. Generate Session Report");
            System.out.println("16. Allocate Seating");
            System.out.println("17. Display Seating Chart");
            System.out.println("18. Allocate Invigilators");
            System.out.println("19. View Invigilator Allocations");
            System.out.println("20. System Dashboard");
            System.out.println("21. Exit");

            System.out.print("Enter your choice: ");

            // Wrapper class converts String input into Integer
            Integer choiceInput;

            try {
                choiceInput = Integer.valueOf(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
                continue;
            }

            choice = choiceInput;

            switch (choice) {

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
                    studentManager.displayStudents();
                    break;

                case 5:
                    viewRooms();
                    break;

                case 6:
                    viewInvigilators();
                    break;

                case 7:
                    searchStudent();
                    break;

                case 8:
                    removeStudent();
                    break;

                case 9:
                    studentManager.displaySortedRollNumbers();
                    break;

                case 10:
                    studentManager.displayBranches();
                    break;

                case 11:
                    studentManager.displayOverflowStudents();
                    break;

                case 12:
                    addExamSession();
                    break;

                case 13:
                    sessionManager.displaySessions();
                    break;

                case 14:
                    searchExamSession();
                    break;

                case 15:
                    System.out.println(sessionManager.generateReport());
                    break;

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

                case 20:
                    displayDashboard();
                    break;

                case 21:
                    System.out.println("Exiting project...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 21);

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

        rooms.add(room);

        System.out.println("Room added successfully!");
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

    // Allocates students across available rooms
    static void allocateSeating() {

        ArrayList<Student> allStudents = studentManager.getStudents();

        if (allStudents.isEmpty()) {
            System.out.println("No students available!");
            return;
        }

        if (rooms.isEmpty()) {
            System.out.println("No rooms available!");
            return;
        }

        // Clear previous overflow list before a new allocation
        // (We will improve reset handling in a later integration phase)

        int studentIndex = 0;

        for (Room room : rooms) {

            if (studentIndex >= allStudents.size()) {
                break;
            }

            // Try the largest group that fits this room
            int groupSize = Math.min(room.getCapacity(),
                    allStudents.size() - studentIndex);

            boolean allocated = false;

            // Reduce group size if the seating arrangement fails
            while (groupSize > 0) {

                ArrayList<Student> group = new ArrayList<>();

                for (int i = 0; i < groupSize; i++) {
                    group.add(allStudents.get(studentIndex + i));
                }

                if (seatAllocator.allocateSeats(room, group)) {

                    System.out.println("\nStudents allocated successfully in "
                            + room.getRoomId());

                    studentIndex += groupSize;
                    allocated = true;
                    break;
                }

                groupSize--;
            }

            // If even a single student could not be seated, move to next room
            if (!allocated) {
                System.out.println("Unable to allocate students in "
                        + room.getRoomId());
            }
        }

        // Remaining students are overflow students
        while (studentIndex < allStudents.size()) {

            Student student = allStudents.get(studentIndex);
            studentManager.addToOverflow(student);

            studentIndex++;
        }

        System.out.println("\nSeating allocation process completed.");
        System.out.println("Students processed: " + allStudents.size());
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

        boolean allocatedAny = false;

        // Process each room
        for (Room room : rooms) {

            String[][] seats = room.getSeats();
            int studentCount = 0;

            // Count occupied seats
            for (int i = 0; i < seats.length; i++) {
                for (int j = 0; j < seats[i].length; j++) {
                    if (seats[i][j] != null) {
                        studentCount++;
                    }
                }
            }

            // Skip empty rooms
            if (studentCount == 0) {
                continue;
            }

            boolean success = invigilatorAllocator.allocate(
                    room,
                    selectedSession,
                    invigilators,
                    studentCount
            );

            if (success) {
                allocatedAny = true;
            }
        }

        if (!allocatedAny) {
            System.out.println("No invigilator allocation was completed.");
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



}
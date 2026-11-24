import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Locale;

public class StudentManager {

    // Stores complete student objects.
    private ArrayList<Student> students = new ArrayList<>();

    // Stores students who could not be seated.
    private LinkedList<Student> overflowStudents = new LinkedList<>();

    // Stores unique roll numbers for duplicate checking.
    private HashSet<String> rollNumbers = new HashSet<>();

    // Stores unique branches in insertion order.
    private LinkedHashSet<String> branches = new LinkedHashSet<>();

    // Stores roll numbers in sorted order.
    private TreeSet<String> sortedRollNumbers = new TreeSet<>();

    // Load saved students when the manager is created.
    public StudentManager() {
        loadStudentsFromDatabase();
    }

    // Convert roll number to a consistent format.
    private String normalizeRollNo(String rollNo) {
        return rollNo.trim().toLowerCase(Locale.ROOT);
    }

    // Add a student to MySQL and Java collections.
    public boolean addStudent(Student student) {

        String rollNo = student.getRollNo();

        // Check duplicate roll number in Java.
        if (rollNumbers.contains(normalizeRollNo(rollNo))) {
            System.out.println("Student already exists!");
            return false;
        }

        String sql = "INSERT INTO students "
                   + "(roll_no, name, branch, subject) "
                   + "VALUES (?, ?, ?, ?)";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(sql)
        ) {

            ps.setString(1, student.getRollNo());
            ps.setString(2, student.getName());
            ps.setString(3, student.getBranch());
            ps.setString(4, student.getSubject());

            // Save student in MySQL.
            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {

                // Update Java collections only after DB success.
                students.add(student);

                rollNumbers.add(normalizeRollNo(rollNo));

                branches.add(student.getBranch());

                sortedRollNumbers.add(rollNo);

                System.out.println(
                        "Student saved successfully!"
                );

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Could not save student to database."
            );

            e.printStackTrace();
        }

        return false;
    }

    // Load all students from MySQL.
    public void loadStudentsFromDatabase() {

        String sql = "SELECT roll_no, name, branch, subject "
                   + "FROM students";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery()
        ) {

            // Clear existing collections before loading.
            students.clear();
            rollNumbers.clear();
            branches.clear();
            sortedRollNumbers.clear();

            while (rs.next()) {

                String rollNo = rs.getString("roll_no");
                String name = rs.getString("name");
                String branch = rs.getString("branch");
                String subject = rs.getString("subject");

                Student student = new Student(
                        rollNo,
                        name,
                        branch,
                        subject
                );

                students.add(student);

                rollNumbers.add(
                        normalizeRollNo(rollNo)
                );

                branches.add(branch);

                sortedRollNumbers.add(rollNo);
            }

            System.out.println(
                    "Students loaded from database: "
                            + students.size()
            );

        } catch (SQLException e) {

            System.out.println(
                    "Could not load students from database."
            );

            e.printStackTrace();
        }
    }

    // Search student using roll number.
    public Student searchStudent(String rollNo) {

        for (Student student : students) {

            if (student.getRollNo().equalsIgnoreCase(rollNo)) {
                return student;
            }
        }

        return null;
    }

    // Remove student from MySQL and Java collections.
    public boolean removeStudent(String rollNo) {

        Student student = searchStudent(rollNo);

        if (student == null) {
            return false;
        }

        String sql = "DELETE FROM students WHERE roll_no = ?";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(sql)
        ) {

            ps.setString(1, student.getRollNo());

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {

                students.remove(student);

                rollNumbers.remove(
                        normalizeRollNo(student.getRollNo())
                );

                sortedRollNumbers.remove(
                        student.getRollNo()
                );

                // Rebuild branches because a branch may
                // no longer have any students.
                branches.clear();

                for (Student s : students) {
                    branches.add(s.getBranch());
                }

                System.out.println(
                        "Student removed successfully!"
                );

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Could not remove student from database."
            );

            e.printStackTrace();
        }

        return false;
    }

    // Display all students.
    public void displayStudents() {

        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        for (Student student : students) {
            student.displayStudent();
        }
    }

    // Display student roll numbers in sorted order.
    public void displaySortedRollNumbers() {

        System.out.println("Sorted Roll Numbers:");

        for (String rollNo : sortedRollNumbers) {
            System.out.println(rollNo);
        }
    }

    // Display unique branches in insertion order.
    public void displayBranches() {

        System.out.println("Available Branches:");

        for (String branch : branches) {
            System.out.println(branch);
        }
    }

    // Add a student to overflow list.
    public void addToOverflow(Student student) {
        overflowStudents.add(student);
    }

    // Display overflow students.
    public void displayOverflowStudents() {

        if (overflowStudents.isEmpty()) {
            System.out.println("No overflow students.");
            return;
        }

        System.out.println("Overflow Students:");

        for (Student student : overflowStudents) {
            student.displayStudent();
        }
    }

    // Return number of students.
    public int getStudentCount() {
        return students.size();
    }

    // Return overflow count.
    public int getOverflowCount() {
        return overflowStudents.size();
    }

    // Clear overflow students before a new allocation.
    public void clearOverflowStudents() {
        overflowStudents.clear();
    }

    // Return student list for existing algorithms.
    public ArrayList<Student> getStudents() {
        return students;
    }
}
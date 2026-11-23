// This class will manage our student records using multiple collections.

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class StudentManager {

    // Stores complete student objects
    private ArrayList<Student> students = new ArrayList<>();

    // Stores students who could not be seated
    private LinkedList<Student> overflowStudents = new LinkedList<>();

    // Stores unique roll numbers for duplicate checking
    private HashSet<String> rollNumbers = new HashSet<>();

    // Stores unique branches in insertion order
    private LinkedHashSet<String> branches = new LinkedHashSet<>();

    // Stores roll numbers in sorted order
    private TreeSet<String> sortedRollNumbers = new TreeSet<>();

    // Add a student if roll number is not duplicate
    public boolean addStudent(Student student) {

        String rollNo = student.getRollNo();

        // HashSet checks duplicates efficiently
        if (rollNumbers.contains(rollNo)) {
            return false;
        }

        students.add(student);
        rollNumbers.add(rollNo);
        branches.add(student.getBranch());
        sortedRollNumbers.add(rollNo);

        return true;
    }

    // Search student using roll number
    public Student searchStudent(String rollNo) {

        for (Student student : students) {
            if (student.getRollNo().equalsIgnoreCase(rollNo)) {
                return student;
            }
        }

        return null;
    }

    // Remove student using roll number
    public boolean removeStudent(String rollNo) {

        Student student = searchStudent(rollNo);

        if (student == null) {
            return false;
        }

        students.remove(student);
        rollNumbers.remove(student.getRollNo());
        sortedRollNumbers.remove(student.getRollNo());

        // Rebuild branches because a branch may no longer have students
        branches.clear();

        for (Student s : students) {
            branches.add(s.getBranch());
        }

        return true;
    }

    // Display all students
    public void displayStudents() {

        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        for (Student student : students) {
            student.displayStudent();
        }
    }

    // Display student roll numbers in sorted order
    public void displaySortedRollNumbers() {

        System.out.println("Sorted Roll Numbers:");

        for (String rollNo : sortedRollNumbers) {
            System.out.println(rollNo);
        }
    }

    // Display unique branches in insertion order
    public void displayBranches() {

        System.out.println("Available Branches:");

        for (String branch : branches) {
            System.out.println(branch);
        }
    }

    // Add a student to overflow list
    public void addToOverflow(Student student) {
        overflowStudents.add(student);
    }

    // Display overflow students
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

    // Return number of students
    public int getStudentCount() {
        return students.size();
    }

    // Return overflow count
    public int getOverflowCount() {
        return overflowStudents.size();
    }

    // Return student list for future algorithms
    public ArrayList<Student> getStudents() {
        return students;
    }
}

// ArrayList<Student> -> Purpose: Store all student objects.
// private ArrayList<Student> students = new ArrayList<>();

// LinkedList<Student> -> Maintain students who could not be seated.
// private LinkedList<Student> overflowStudents = new LinkedList<>();

// HashSet<String>
// private HashSet<String> rollNumbers = new HashSet<>();
// Purpose: Prevent duplicate roll numbers.

// LinkedHashSet<String>
// private LinkedHashSet<String> branches = new LinkedHashSet<>();
// Remember: LinkedHashSet maintains insertion order and removes duplicates

// TreeSet<String>
// private TreeSet<String> sortedRollNumbers = new TreeSet<>();
// TreeSet stores unique elements in sorted order.



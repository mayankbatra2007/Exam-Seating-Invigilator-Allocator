// A student stores their roll number, name, branch and subject.
public class Student {

    private String rollNo;
    private String name;
    private String branch;
    private String subject;

    // Constructor initializes a student
    public Student(String rollNo, String name, String branch, String subject) {
        this.rollNo = rollNo;
        this.name = name;
        this.branch = branch;
        this.subject = subject;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getName() {
        return name;
    }

    public String getBranch() {
        return branch;
    }

    public String getSubject() {
        return subject;
    }

    // Display student details
    public void displayStudent() {
        System.out.println("Roll No: " + rollNo
                + " | Name: " + name
                + " | Branch: " + branch
                + " | Subject: " + subject);
    }
}

// Concepts used
// - class — blueprint for Student objects.
// - private — protects data.
// - constructor — initializes object data.
// - this isn't necessary here because I used different parameter names, but we'll use it in our other classes.
// - Getters — allow controlled access to private fields.
// - Method — displayStudent().
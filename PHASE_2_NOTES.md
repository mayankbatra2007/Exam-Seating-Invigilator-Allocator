# Phase 2 – Java Collections Framework

## Objective
In Phase 2, we improved the student management system using the Java Collections Framework. We introduced different collections for storing, searching, sorting and managing records.

## Features Implemented
- Prevented duplicate student roll numbers.
- Implemented student searching using roll number.
- Implemented student deletion.
- Displayed student roll numbers in sorted order.
- Displayed unique branches.
- Added room ID duplicate checking.
- Created a dedicated StudentManager class for student-related operations.

## Collections Used

### ArrayList
Used to store Student objects.

### LinkedList
Created an overflow student list to store students who could not be seated.

### HashSet
Used to store unique roll numbers and prevent duplicate student entries.

### LinkedHashSet
Used to maintain unique branches while preserving insertion order.

### TreeSet
Used to store roll numbers in sorted order.

## Classes Updated and Created

### StudentManager.java
Responsible for managing student records.

Implemented:
- addStudent()
- searchStudent()
- removeStudent()
- displayStudents()
- displaySortedRollNumbers()
- displayBranches()
- addToOverflow()
- displayOverflowStudents()
- getStudentCount()
- getOverflowCount()

### Main.java
Updated the menu to include:
- Search Student
- Remove Student
- View Sorted Roll Numbers
- View Unique Branches
- View Overflow Students

## Java Concepts Used
- Java Collections Framework
- ArrayList
- LinkedList
- HashSet
- LinkedHashSet
- TreeSet
- Iteration using enhanced for loops
- Searching
- Removing elements
- Duplicate checking
- Encapsulation
- Separation of responsibilities using StudentManager

## Project Status
Phase 2 is completed. The student management system now supports duplicate checking, searching, deleting and displaying sorted and unique data.

## Next Phase
Phase 3 – Exam Session Management, String Handling, String Immutability, StringBuilder, StringBuffer, Wrapper Classes and Input Validation.

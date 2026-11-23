# Phase 1 – Project Foundation

## Project Name
**Exam Seating & Invigilator Allocator**

## Objective
In Phase 1, we created the basic foundation of our Java console-based project. The purpose of this phase is to establish the main classes, store basic data and create a menu-driven console application.

## Features Implemented
- Created a menu-driven Java console application.
- Added student management (add and view students).
- Added room management (add and view rooms).
- Added invigilator management (add and view invigilators).
- Created an ExamSession class to represent exam details.
- Implemented basic input validation.
- Stored records using ArrayList.
- Represented room seating using a 2D array.

## Classes Created

### 1. Student.java
Stores student information:
- Roll Number
- Name
- Branch
- Subject

Implemented a constructor, getters and a display method.

### 2. Room.java
Stores room information:
- Room ID
- Number of rows
- Number of columns

Calculates room capacity using rows × columns and initializes a 2D seating array.

### 3. Invigilator.java
Stores invigilator information:
- Name
- Type
- Maximum duties
- Duties completed so far

Duty limits are assigned based on the invigilator type:
- Senior Faculty: 2 duties
- Faculty: 4 duties
- Lab Assistant: 6 duties

### 4. ExamSession.java
Stores examination details:
- Subject
- Exam date
- Time slot

### 5. Main.java
Acts as the main entry point of the application.

Implemented:
- Menu system using switch-case.
- Input handling using Scanner.
- Adding students, rooms and invigilators.
- Displaying stored records.
- Storing objects using ArrayList.

## Java Concepts Used
- Classes and Objects
- Constructors
- `this` keyword
- Encapsulation
- Private data members
- Getter methods
- Static members
- Methods
- ArrayList
- Two-dimensional arrays
- Loops
- Switch-case
- Scanner for input handling
- Basic input validation

## Project Status
Phase 1 is completed. The basic project structure and menu-driven application are ready. Student, room and invigilator records can be entered and displayed.

## Next Phase
**Phase 2 – Data Management using Java Collections Framework**

We will improve record management using ArrayList, LinkedList, HashSet, LinkedHashSet and TreeSet.

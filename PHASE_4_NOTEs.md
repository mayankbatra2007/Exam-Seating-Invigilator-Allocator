# Phase 4 — Smart Seating Allocation

## Objective
Implement an algorithm that assigns students to seats in examination rooms while preventing students from the same branch from sitting directly next to each other.

## Features Implemented
- Seating allocation using a 2D array.
- Room capacity checking.
- Student assignment to seats.
- Adjacent branch checking in four directions:
  - Up
  - Down
  - Left
  - Right
- Backtracking algorithm for exploring seating arrangements.
- Seating chart display.
- Integration with the Main menu.

## Files Created
- `SeatAllocator.java`

## Files Updated
- `Main.java`

## Concepts Learned
- Two-dimensional arrays.
- Recursion.
- Backtracking.
- Boolean arrays.
- Lists and objects.
- Encapsulation.
- Algorithmic constraint checking.
- Menu integration.

## Algorithm
1. Clear the room's previous seating arrangement.
2. Check room capacity.
3. Select a seat.
4. Try an unused student.
5. Check neighboring seats for branch conflicts.
6. If valid, place the student.
7. Recursively attempt the next seat.
8. If a conflict occurs later, undo the placement and try another student.
9. Display the completed seating chart.

## Project Status
Phase 4 completed and tested successfully.

## Next Phase
Phase 5 — Smart Invigilator Allocation using Maps, Queues, PriorityQueue and fairness logic.

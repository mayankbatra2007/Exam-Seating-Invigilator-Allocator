# Phase 5 — Smart Invigilator Allocation

## Objective
Automatically assign eligible invigilators to examination rooms while maintaining duty limits, preventing time-slot conflicts and promoting fair distribution.

## Classes Created / Updated
- `InvigilatorAssignment.java`
- `InvigilatorAllocator.java`
- `Invigilator.java`
- `SessionManager.java`
- `Main.java`

## Rules Implemented
1. Minimum 2 invigilators per room.
2. Additional invigilators based on student count (1 per 25 students, subject to minimum 2).
3. No invigilator can be assigned to multiple rooms during the same date and time slot.
4. Senior Faculty maximum duty limit: 2.
5. Faculty maximum duty limit: 4.
6. Lab Assistant maximum duty limit: 6.
7. Invigilators with fewer completed duties are prioritized.

## Data Structures Used
- `ArrayList` — stores assignments.
- `HashMap` — tracks assigned invigilators by date and time.
- `PriorityQueue` — prioritizes invigilators with fewer duties.
- `Comparator` — compares duty counts.

## Algorithm
1. Calculate the number of required invigilators.
2. Identify eligible invigilators.
3. Exclude invigilators who exceeded duty limits.
4. Exclude invigilators already assigned during the same slot.
5. Select eligible invigilators with the lowest duty counts.
6. Create the assignment record.
7. Update duty counts.
8. Display the assignment.

## Learning Outcomes
- Priority queues
- Comparators
- Greedy algorithms
- HashMap
- Object-oriented programming
- Object relationships
- Constraint-based allocation

## Testing
Phase 5 allocation was tested through the main menu using registered students, rooms, exam sessions and invigilators.

**Status: Completed**

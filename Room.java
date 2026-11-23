// A room has an ID, rows and columns. We'll use a 2D array to represent seats.

public class Room {

    private String roomId;
    private int rows;
    private int columns;

    // 2D array representing the seats
    private String[][] seats;

    public Room(String roomId, int rows, int columns) {
        this.roomId = roomId;
        this.rows = rows;
        this.columns = columns;

        // Create the seating grid
        seats = new String[rows][columns];
    }

    public String[][] getSeats() {
        return seats;
    }

    public String getRoomId() {
        return roomId;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getCapacity() {
        return rows * columns;
    }

    // Display room information
    public void displayRoom() {
        System.out.println("Room ID: " + roomId
                + " | Rows: " + rows
                + " | Columns: " + columns
                + " | Capacity: " + getCapacity());
    }
}

// Concepts used
// - Constructor
// - this
// - Encapsulation
// - Methods
// - 2D array
// - Array initialization
// Remember: A room with 3 rows and 4 columns has:
// [Capacity = 3 * 4 = 12]
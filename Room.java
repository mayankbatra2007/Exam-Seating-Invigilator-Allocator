import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

        seats = new String[rows][columns];
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

    public String[][] getSeats() {
        return seats;
    }

    public int getCapacity() {
        return rows * columns;
    }

    public void displayRoom() {
        System.out.println("Room ID: " + roomId
                + " | Rows: " + rows
                + " | Columns: " + columns
                + " | Capacity: " + getCapacity());
    }

    // Save room details to MySQL
    public static boolean saveRoom(Room room) {

        String sql = "INSERT INTO rooms (room_id, row_count, column_count) "
                   + "VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, room.getRoomId());
            statement.setInt(2, room.getRows());
            statement.setInt(3, room.getColumns());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Could not save room to database.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Load all saved rooms from MySQL
    public static ArrayList<Room> loadRooms() {

        ArrayList<Room> loadedRooms = new ArrayList<>();

        String sql = "SELECT room_id, row_count, column_count FROM rooms";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                String roomId = resultSet.getString("room_id");
                int rows = resultSet.getInt("row_count");
                int columns = resultSet.getInt("column_count");

                loadedRooms.add(new Room(roomId, rows, columns));
            }

        } catch (SQLException e) {
            System.out.println("Could not load rooms from database.");
            System.out.println(e.getMessage());
        }

        return loadedRooms;
    }

    // Delete a room from MySQL
    public static boolean deleteRoom(String roomId) {

        String sql = "DELETE FROM rooms WHERE room_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, roomId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Could not delete room from database.");
            System.out.println(e.getMessage());
            return false;
        }
    }
}
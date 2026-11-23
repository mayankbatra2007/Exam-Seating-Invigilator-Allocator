// This class handles common input validation so we don't repeat the same code everywhere.

public class InputValidator {

    // Validate that text is not empty
    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }

    // Validate roll number format
    public static boolean isValidRollNo(String rollNo) {
        return rollNo.matches("[A-Za-z0-9]+");
    }

    // Validate a person's name
    public static boolean isValidName(String name) {
        return name.matches("[A-Za-z ]+");
    }

    // Validate positive integer
    public static boolean isPositiveNumber(String input) {
        try {
            Integer number = Integer.valueOf(input);
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Normalize text for consistent comparison
    public static String normalize(String text) {
        return text.trim().toUpperCase();
    }
}
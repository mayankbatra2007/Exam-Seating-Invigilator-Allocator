// We automatically assign duty limits according to the type of invigilator.

public class Invigilator {

    private String name;
    private String type;
    private int maxDuties;
    private int dutiesSoFar;

    public Invigilator(String name, String type, int dutiesSoFar) {
        this.name = name;
        this.type = type;
        this.dutiesSoFar = dutiesSoFar;

        // Assign duty limits according to the invigilator type
        if (type.equalsIgnoreCase("Senior Faculty")) {
            this.maxDuties = 2;
        } else if (type.equalsIgnoreCase("Faculty")) {
            this.maxDuties = 4;
        } else if (type.equalsIgnoreCase("Lab Assistant")) {
            this.maxDuties = 6;
        } else {
            this.maxDuties = 0;
        }
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getMaxDuties() {
        return maxDuties;
    }

    public int getDutiesSoFar() {
        return dutiesSoFar;
    }

    // Display invigilator information
    public void displayInvigilator() {
        System.out.println("Name: " + name
                + " | Type: " + type
                + " | Duties: " + dutiesSoFar
                + "/" + maxDuties);
    }

    // Increase duty count when assigned to an exam
    public void incrementDuties() {
        dutiesSoFar++;
    }

}

// Concepts used
// - Constructors
// - this
// - Encapsulation
// - Methods
// - Conditional statements
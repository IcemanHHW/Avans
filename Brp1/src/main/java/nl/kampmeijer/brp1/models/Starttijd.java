package nl.kampmeijer.brp1.models;

public class Starttijd {
    private int id;
    private String starttijd;

    /**
     * Default constructor for creating a new instance of the Starttijd class.
     * Initializes an empty Starttijd object.
     */
    public Starttijd() {

    }

    /**
     * Retrieves the unique identifier of this instance.
     *
     * @return the ID of this instance
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this object.
     *
     * @param id the ID to be assigned to this object
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the starttijd.
     *
     * @return the starttijd as a String
     */
    public String getStarttijd() {
        return starttijd;
    }

    /**
     * Sets the value of the 'starttijd' property.
     *
     * @param starttijd the name to be assigned to the 'starttijd' property
     */
    public void setStarttijd(String starttijd) {
        this.starttijd = starttijd;
    }

    /**
     * Returns the string representation of this instance.
     *
     * @return the value of the "starttijd" field
     */
    @Override
    public String toString() {
        return starttijd;
    }
}

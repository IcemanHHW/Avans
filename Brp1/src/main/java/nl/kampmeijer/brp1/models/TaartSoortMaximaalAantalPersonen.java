package nl.kampmeijer.brp1.models;

public class TaartSoortMaximaalAantalPersonen {

    // Foreign key references
    private int soort_id;
    private int maxpers_id;

    // Resolved values from the database (based on the above IDs)
    private String soort_name;
    private int maxpers_number;

    /**
     * Default constructor for creating a new instance of the TaartSoortMaximaalAantalPersonen class.
     * Initializes an empty TaartSoortMaximaalAantalPersonen object.
     */
    public TaartSoortMaximaalAantalPersonen() {
    }

    /**
     * Returns the ID of the soort
     *
     * @return The soort ID.
     */
    public int getSoort_id() {
        return soort_id;
    }

    /**
     * Sets the ID of the soort
     *
     * @param soort_id The soort ID.
     */
    public void setSoort_id(int soort_id) {
        this.soort_id = soort_id;
    }

    /**
     * Returns the ID of the maximaalaantalpersonen
     *
     * @return The maxpers ID.
     */
    public int getMaxpers_id() {
        return maxpers_id;
    }

    /**
     * Sets the ID of maximaalaantalpersonen
     *
     * @param maxpers_id The maxpers ID.
     */
    public void setMaxpers_id(int maxpers_id) {
        this.maxpers_id = maxpers_id;
    }

    /**
     * Returns the name of the soort as retrieved from the database based on {@code soort_id}.
     *
     * @return The soort name.
     */
    public String getSoort_name() {
        return soort_name;
    }

    /**
     * Sets the name of the soort
     *
     * @param soort_name The soort name.
     */
    public void setSoort_name(String soort_name) {
        this.soort_name = soort_name;
    }

    /**
     * Returns the maximaalaantalpersonen as retrieved from the database based on {@code maxpers_id}.
     *
     * @return The maximum number of persons.
     */
    public int getMaxpers_number() {
        return maxpers_number;
    }

    /**
     * Sets the the maximaalaantalpersonen
     *
     * @param maxpers_number The maximum number of persons.
     */
    public void setMaxpers_number(int maxpers_number) {
        this.maxpers_number = maxpers_number;
    }

    /**
     * Returns a readable summary of this soort and its maximum allowed participants.
     *
     * @return A formatted string containing the soort name and maximum persons count.
     */
    @Override
    public String toString() {
        return soort_name + " " + maxpers_number;
    }
}

package nl.kampmeijer.brp1.models;

public class MaximaalAantalPersonen {
    private int id;
    private int maximumnummer;

    /**
     * Default constructor for creating a new instance of the MaximaalAantalPersonen class.
     * Initializes an empty MaximaalAantalPersonen object.
     */
    public MaximaalAantalPersonen() {

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
     * Retrieves the maximumnummer.
     *
     * @return the maximumnummer as an int
     */
    public int getMaximumnummer() {
        return maximumnummer;
    }

    /**
     * Sets the value of the 'maximumnummer' property.
     *
     * @param maximumnummer the int to the 'maximumnummer' property
     */
    public void setMaximumnummer(int maximumnummer) {
        this.maximumnummer = maximumnummer;
    }

    /**
     * Returns the string representation of this instance.
     *
     * @return the value of the "maximumnummer" field
     */
    @Override
    public String toString() {
        return String.valueOf(maximumnummer);
    }
}

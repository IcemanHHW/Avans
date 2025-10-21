package nl.kampmeijer.fgt1_fysio.classes;

public class Woonplaats {
    private int id;
    private String wpnm;

    /**
     * Default constructor for creating a new instance of the Woonplaats class.
     * Initializes an empty Woonplaats object.
     */
    public Woonplaats() {

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
     * Retrieves the value of the 'wpnm' field.
     *
     * @return the value of the 'wpnm' field as a String
     */
    public String getWpnm() {
        return wpnm;
    }

    /**
     * Sets the value of the 'wpnm' property.
     *
     * @param wpnm the name or string value to be assigned to the 'wpnm' property
     */
    public void setWpnm(String wpnm) {
        this.wpnm = wpnm;
    }

    /**
     * Returns the string representation of this instance.
     *
     * @return the value of the "wpnm" field
     */
    @Override
    public String toString() {
        return wpnm;
    }
}

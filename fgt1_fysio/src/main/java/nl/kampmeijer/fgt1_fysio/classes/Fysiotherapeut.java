package nl.kampmeijer.fgt1_fysio.classes;

public class Fysiotherapeut {
    private int id;
    private String fysnm;

    /**
     * Default constructor for creating a new instance of the Fysiotherapeut class.
     * Initializes an empty Fysiotherapeut object.
     */
    public Fysiotherapeut() {

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
     * Retrieves the name of the physiotherapist.
     *
     * @return the name of the physiotherapist as a String
     */
    public String getFysnm() {
        return fysnm;
    }

    /**
     * Sets the value of the 'fysnm' property.
     *
     * @param fysnm the name to be assigned to the 'fysnm' property
     */
    public void setFysnm(String fysnm) {
        this.fysnm = fysnm;
    }

    /**
     * Returns the string representation of this instance.
     *
     * @return the value of the "fysnm" field
     */
    @Override
    public String toString() {
        return fysnm;
    }
}

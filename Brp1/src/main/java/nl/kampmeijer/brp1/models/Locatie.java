package nl.kampmeijer.brp1.models;

public class Locatie {
    private int id;
    private String locatienaam;

    /**
     * Default constructor for creating a new instance of the Locatie class.
     * Initializes an empty Locatie object.
     */
    public Locatie() {

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
     * Retrieves the locatienaam.
     *
     * @return the locatienaam as a String
     */
    public String getLocatienaam() {
        return locatienaam;
    }

    /**
     * Sets the value of the 'locatienaam' property.
     *
     * @param locatienaam the name to be assigned to the 'locatienaam' property
     */
    public void setLocatienaam(String locatienaam) {
        this.locatienaam = locatienaam;
    }

    /**
     * Returns the string representation of this instance.
     *
     * @return the value of the "locatienaam" field
     */
    @Override
    public String toString() {
        return locatienaam;
    }
}

package nl.kampmeijer.brp1.models;

public class Soort {
    private int id;
    private String soortnaam;

    /**
     * Default constructor for creating a new instance of the Soort class.
     * Initializes an empty Soort object.
     */
    public Soort() {

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
     * Retrieves the soortnaam.
     *
     * @return the soortnaam as a String
     */
    public String getSoortnaam() {
        return soortnaam;
    }

    /**
     * Sets the value of the 'soortnaam' property.
     *
     * @param soortnaam the name 'soortnaam' property
     */
    public void setSoortnaam(String soortnaam) {
        this.soortnaam = soortnaam;
    }

    /**
     * Returns the string representation of this instance.
     *
     * @return the value of the "soortnaam" field
     */
    @Override
    public String toString() {
        return soortnaam;
    }
}

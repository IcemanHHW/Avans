package nl.kampmeijer.fgt1_fysio.classes;

public class Adres {
    private int id;
    private String anm;

    /**
     * Default constructor for creating a new instance of the Adres class.
     * Initializes an empty Adres object.
     */
    public Adres() {

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
     * Retrieves the name associated with the address.
     *
     * @return the name of the address
     */
    public String getAnm() {
        return anm;
    }

    /**
     * Sets the value of the 'anm' property.
     *
     * @param anm the name or string value to be assigned to the 'anm' property
     */
    public void setAnm(String anm) {
        this.anm = anm;
    }

    /**
     * Returns the string representation of this object.
     *
     * @return the string representation of the "anm" field
     */
    @Override
    public String toString() {
        return anm;
    }
}

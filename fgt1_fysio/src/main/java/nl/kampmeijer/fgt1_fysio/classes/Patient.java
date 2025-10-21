package nl.kampmeijer.fgt1_fysio.classes;

public class Patient {
    private int id;
    private String pnm;

    /**
     * Default constructor for creating a new instance of the Patient class.
     * Initializes an empty Patient object.
     */
    public Patient() {

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
     * Retrieves the value of the 'pnm' field.
     *
     * @return the name or string value associated with the patient
     */
    public String getPnm() {
        return pnm;
    }

    /**
     * Sets the value of the 'pnm' property.
     *
     * @param pnm the name to be assigned to the 'pnm' property
     */
    public void setPnm(String pnm) {
        this.pnm = pnm;
    }

    /**
     * Returns the string representation of this instance.
     *
     * @return the value of the "pnm" field
     */
    @Override
    public String toString() {
        return pnm;
    }
}

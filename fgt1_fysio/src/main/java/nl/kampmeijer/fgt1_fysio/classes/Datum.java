package nl.kampmeijer.fgt1_fysio.classes;

public class Datum {
    private int id;
    private String dtm;

    /**
     * Default constructor for creating a new instance of the Datum class.
     * Initializes an empty Datum object.
     */
    public Datum() {

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
     * Retrieves the value of the 'dtm' field.
     *
     * @return the value of the 'dtm' field as a String
     */
    public String getDtm() {
        return dtm;
    }

    /**
     * Sets the value of the 'dtm' property.
     *
     * @param dtm the date or string value to be assigned to the 'dtm' property
     */
    public void setDtm(String dtm) {
        this.dtm = dtm;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return the string value of the "dtm" field
     */
    @Override
    public String toString() {
        return dtm;
    }
}

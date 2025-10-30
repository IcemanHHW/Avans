package nl.kampmeijer.brp1.models;

public class Datum {
    private int id;
    private String datum;

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
     * Retrieves the datum.
     *
     * @return the datum as a String
     */
    public String getDatum() {
        return datum;
    }

    /**
     * Sets the value of the 'datum' property.
     *
     * @param datum the date to be assigned to the 'datum' property
     */
    public void setDatum(String datum) {
        this.datum = datum;
    }

    /**
     * Returns the string representation of this instance.
     *
     * @return the value of the "datum" field
     */
    @Override
    public String toString() {
        return datum;
    }
}

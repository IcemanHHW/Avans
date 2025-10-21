package nl.kampmeijer.fgt1_fysio.classes;

public class Specialisatie {
    private int id;
    private String spec;

    /**
     * Default constructor for creating a new instance of the Specialisatie class.
     * Initializes an empty Specialisatie object.
     */
    public Specialisatie() {
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
     * Retrieves the value of the 'spec' field.
     *
     * @return the specialization value as a String
     */
    public String getSpec() {
        return spec;
    }

    /**
     * Sets the value of the 'spec' property.
     *
     * @param spec the specialization to be assigned to the 'spec' property
     */
    public void setSpec(String spec) {
        this.spec = spec;
    }

    /**
     * Returns the string representation of this instance.
     *
     * @return the value of the "spec" field
     */
    @Override
    public String toString() {
        return spec;
    }
}

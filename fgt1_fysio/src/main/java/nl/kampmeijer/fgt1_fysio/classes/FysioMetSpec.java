package nl.kampmeijer.fgt1_fysio.classes;

public class FysioMetSpec {
    private int fysio_id;
    private int spec_id;

    /**
     * The name of the fysiotherapeut associated with this record.
     * This value is retrieved from the <strong>fysiotherapeuten</strong> table
     * using the {@code fysio_id}. It provides a readable name for display
     * purposes instead of showing only the ID.
     */
    private String fysio_name;

    /**
     * The type of specialisatie linked to the fysiotherapeut.
     * This value is retrieved from the <strong>specialiteiten</strong> table
     * using the {@code spec_id}. It provides a readable name for display
     * purposes instead of showing only the ID.
     */
    private String spec_type;

    /**
     * Default constructor for creating a new instance of the FysioMetSpec class.
     * Initializes an empty FysioMetSpec object.
     */
    public FysioMetSpec() {

    }

    /**
     * Retrieves the unique identifier of the fysiotherapist.
     *
     * @return the ID of the fysiotherapist
     */
    public int getFysio_id() {
        return fysio_id;
    }

    /**
     * Sets the fysiotherapist ID for this object.
     *
     * @param fysio_id the ID of the fysiotherapist to be assigned
     */
    public void setFysio_id(int fysio_id) {
        this.fysio_id = fysio_id;
    }

    /**
     * Retrieves the unique identifier for the specialisatie.
     *
     * @return the specialisatie ID
     */
    public int getSpec_id() {
        return spec_id;
    }

    /**
     * Sets the specialisatie ID for this object.
     *
     * @param spec_id the specialisatie ID to be assigned
     */
    public void setSpec_id(int spec_id) {
        this.spec_id = spec_id;
    }

    /**
     * Retrieves the specialisatie type associated with this instance.
     *
     * @return the specialisatie as a String
     */
    public String getSpec_type() {
        return spec_type;
    }

    /**
     * Sets the specialisatie for this object.
     *
     * @param spec_type the specialisatie to be assigned as a String
     */
    public void setSpec_type(String spec_type) {
        this.spec_type = spec_type;
    }

    /**
     * Retrieves the value of the 'fysio_name' field.
     *
     * @return the name of the fysiotherapeut as a String
     */
    public String getFysio_name() {
        return fysio_name;
    }

    /**
     * Sets the name of the fysiotherapeut.
     *
     * @param fysio_name the name to be assigned to the fysiotherapeut
     */
    public void setFysio_name(String fysio_name) {
        this.fysio_name = fysio_name;
    }

    /**
     * Returns a string representation of this object, combining the fysiotherapeut's name
     * and the specialization type.
     *
     * @return a string that consists of the fysiotherapeut's name and the specialisatie, separated by a space
     */
    @Override
    public String toString() {
        return fysio_name + " " +  spec_type;
    }
}

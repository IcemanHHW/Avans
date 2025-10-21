package nl.kampmeijer.fgt1_fysio.classes;

public class FysioAfspraak {
    private int fysio_id;
    private int patient_id;
    private int datum_id;

    /**
     * The name of the fysiotherapeut associated with this record.
     * This value is retrieved from the <strong>fysiotherapeuten</strong> table
     * using the {@code fysio_id}. It provides a readable name for display
     * purposes instead of showing only the ID.
     */
    private String fysio_name;

    /**
     * The name of the patient associated with this record.
     * This value is retrieved from the <strong>patienten</strong> table
     * using the {@code patient_id}. It provides the full name of the patient
     * instead of displaying only the ID.
     */
    private String patient_name;

    /**
     * The date associated with this record.
     * This value is retrieved from the <strong>datums</strong> table
     * using the {@code datum_id}. It provides the full date
     * instead of displaying only the ID.
     */
    private String datum_date;

    /**
     * Default constructor for creating a new instance of the FysioAfspraak class.
     * Initializes an empty FysioAfspraak object.
     */
    public FysioAfspraak() {

    }

    /**
     * Retrieves the unique identifier for the fysiotherapeut associated with this instance.
     *
     * @return the ID of the fysiotherapeut
     */
    public int getFysio_id() {
        return fysio_id;
    }

    /**
     * Sets the unique identifier for the fysiotherapeut associated with this instance.
     *
     * @param fysio_id the ID to be assigned to the fysiotherapeut
     */
    public void setFysio_id(int fysio_id) {
        this.fysio_id = fysio_id;
    }

    /**
     *
     */
    public int getPatient_id() {
        return patient_id;
    }

    /**
     *
     */
    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    /**
     * Retrieves the identifier for the datum associated with this instance.
     *
     * @return the datum ID as an integer
     */
    public int getDatum_id() {
        return datum_id;
    }

    /**
     *
     */
    public void setDatum_id(int datum_id) {
        this.datum_id = datum_id;
    }

    /**
     *
     */
    public String getFysio_name() {
        return fysio_name;
    }

    /**
     * Sets the name of the fysiotherapeut.
     *
     * @param fysio_name the name of the fysiotherapeut to be assigned
     */
    public void setFysio_name(String fysio_name) {
        this.fysio_name = fysio_name;
    }

    /**
     * Retrieves the name of the patient associated with this instance.
     *
     * @return the patient's name as a String
     */
    public String getPatient_name() {
        return patient_name;
    }

    /**
     * Sets the name of the patient.
     *
     * @param patient_name the name of the patient to be assigned
     */
    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    /**
     *
     */
    public String getDatum_date() {
        return datum_date;
    }

    /**
     * Sets the value of the 'datum_date' property.
     *
     * @param datum_date the date or string value to be assigned to the 'datum_date' property
     */
    public void setDatum_date(String datum_date) {
        this.datum_date = datum_date;
    }

    /**
     * Generates a string representation of the FysioAfspraak object.
     * The representation includes the fysiotherapeut's name, the patient's name, and the date.
     *
     * @return a concatenated string of fysio_name, patient_name, and datum_date separated by spaces
     */
    @Override
    public String toString() {
        return fysio_name + " " +  patient_name + " " +  datum_date;
    }
}

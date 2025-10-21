package nl.kampmeijer.fgt1_fysio.classes;

public class PatientAdresGegevens {
    private int patient_id;
    private int adres_id;
    private int woonplaats_id;

    /**
     * The name of the patient associated with this record.
     * This value is retrieved from the <strong>patienten</strong> table
     * using the {@code patient_id}. It provides the full name of the patient
     * instead of displaying only the ID.
     */
    private String patient_name;

    /**
     * The name of the address linked to the patient.
     * This value is retrieved from the <strong>adressen</strong> table
     * using the {@code adres_id}. It represents the address information
     * (such as street name and number) of the patient.
     */
    private String adres_name;

    /**
     * The name of the woonplaats (city or town) associated with the address.
     * This value is retrieved from the <strong>woonplaatsen</strong> table
     * using the {@code woonplaats_id}. It represents the city or town where
     * the patient lives.
     */
    private String woonplaats_name;

    /**
     * Default constructor for the PatientAdresGegevens class.
     * Initializes a new instance of the PatientAdresGegevens class with no parameters.
     */
    public PatientAdresGegevens() {

    }

    /**
     * Retrieves the unique identifier of the patient.
     *
     * @return the patient ID as an integer
     */
    public int getPatient_id() {
        return patient_id;
    }

    /**
     * Sets the unique identifier for the patient associated with this instance.
     *
     * @param patient_id the ID of the patient to be assigned
     */
    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    /**
     * Retrieves the unique identifier of the address associated with the patient.
     *
     * @return the address ID as an integer
     */
    public int getAdres_id() {
        return adres_id;
    }

    /**
     * Sets the unique identifier for the address associated with this instance.
     *
     * @param adres_id the ID of the address to be assigned
     */
    public void setAdres_id(int adres_id) {
        this.adres_id = adres_id;
    }

    /**
     * Retrieves the unique identifier of the current woonplaats.
     *
     * @return the woonplaats_id as an integer
     */
    public int getWoonplaats_id() {
        return woonplaats_id;
    }

    /**
     * Sets the unique identifier for the woonplaats associated with this instance.
     *
     * @param woonplaats_id the ID of the woonplaats to be assigned
     */
    public void setWoonplaats_id(int woonplaats_id) {
        this.woonplaats_id = woonplaats_id;
    }

    /**
     * Retrieves the name of the patient.
     *
     * @return the patient's name as a String
     */
    public String getPatient_name() {
        return patient_name;
    }

    /**
     * Sets the name of the patient.
     *
     * @param patient_name the name to be assigned to the patient
     */
    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    /**
     * Retrieves the name associated with the address.
     *
     * @return the name of the address as a String
     */
    public String getAdres_name() {
        return adres_name;
    }

    /**
     * Sets the name associated with the address.
     *
     * @param adres_name the name of the address to be assigned
     */
    public void setAdres_name(String adres_name) {
        this.adres_name = adres_name;
    }

    /**
     * Retrieves the name of the woonplaats associated with this object.
     *
     * @return the name of the woonplaats as a String
     */
    public String getWoonplaats_name() {
        return woonplaats_name;
    }

    /**
     * Sets the value of the 'woonplaats_name' property.
     *
     * @param woonplaats_name the name of the woonplaats to be assigned to this object
     */
    public void setWoonplaats_name(String woonplaats_name) {
        this.woonplaats_name = woonplaats_name;
    }

    /**
     * Returns a string representation of the object, which includes the patient's name,
     * address name, and woonplaats name, separated by spaces.
     *
     * @return a concatenated string containing the patient's name, address name,
     * and woonplaats name
     */
    @Override
    public String toString() {
        return patient_name + " " +  adres_name + " " +  woonplaats_name;
    }
}

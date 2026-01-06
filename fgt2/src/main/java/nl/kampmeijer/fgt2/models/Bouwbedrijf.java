package nl.kampmeijer.fgt2.models;

public class Bouwbedrijf {
    private String bwbrfnm;

    /**
     * Default constructor for creating a new instance of the Bouwbedrijf class.
     * Initializes an empty Bouwbedrijf object.
     */
    public Bouwbedrijf() {

    }

    /**
     * Retrieves the bwbrfnm.
     *
     * @return the bwbrfnm as a String
     */
    public String getBwbrfnm() {
        return bwbrfnm;
    }

    /**
     * Sets the value of the 'bwbrfnm' property.
     *
     * @param bwbrfnm the name to be assigned to the 'bwbrfnm' property
     */
    public void setBwbrfnm(String bwbrfnm) {
        this.bwbrfnm = bwbrfnm;
    }

    /**
     * Returns the string representation of this instance.
     *
     * @return the value of the "bwbrfnm" field
     */
    @Override
    public String toString() {
        return bwbrfnm;
    }
}

package nl.kampmeijer.fgt2.models;

public class Medewerker extends Persoon{
    private String bwbrfnm;

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
     * @return the values of the "nm" and "bwbrfnm" fields
     */
    @Override
    public String infoPersoon() {
        return nm + "--" + bwbrfnm;
    }
}

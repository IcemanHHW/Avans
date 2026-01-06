package nl.kampmeijer.fgt2.models;

public class Persoon {
    protected String nm, bsn;

    /**
     * Retrieves the nm.
     *
     * @return the nm as a String
     */
    public String getNm() {
        return nm;
    }

    /**
     * Sets the value of the 'nm' property.
     *
     * @param nm the name to be assigned to the 'nm' property
     */
    public void setNm(String nm) {
        this.nm = nm;
    }

    /**
     * Retrieves the bsn.
     *
     * @return the bsn as a String
     */
    public String getBsn() {
        return bsn;
    }

    /**
     * Sets the value of the 'bsn' property.
     *
     * @param bsn the BSN value to be assigned to the 'bsn' property
     */
    public void setBsn(String bsn) {
        this.bsn = bsn;
    }

    /**
     * Returns the string representation of this instance.
     *
     * @return the value of the "nm" field
     */
    public String infoPersoon() {
        return nm;
    }
}

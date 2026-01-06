package nl.kampmeijer.fgt2.models;

public class Bewoner extends Persoon {
    private String pprtmntnr, vrdpng, wntrnnm;

    /**
     * Retrieves the pprtmntnr.
     *
     * @return the pprtmntnr as a String
     */
    public String getPprtmntnr() {
        return pprtmntnr;
    }

    /**
     * Sets the value of the 'pprtmntnr' property.
     *
     * @param pprtmntnr the number to be assigned to the 'pprtmntnr' property
     */
    public void setPprtmntnr(String pprtmntnr) {
        this.pprtmntnr = pprtmntnr;
    }

    /**
     * Retrieves the vrdpng.
     *
     * @return the vrdpng as a String
     */
    public String getVrdpng() {
        return vrdpng;
    }

    /**
     * Sets the value of the 'vrdpng' property.
     *
     * @param vrdpng the number to be assigned to the 'vrdpng' property
     */
    public void setVrdpng(String vrdpng) {
        this.vrdpng = vrdpng;
    }

    /**
     * Retrieves the wntrnnm.
     *
     * @return the wntrnnm as a String
     */
    public String getWntrnnm() {
        return wntrnnm;
    }

    /**
     * Sets the value of the 'wntrnnm' property.
     *
     * @param wntrnnm the name to be assigned to the 'wntrnnm' property
     */
    public void setWntrnnm(String wntrnnm) {
        this.wntrnnm = wntrnnm;
    }


    /**
     * Returns the string representation of this instance.
     *
     * @return the value of the "nm" field
     */
    @Override
    public String infoPersoon() {
       return super.infoPersoon();
    }
}

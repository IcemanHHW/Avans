package nl.kampmeijer.fgt2.models;

public class Melding {
    private String bsnb, bsnw, dtm, tdstp, wntrnnm;

    public Melding() {

    }

    /**
     * Retrieves the bsnb.
     *
     * @return the bsnb as a String
     */
    public String getBsnb() {
        return bsnb;
    }

    /**
     * Sets the value of the 'bsnb' property.
     *
     * @param bsnb the BSN value to be assigned to the 'bsnb' property
     */
    public void setBsnb(String bsnb) {
        this.bsnb = bsnb;
    }

    /**
     * Retrieves the bsnw.
     *
     * @return the bsnw as a String
     */
    public String getBsnw() {
        return bsnw;
    }

    /**
     * Sets the value of the 'bsnw' property.
     *
     * @param bsnw the BSN value to be assigned to the 'bsnw' property
     */
    public void setBsnw(String bsnw) {
        this.bsnw = bsnw;
    }

    /**
     * Retrieves the dtm.
     *
     * @return the dtm as a String
     */
    public String getDtm() {
        return dtm;
    }

    /**
     * Sets the value of the 'dtm' property.
     *
     * @param dtm the date to be assigned to the 'dtm' property
     */
    public void setDtm(String dtm) {
        this.dtm = dtm;
    }

    /**
     * Retrieves the tdstp.
     *
     * @return the tdstp as a String
     */
    public String getTdstp() {
        return tdstp;
    }

    /**
     * Sets the value of the 'tdstp' property.
     *
     * @param tdstp the timestamp to be assigned to the 'tdstp' property
     */
    public void setTdstp(String tdstp) {
        this.tdstp = tdstp;
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
     * Returns a readable summary of this registration,
     * combining the date, time and woontoren fields into a single string.
     *
     * @return A formatted string representing this registration.
     */
    @Override
    public String toString() {
        return dtm + " " + tdstp + " " + wntrnnm;
    }
}

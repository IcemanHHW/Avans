package nl.kampmeijer.fgt2.models;

public class Woontoren {
    private String wntrnnm;

    /**
     * Default constructor for creating a new instance of the Woontoren class.
     * Initializes an empty Woontoren object.
     */
    public Woontoren(){
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
     * @return the value of the "wntrnnm" field
     */
    @Override
    public String toString() {
        return wntrnnm;
    }
}

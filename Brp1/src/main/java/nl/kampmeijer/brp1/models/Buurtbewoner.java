package nl.kampmeijer.brp1.models;

public class Buurtbewoner {
    private int id;
    private String buurtbewonernaam;

    /**
     * Default constructor for creating a new instance of the Buurtbewoner class.
     * Initializes an empty Buurtbewoner object.
     */
    public Buurtbewoner(){

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
     * Retrieves the buurtbewonernaam.
     *
     * @return buurtbewonernaam as a String
     */
    public String getBuurtbewonernaam() {
        return buurtbewonernaam;
    }

    /**
     * Sets the value of the 'buurtbewonernaam' property.
     *
     * @param buurtbewonernaam the name to be assigned to the 'buurtbewonernaam' property
     */
    public void setBuurtbewonernaam(String buurtbewonernaam) {
        this.buurtbewonernaam = buurtbewonernaam;
    }

    /**
     * Returns the string representation of this instance.
     *
     * @return the value of the "buurtbewonernaam" field
     */
    @Override
    public String toString() {
        return buurtbewonernaam;
    }
}

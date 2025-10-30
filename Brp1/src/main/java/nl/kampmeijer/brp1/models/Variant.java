package nl.kampmeijer.brp1.models;

public class Variant {
    private int id;
    private String variantnaam;

    /**
     * Default constructor for creating a new instance of the Variant class.
     * Initializes an empty Variant object.
     */
    public Variant() {

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
     * Retrieves the variantnaam.
     *
     * @return the variantnaam as a String
     */
    public String getVariantnaam() {
        return variantnaam;
    }

    /**
     * Sets the value of the 'variantnaam' property.
     *
     * @param variantnaam the name to be assigned to the 'variantnaam' property
     */
    public void setVariantnaam(String variantnaam) {
        this.variantnaam = variantnaam;
    }

    /**
     * Returns the string representation of this instance.
     *
     * @return the value of the "variantnaam" field
     */
    @Override
    public String toString() {
        return variantnaam;
    }
}

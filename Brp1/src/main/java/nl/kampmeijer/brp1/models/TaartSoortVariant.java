package nl.kampmeijer.brp1.models;

public class TaartSoortVariant {

    // Foreign key references
    private int soort_id;
    private int variant_id;

    // Resolved names from the database (based on the above IDs)
    private String soort_name;
    private String variant_name;

    /**
     * Default constructor for creating a new instance of the TaartSoortVariant class.
     * Initializes an empty TaartSoortVariant object.
     */
    public TaartSoortVariant() {
    }

    /**
     * Returns the ID of the soort
     *
     * @return The soort ID.
     */
    public int getSoort_id() {
        return soort_id;
    }

    /**
     * Sets the ID of the soort
     *
     * @param soort_id The soort ID.
     */
    public void setSoort_id(int soort_id) {
        this.soort_id = soort_id;
    }

    /**
     * Returns the ID of the variant
     *
     * @return The variant ID.
     */
    public int getVariant_id() {
        return variant_id;
    }

    /**
     * Sets the ID of the variant
     *
     * @param variant_id The variant ID.
     */
    public void setVariant_id(int variant_id) {
        this.variant_id = variant_id;
    }

    /**
     * Returns the name of the soort as retrieved from the database based on {@code soort_id}.
     *
     * @return The soort name.
     */
    public String getSoort_name() {
        return soort_name;
    }

    /**
     * Sets the name of the soort
     *
     * @param soort_name The soort name.
     */
    public void setSoort_name(String soort_name) {
        this.soort_name = soort_name;
    }

    /**
     * Returns the name of the variant as retrieved from the database based on {@code variant_id}.
     *
     * @return The variant name.
     */
    public String getVariant_name() {
        return variant_name;
    }

    /**
     * Sets the name of the variant
     *
     * @param variant_name The variant name.
     */
    public void setVariant_name(String variant_name) {
        this.variant_name = variant_name;
    }

    /**
     * Returns a readable summary of this soort and its variant.
     *
     * @return A formatted string containing the soort and variant names.
     */
    @Override
    public String toString() {
        return soort_name + " " + variant_name;
    }
}

package nl.kampmeijer.brp1.models;

public class InschrijvingBuurtbewoner {

    // Foreign key references
    private int buurtbewoner_id;
    private int soort_id;
    private int variant_id;
    private int datum_id;
    private int starttijd_id;
    private int locatie_id;

    // Resolved names and values from the database (based on the above IDs)
    private String buurtbewoner_name;
    private String soort_name;
    private String variant_name;
    private String datum_date;
    private String starttijd_time;
    private String locatie_name;

    /**
     * Default constructor for creating a new instance of the InschrijvingBuurtbewoner class.
     * Initializes an empty InschrijvingBuurtbewoner object.
     */
    public InschrijvingBuurtbewoner() {

    }

    /**
     * Returns the ID of the registered buurtbewoner
     *
     * @return The buurtbewoner ID.
     */
    public int getBuurtbewoner_id() {
        return buurtbewoner_id;
    }

    /**
     * Sets the ID of the registered buurtbewoner
     *
     * @param buurtbewoner_id The buurtbewoner ID.
     */
    public void setBuurtbewoner_id(int buurtbewoner_id) {
        this.buurtbewoner_id = buurtbewoner_id;
    }

    /**
     * Returns the ID of the selected soort
     *
     * @return The soort ID.
     */
    public int getSoort_id() {
        return soort_id;
    }

    /**
     * Sets the ID of the selected soort
     *
     * @param soort_id The soort ID.
     */
    public void setSoort_id(int soort_id) {
        this.soort_id = soort_id;
    }

    /**
     * Returns the ID of the selected variant
     *
     * @return The variant ID.
     */
    public int getVariant_id() {
        return variant_id;
    }

    /**
     * Sets the ID of the selected variant
     *
     * @param variant_id The variant ID.
     */
    public void setVariant_id(int variant_id) {
        this.variant_id = variant_id;
    }

    /**
     * Returns the ID of the selected datum
     *
     * @return The datum ID.
     */
    public int getDatum_id() {
        return datum_id;
    }

    /**
     * Sets the ID of the selected datum
     *
     * @param datum_id The datum ID.
     */
    public void setDatum_id(int datum_id) {
        this.datum_id = datum_id;
    }

    /**
     * Returns the ID of the selected starttijd
     *
     * @return The starttijd ID.
     */
    public int getStarttijd_id() {
        return starttijd_id;
    }

    /**
     * Sets the ID of the selected starttijd
     *
     * @param starttijd_id The starttijd ID.
     */
    public void setStarttijd_id(int starttijd_id) {
        this.starttijd_id = starttijd_id;
    }

    /**
     * Returns the ID of the selected locatie
     *
     * @return The locatie ID.
     */
    public int getLocatie_id() {
        return locatie_id;
    }

    /**
     * Sets the ID of the selected locatie
     *
     * @param locatie_id The locatie ID.
     */
    public void setLocatie_id(int locatie_id) {
        this.locatie_id = locatie_id;
    }

    /**
     * Returns the name of the buurtbewoner as retrieved from the database based on {@code buurtbewoner_id}.
     *
     * @return The buurtbewoner's name.
     */
    public String getBuurtbewoner_name() {
        return buurtbewoner_name;
    }

    /**
     * Sets the name of the buurtbewoner
     *
     * @param buurtbewoner_name The buurtbewoner's name.
     */
    public void setBuurtbewoner_name(String buurtbewoner_name) {
        this.buurtbewoner_name = buurtbewoner_name;
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
     * Sets the name of the soort .
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
     * Sets the name of the variant.
     *
     * @param variant_name The variant name.
     */
    public void setVariant_name(String variant_name) {
        this.variant_name = variant_name;
    }

    /**
     * Returns the formatted date string (datum), as retrieved from the database based on {@code datum_id}.
     *
     * @return The date as a formatted string.
     */
    public String getDatum_date() {
        return datum_date;
    }

    /**
     * Sets the formatted date string (datum).
     *
     * @param datum_date The date as a formatted string.
     */
    public void setDatum_date(String datum_date) {
        this.datum_date = datum_date;
    }

    /**
     * Returns the formatted start time string as retrieved from the database based on {@code starttijd_id}.
     *
     * @return The start time as a formatted string.
     */
    public String getStarttijd_time() {
        return starttijd_time;
    }

    /**
     * Sets the formatted start time string.
     *
     * @param starttijd_time The start time as a formatted string.
     */
    public void setStarttijd_time(String starttijd_time) {
        this.starttijd_time = starttijd_time;
    }

    /**
     * Returns the name of the locatie as retrieved from the database based on {@code locatie_id}.
     *
     * @return The locatie name.
     */
    public String getLocatie_name() {
        return locatie_name;
    }

    /**
     * Sets the name of the locatie.
     *
     * @param locatie_name The locatie name.
     */
    public void setLocatie_name(String locatie_name) {
        this.locatie_name = locatie_name;
    }

    /**
     * Returns a readable summary of this registration,
     * combining all descriptive fields into a single string.
     *
     * @return A formatted string representing this registration.
     */
    @Override
    public String toString() {
        return buurtbewoner_name + " " + soort_name + " " + variant_name + " " +  datum_date + " " + starttijd_time + " " + locatie_name;
    }
}

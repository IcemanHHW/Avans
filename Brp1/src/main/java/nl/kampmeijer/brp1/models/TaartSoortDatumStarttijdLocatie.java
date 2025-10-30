package nl.kampmeijer.brp1.models;

public class TaartSoortDatumStarttijdLocatie {

    // Foreign key references
    private int soort_id;
    private int datum_id;
    private int starttijd_id;
    private int locatie_id;

    // Resolved names and values from the database (based on the above IDs)
    private String soort_name;
    private String datum_date;
    private String starttijd_time;
    private String locatie_name;

    /**
     * Default constructor for creating a new instance of the TaartSoortDatumStarttijdLocatie class.
     * Initializes an empty TaartSoortDatumStarttijdLocatie object.
     */
    public TaartSoortDatumStarttijdLocatie() {
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
     * Returns the ID of the datum
     *
     * @return The datum ID.
     */
    public int getDatum_id() {
        return datum_id;
    }

    /**
     * Sets the ID of the datum
     *
     * @param datum_id The datum ID.
     */
    public void setDatum_id(int datum_id) {
        this.datum_id = datum_id;
    }

    /**
     * Returns the ID of the starttijd
     *
     * @return The starttijd ID.
     */
    public int getStarttijd_id() {
        return starttijd_id;
    }

    /**
     * Sets the ID of the starttijd
     *
     * @param starttijd_id The starttijd ID.
     */
    public void setStarttijd_id(int starttijd_id) {
        this.starttijd_id = starttijd_id;
    }

    /**
     * Returns the ID of the locatie
     *
     * @return The locatie ID.
     */
    public int getLocatie_id() {
        return locatie_id;
    }

    /**
     * Sets the ID of the locatie
     *
     * @param locatie_id The locatie ID.
     */
    public void setLocatie_id(int locatie_id) {
        this.locatie_id = locatie_id;
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
     * Returns the formatted datum string as retrieved from the database based on {@code datum_id}.
     *
     * @return The date as a formatted string.
     */
    public String getDatum_date() {
        return datum_date;
    }

    /**
     * Sets the formatted datum string
     *
     * @param datum_date The date as a formatted string.
     */
    public void setDatum_date(String datum_date) {
        this.datum_date = datum_date;
    }

    /**
     * Returns the formatted starttijd string as retrieved from the database based on {@code starttijd_id}.
     *
     * @return The starttijd as a formatted string.
     */
    public String getStarttijd_time() {
        return starttijd_time;
    }

    /**
     * Sets the formatted starttijd string.
     *
     * @param starttijd_time The starttijd as a formatted string.
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
     * Sets the name of the locatie
     *
     * @param locatie_name The locatie name.
     */
    public void setLocatie_name(String locatie_name) {
        this.locatie_name = locatie_name;
    }

    /**
     * Returns a readable summary of this combination,
     * combining the soort, datum, starttijd, and locatie fields.
     *
     * @return A formatted string representing this combination.
     */
    @Override
    public String toString() {
        return soort_name + " " + datum_date + " " + starttijd_time + " " + locatie_name;
    }
}

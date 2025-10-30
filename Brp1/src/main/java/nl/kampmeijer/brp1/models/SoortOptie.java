package nl.kampmeijer.brp1.models;

/**
 * Represents a specific combination of Datum, Starttijd, and Locatie for a given Soort
 * along with the current number of registrations and the maximum allowed participants.
 * <p>
 * This class is used in the dropdown for selecting available slots when signing up a Buurtbewoner.
 */
public class SoortOptie {
    private final Datum datum;
    private final Starttijd starttijd;
    private final Locatie locatie;
    private final int currentCount;
    private final int maxCount;

    /**
     * Constructs a new SoortOptie with the given properties.
     *
     * @param datum        The datum of the option.
     * @param starttijd    The starttijd of the option.
     * @param locatie      The locatie of the option.
     * @param currentCount The number of current registrations for this option.
     * @param maxCount     The maximum number of allowed participants for this option.
     */
    public SoortOptie(Datum datum, Starttijd starttijd, Locatie locatie, int currentCount, int maxCount) {
        this.datum = datum;
        this.starttijd = starttijd;
        this.locatie = locatie;
        this.currentCount = currentCount;
        this.maxCount = maxCount;
    }

    /**
     * Returns the date (Datum) associated with this option.
     *
     * @return The {@link Datum} of this SoortOptie.
     */
    public Datum getDatum() {
        return datum;
    }

    /**
     * Returns the Starttijd associated with this option.
     *
     * @return The {@link Starttijd} of this SoortOptie.
     */
    public Starttijd getStarttijd() {
        return starttijd;
    }

    /**
     * Returns the Locatie associated with this option.
     *
     * @return The {@link Locatie} of this SoortOptie.
     */
    public Locatie getLocatie() {
        return locatie;
    }

    /**
     * Returns the current number of registrations for this option.
     *
     * @return The number of currently registered participants.
     */
    public int getCurrentCount() {
        return currentCount;
    }

    /**
     * Returns the maximum allowed number of participants for this option.
     *
     * @return The maximum number of participants that can register.
     */
    public int getMaxCount() {
        return maxCount;
    }

    /**
     * Returns a string representation of this SoortOptie, including the datum, starttijd and locatie.
     *
     * @return A formatted string representing this SoortOptie.
     */
    @Override
    public String toString() {
        return datum + " " + starttijd + " " + locatie;
    }
}

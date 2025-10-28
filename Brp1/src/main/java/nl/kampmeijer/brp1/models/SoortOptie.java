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

    public SoortOptie(Datum datum, Starttijd starttijd, Locatie locatie, int currentCount, int maxCount) {
        this.datum = datum;
        this.starttijd = starttijd;
        this.locatie = locatie;
        this.currentCount = currentCount;
        this.maxCount = maxCount;
    }

    public Datum getDatum() {
        return datum;
    }

    public Starttijd getStarttijd() {
        return starttijd;

    }
    public Locatie getLocatie() {
        return locatie;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    @Override
    public String toString() {
        return datum + " " + starttijd + " " + locatie;
    }
}

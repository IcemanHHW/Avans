package nl.kampmeijer.brp2.models;

public class Locatie {
    private String locatieNaam;

    public Locatie(String locatieNaam) {
        this.locatieNaam = locatieNaam;
    }

    public String getLocatieNaam() {
        return locatieNaam;
    }

    public void setLocatieNaam(String locatieNaam) {
        this.locatieNaam = locatieNaam;
    }

    @Override
    public String toString() {
        return locatieNaam;
    }
}

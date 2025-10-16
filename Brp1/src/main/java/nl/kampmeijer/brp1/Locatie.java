package nl.kampmeijer.brp1;

public class Locatie {
    private String locatienaam;

    public Locatie() {

    }

    public String getLocatienaam() {
        return locatienaam;
    }

    public void setLocatienaam(String locatienaam) {
        this.locatienaam = locatienaam;
    }

    @Override
    public String toString() {
        return locatienaam;
    }
}

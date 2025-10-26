package nl.kampmeijer.brp1.models;

public class Locatie {
    private int id;
    private String locatienaam;

    public Locatie() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

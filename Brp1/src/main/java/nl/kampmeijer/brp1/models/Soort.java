package nl.kampmeijer.brp1.models;

public class Soort {
    private int id;
    private String soortnaam;

    public Soort() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSoortnaam() {
        return soortnaam;
    }

    public void setSoortnaam(String soortnaam) {
        this.soortnaam = soortnaam;
    }

    @Override
    public String toString() {
        return soortnaam;
    }
}

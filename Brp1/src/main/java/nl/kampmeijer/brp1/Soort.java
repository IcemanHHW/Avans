package nl.kampmeijer.brp1;

public class Soort {
    private String soortnaam;

    public Soort() {

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

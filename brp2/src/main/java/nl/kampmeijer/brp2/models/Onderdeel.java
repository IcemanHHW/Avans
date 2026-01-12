package nl.kampmeijer.brp2.models;

public class Onderdeel {
    private String onderdeelNaam;

    public Onderdeel(String onderdeelNaam) {
        this.onderdeelNaam = onderdeelNaam;
    }

    public String getOnderdeelNaam() {
        return onderdeelNaam;
    }

    public void setOnderdeelNaam(String onderdeelNaam) {
        this.onderdeelNaam = onderdeelNaam;
    }

    @Override
    public String toString() {
        return onderdeelNaam;
    }
}

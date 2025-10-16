package nl.kampmeijer.brp1;

public class TaartMetSoortVoorMaximaalAantalPersonen {
    private String soortnaam;
    private int maximumnummer;

    public TaartMetSoortVoorMaximaalAantalPersonen() {

    }

    public String getSoortnaam() {
        return soortnaam;
    }

    public void setSoortnaam(String soortnaam) {
        this.soortnaam = soortnaam;
    }

    public int getMaximumnummer() {
        return maximumnummer;
    }

    public void setMaximumnummer(int maximumnummer) {
        this.maximumnummer = maximumnummer;
    }

    @Override
    public String toString() {
        return soortnaam +  " " + maximumnummer;
    }
}

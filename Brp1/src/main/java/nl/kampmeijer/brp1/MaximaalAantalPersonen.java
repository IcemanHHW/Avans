package nl.kampmeijer.brp1;

public class MaximaalAantalPersonen {
    private int maximimumnummer;

    public MaximaalAantalPersonen() {

    }

    public int getMaximimumnummer() {
        return maximimumnummer;
    }

    public void setMaximimumnummer(int maximimumnummer) {
        this.maximimumnummer = maximimumnummer;
    }

    @Override
    public String toString() {
        return String.valueOf(maximimumnummer);
    }
}

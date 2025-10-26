package nl.kampmeijer.brp1.models;

public class MaximaalAantalPersonen {
    private int id;
    private int maximimumnummer;

    public MaximaalAantalPersonen() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

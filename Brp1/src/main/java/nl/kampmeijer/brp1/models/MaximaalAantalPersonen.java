package nl.kampmeijer.brp1.models;

public class MaximaalAantalPersonen {
    private int id;
    private int maximumnummer;

    public MaximaalAantalPersonen() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaximumnummer() {
        return maximumnummer;
    }

    public void setMaximumnummer(int maximumnummer) {
        this.maximumnummer = maximumnummer;
    }

    @Override
    public String toString() {
        return String.valueOf(maximumnummer);
    }
}

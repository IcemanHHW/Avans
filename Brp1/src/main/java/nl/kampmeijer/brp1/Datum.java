package nl.kampmeijer.brp1;

public class Datum {
    private String datum;

    public Datum() {

    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    @Override
    public String toString() {
        return datum;
    }
}

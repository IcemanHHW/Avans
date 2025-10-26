package nl.kampmeijer.brp1.models;

public class Datum {
    private int id;
    private String datum;

    public Datum() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

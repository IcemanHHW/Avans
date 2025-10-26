package nl.kampmeijer.brp1.models;

public class Starttijd {
    private int id;
    private String starttijd;

    public Starttijd() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStarttijd() {
        return starttijd;
    }

    public void setStarttijd(String starttijd) {
        this.starttijd = starttijd;
    }

    @Override
    public String toString() {
        return starttijd;
    }
}

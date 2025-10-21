package nl.kampmeijer.fgt1_fysio.classes;

public class Woonplaats {
    private int id;
    private String wpnm;

    public Woonplaats() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWpnm() {
        return wpnm;
    }

    public void setWpnm(String wpnm) {
        this.wpnm = wpnm;
    }

    @Override
    public String toString() {
        return wpnm;
    }
}

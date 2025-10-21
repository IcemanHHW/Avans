package nl.kampmeijer.fgt1_fysio.classes;

public class Fysiotherapeut {
    private int id;
    private String fysnm;

    public Fysiotherapeut() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFysnm() {
        return fysnm;
    }

    public void setFysnm(String fysnm) {
        this.fysnm = fysnm;
    }

    @Override
    public String toString() {
        return fysnm;
    }
}

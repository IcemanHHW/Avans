package nl.kampmeijer.fgt1_fysio;

public class Adres {
    private int id;
    private String anm;

    public Adres() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnm() {
        return anm;
    }

    public void setAnm(String anm) {
        this.anm = anm;
    }

    @Override
    public String toString() {
        return anm;
    }
}

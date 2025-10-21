package nl.kampmeijer.fgt1_fysio.classes;

public class Patient {
    private int id;
    private String pnm;

    public Patient() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPnm() {
        return pnm;
    }

    public void setPnm(String pnm) {
        this.pnm = pnm;
    }

    @Override
    public String toString() {
        return pnm;
    }
}

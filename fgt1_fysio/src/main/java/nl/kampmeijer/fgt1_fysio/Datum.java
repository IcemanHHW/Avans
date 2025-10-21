package nl.kampmeijer.fgt1_fysio;

public class Datum {
    private int id;
    private String dtm;

    public Datum() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDtm() {
        return dtm;
    }

    public void setDtm(String dtm) {
        this.dtm = dtm;
    }

    @Override
    public String toString() {
        return dtm;
    }
}

package nl.kampmeijer.brp1.models;

public class Buurtbewoner {
    private int id;
    private String buurtbewonernaam;

    public Buurtbewoner(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuurtbewonernaam() {
        return buurtbewonernaam;
    }

    public void setBuurtbewonernaam(String buurtbewonernaam) {
        this.buurtbewonernaam = buurtbewonernaam;
    }

    @Override
    public String toString() {
        return buurtbewonernaam;
    }
}

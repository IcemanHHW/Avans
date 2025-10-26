package nl.kampmeijer.brp1.models;

public class Variant {
    private int id;
    private String variantnaam;

    public Variant() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVariantnaam() {
        return variantnaam;
    }

    public void setVariantnaam(String variantnaam) {
        this.variantnaam = variantnaam;
    }

    @Override
    public String toString() {
        return variantnaam;
    }
}

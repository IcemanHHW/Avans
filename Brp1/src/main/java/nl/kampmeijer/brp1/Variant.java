package nl.kampmeijer.brp1;

public class Variant {
    private String variantnaam;

    public Variant() {

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

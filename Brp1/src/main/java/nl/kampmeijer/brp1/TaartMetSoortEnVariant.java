package nl.kampmeijer.brp1;

public class TaartMetSoortEnVariant {
    private String soortnaam;
    private String variantnaam;

    public TaartMetSoortEnVariant() {

    }

    public String getSoortnaam() {
        return soortnaam;
    }

    public void setSoortnaam(String soortnaam) {
        this.soortnaam = soortnaam;
    }

    public String getVariantnaam() {
        return variantnaam;
    }

    public void setVariantnaam(String variantnaam) {
        this.variantnaam = variantnaam;
    }

    @Override
    public String toString() {
        return soortnaam +  " " + variantnaam;
    }
}

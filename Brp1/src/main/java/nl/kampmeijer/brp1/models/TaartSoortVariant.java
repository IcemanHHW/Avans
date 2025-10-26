package nl.kampmeijer.brp1.models;

public class TaartSoortVariant {
    private int soort_id;
    private int variant_id;

    private String soort_name;
    private String variant_name;

    public TaartSoortVariant() {

    }

    public int getSoort_id() {
        return soort_id;
    }

    public void setSoort_id(int soort_id) {
        this.soort_id = soort_id;
    }

    public int getVariant_id() {
        return variant_id;
    }

    public void setVariant_id(int variant_id) {
        this.variant_id = variant_id;
    }

    public String getSoort_name() {
        return soort_name;
    }

    public void setSoort_name(String soort_name) {
        this.soort_name = soort_name;
    }

    public String getVariant_name() {
        return variant_name;
    }

    public void setVariant_name(String variant_name) {
        this.variant_name = variant_name;
    }

    @Override
    public String toString() {
        return soort_name + " " +  variant_name;
    }
}

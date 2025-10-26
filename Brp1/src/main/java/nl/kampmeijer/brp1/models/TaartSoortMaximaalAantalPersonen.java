package nl.kampmeijer.brp1.models;

public class TaartSoortMaximaalAantalPersonen {
    private int soort_id;
    private int maxpers_id;

    private String soort_name;
    private int maxpers_number;

    public TaartSoortMaximaalAantalPersonen() {

    }

    public int getSoort_id() {
        return soort_id;
    }

    public void setSoort_id(int soort_id) {
        this.soort_id = soort_id;
    }

    public int getMaxpers_id() {
        return maxpers_id;
    }

    public void setMaxpers_id(int maxpers_id) {
        this.maxpers_id = maxpers_id;
    }

    public String getSoort_name() {
        return soort_name;
    }

    public void setSoort_name(String soort_name) {
        this.soort_name = soort_name;
    }

    public int getMaxpers_number() {
        return maxpers_number;
    }

    public void setMaxpers_number(int maxpers_number) {
        this.maxpers_number = maxpers_number;
    }

    @Override
    public String toString() {
        return soort_name + " " + maxpers_number;
    }
}

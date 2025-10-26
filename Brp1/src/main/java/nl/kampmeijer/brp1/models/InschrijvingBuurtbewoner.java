package nl.kampmeijer.brp1.models;

public class InschrijvingBuurtbewoner {
    private int buurtbewoner_id;
    private int soort_id;
    private int variant_id;
    private int datum_id;
    private int starttijd_id;
    private int locatie_id;

    private String buurtbewoner_name;
    private String soort_name;
    private String variant_name;
    private String datum_date;
    private String starttijd_time;
    private String locatie_name;

    public int getBuurtbewoner_id() {
        return buurtbewoner_id;
    }

    public void setBuurtbewoner_id(int buurtbewoner_id) {
        this.buurtbewoner_id = buurtbewoner_id;
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

    public int getDatum_id() {
        return datum_id;
    }

    public void setDatum_id(int datum_id) {
        this.datum_id = datum_id;
    }

    public int getStarttijd_id() {
        return starttijd_id;
    }

    public void setStarttijd_id(int starttijd_id) {
        this.starttijd_id = starttijd_id;
    }

    public int getLocatie_id() {
        return locatie_id;
    }

    public void setLocatie_id(int locatie_id) {
        this.locatie_id = locatie_id;
    }

    public String getBuurtbewoner_name() {
        return buurtbewoner_name;
    }

    public void setBuurtbewoner_name(String buurtbewoner_name) {
        this.buurtbewoner_name = buurtbewoner_name;
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

    public String getDatum_date() {
        return datum_date;
    }

    public void setDatum_date(String datum_date) {
        this.datum_date = datum_date;
    }

    public String getStarttijd_time() {
        return starttijd_time;
    }

    public void setStarttijd_time(String starttijd_time) {
        this.starttijd_time = starttijd_time;
    }

    public String getLocatie_name() {
        return locatie_name;
    }

    public void setLocatie_name(String locatie_name) {
        this.locatie_name = locatie_name;
    }

    @Override
    public String toString() {
        return buurtbewoner_name + " " + soort_name + " " + variant_name + " " +  datum_date + " " + starttijd_time + " " + locatie_name;
    }
}

package nl.kampmeijer.brp1.models;

public class TaartSoortDatumStarttijdLocatie {
    private int soort_id;
    private int datum_id;
    private int starttijd_id;
    private int locatie_id;

    private String soort_name;
    private String datum_date;
    private String starttijd_time;
    private String locatie_name;

    public TaartSoortDatumStarttijdLocatie() {

    }

    public int getSoort_id() {
        return soort_id;
    }

    public void setSoort_id(int soort_id) {
        this.soort_id = soort_id;
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

    public String getSoort_name() {
        return soort_name;
    }

    public void setSoort_name(String soort_name) {
        this.soort_name = soort_name;
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
        return soort_name + " " + datum_date + " " + starttijd_time + " " + locatie_name;
    }
}

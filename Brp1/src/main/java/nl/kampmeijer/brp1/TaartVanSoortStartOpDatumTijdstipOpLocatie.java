package nl.kampmeijer.brp1;

public class TaartVanSoortStartOpDatumTijdstipOpLocatie {

    private String soortnaam;
    private String datum;
    private String starttijd;
    private String locatienaam;

    public TaartVanSoortStartOpDatumTijdstipOpLocatie() {

    }

    public String getSoortnaam() {
        return soortnaam;
    }

    public void setSoortnaam(String soortnaam) {
        this.soortnaam = soortnaam;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getStarttijd() {
        return starttijd;
    }

    public void setStarttijd(String starttijd) {
        this.starttijd = starttijd;
    }

    public String getLocatienaam() {
        return locatienaam;
    }

    public void setLocatienaam(String locatienaam) {
        this.locatienaam = locatienaam;
    }

    @Override
    public String toString() {
        return soortnaam +  " " + datum +  " " + starttijd +   " " + locatienaam;
    }
}

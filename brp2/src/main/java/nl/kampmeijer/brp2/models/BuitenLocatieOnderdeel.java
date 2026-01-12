package nl.kampmeijer.brp2.models;

public class BuitenLocatieOnderdeel extends LocatieOnderdeel {
    private String gevelNaam, blootstellingNaam;

    public BuitenLocatieOnderdeel(int lo_id, Locatie locatie, Onderdeel onderdeel, String gevelNaam, String blootstellingNaam) {
        super(lo_id, locatie, onderdeel);
        this.gevelNaam = gevelNaam;
        this.blootstellingNaam = blootstellingNaam;
    }

    public String getGevelNaam() {
        return gevelNaam;
    }

    public void setGevelNaam(String gevelNaam) {
        this.gevelNaam = gevelNaam;
    }

    public String getBlootstellingNaam() {
        return blootstellingNaam;
    }

    public void setBlootstellingNaam(String blootstellingNaam) {
        this.blootstellingNaam = blootstellingNaam;
    }

    @Override
    public String locatieOnderdeelInfo() {
        return onderdeel.getOnderdeelNaam() + " " + gevelNaam + " " + blootstellingNaam;
    }
}

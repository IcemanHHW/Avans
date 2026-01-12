package nl.kampmeijer.brp2.models;

public class BinnenLocatieOnderdeel extends LocatieOnderdeel {
    private String ruimteNaam, verdiepingNummer;

    public BinnenLocatieOnderdeel(int lo_id, Locatie locatie, Onderdeel onderdeel, String ruimteNaam, String verdiepingNummer) {
        super(lo_id, locatie, onderdeel);
        this.ruimteNaam = ruimteNaam;
        this.verdiepingNummer = verdiepingNummer;
    }

    public String getVerdiepingNummer() {
        return verdiepingNummer;
    }

    public void setVerdiepingNummer(String verdiepingNummer) {
        this.verdiepingNummer = verdiepingNummer;
    }

    public String getRuimteNaam() {
        return ruimteNaam;
    }

    public void setRuimteNaam(String ruimteNaam) {
        this.ruimteNaam = ruimteNaam;
    }

    @Override
    public String locatieOnderdeelInfo() {
        return onderdeel.getOnderdeelNaam() + " " + ruimteNaam + " " + verdiepingNummer;
    }
}

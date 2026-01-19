package nl.kampmeijer.brp2.models;

public class AanVraagGemeenteMonument {
    private int aanvraagNummer;
    private Categorie categorie;
    private LocatieOnderdeel locatieOnderdeel;
    private GemeenteMonument gemeenteMonument;

    public AanVraagGemeenteMonument(int aanvraagNummer, Categorie categorie, LocatieOnderdeel locatieOnderdeel, GemeenteMonument gemeenteMonument) {
        this.aanvraagNummer = aanvraagNummer;
        this.categorie = categorie;
        this.locatieOnderdeel = locatieOnderdeel;
        this.gemeenteMonument = gemeenteMonument;
    }

    public int getAanvraagNummer() {
        return aanvraagNummer;
    }

    public void setAanvraagNummer(int aanvraagNummer) {
        this.aanvraagNummer = aanvraagNummer;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public LocatieOnderdeel getLocatieOnderdeel() {
        return locatieOnderdeel;
    }

    public void setLocatieOnderdeel(LocatieOnderdeel locatieOnderdeel) {
        this.locatieOnderdeel = locatieOnderdeel;
    }

    public GemeenteMonument getGemeenteMonument() {
        return gemeenteMonument;
    }

    public void setGemeenteMonument(GemeenteMonument gemeenteMonument) {
        this.gemeenteMonument = gemeenteMonument;
    }

    @Override
    public String toString() {
        return aanvraagNummer + " " + categorie.getCategorieNaam() +  " " + locatieOnderdeel.locatieOnderdeelInfo() + " " + gemeenteMonument.toString();
    }
}

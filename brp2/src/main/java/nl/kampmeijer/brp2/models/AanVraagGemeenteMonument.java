package nl.kampmeijer.brp2.models;

public class AanVraagGemeenteMonument {
    private final int aanvraagNummer;
    private final Categorie categorie;
    private final LocatieOnderdeel locatieOnderdeel;

    public AanVraagGemeenteMonument(int aanvraagNummer, Categorie categorie, LocatieOnderdeel locatieOnderdeel) {
        this.aanvraagNummer = aanvraagNummer;
        this.categorie = categorie;
        this.locatieOnderdeel = locatieOnderdeel;
    }

    public int getAanvraagNummer() {
        return aanvraagNummer;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public LocatieOnderdeel getLocatieOnderdeel() {
        return locatieOnderdeel;
    }

    @Override
    public String toString() {
        return aanvraagNummer + " " + categorie.getCategorieNaam() +  " " + locatieOnderdeel.locatieOnderdeelInfo();
    }
}

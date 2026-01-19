package nl.kampmeijer.brp2.models;

public class GemeenteMonument {
    private int uniekNummer;
    private String straat, huisNr, huisLetter, postcode, plaats;

    public  GemeenteMonument(int uniekNummer, String straat, String huisNr, String huisLetter, String postcode, String plaats) {
        this.uniekNummer = uniekNummer;
        this.straat = straat;
        this.huisNr = huisNr;
        this.huisLetter = huisLetter;
        this.postcode = postcode;
        this.plaats = plaats;
    }

    public int getUniekNummer() {
        return uniekNummer;
    }

    public void setUniekNummer(int uniekNummer) {
        this.uniekNummer = uniekNummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getHuisNr() {
        return huisNr;
    }

    public void setHuisNr(String huisNr) {
        this.huisNr = huisNr;
    }

    public String getHuisLetter() {
        return huisLetter;
    }

    public void setHuisLetter(String huisLetter) {
        this.huisLetter = huisLetter;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    @Override
    public String toString() {
        return straat + " " + huisNr +  huisLetter;
    }
}

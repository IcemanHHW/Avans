package nl.kampmeijer.brp2.models;

public abstract class LocatieOnderdeel {
    protected int lo_id;
    protected Locatie locatie;
    protected Onderdeel onderdeel;


    public LocatieOnderdeel(int lo_id, Locatie locatie, Onderdeel onderdeel) {
        this.lo_id = lo_id;
        this.locatie = locatie;
        this.onderdeel = onderdeel;
    }

    public int getLo_id() {
        return lo_id;
    }

    public Locatie getLocatie() {
        return locatie;
    }

    public Onderdeel getOnderdeel() {
        return onderdeel;
    }

    public void setOnderdeel(Onderdeel onderdeel) {
        this.onderdeel = onderdeel;
    }

    public abstract String locatieOnderdeelInfo();
}

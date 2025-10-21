package nl.kampmeijer.fgt1_fysio;

public class PatientAdresGegevens {
    private int patient_id;
    private int adres_id;
    private int woonplaats_id;

    public PatientAdresGegevens() {

    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getAdres_id() {
        return adres_id;
    }

    public void setAdres_id(int adres_id) {
        this.adres_id = adres_id;
    }

    public int getWoonplaats_id() {
        return woonplaats_id;
    }

    public void setWoonplaats_id(int woonplaats_id) {
        this.woonplaats_id = woonplaats_id;
    }
}

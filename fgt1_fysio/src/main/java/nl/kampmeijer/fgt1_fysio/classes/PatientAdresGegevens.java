package nl.kampmeijer.fgt1_fysio.classes;

public class PatientAdresGegevens {
    private int patient_id;
    private int adres_id;
    private int woonplaats_id;

    private String patient_name;
    private String adres_name;
    private String woonplaats_name;

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

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getAdres_name() {
        return adres_name;
    }

    public void setAdres_name(String adres_name) {
        this.adres_name = adres_name;
    }

    public String getWoonplaats_name() {
        return woonplaats_name;
    }

    public void setWoonplaats_name(String woonplaats_name) {
        this.woonplaats_name = woonplaats_name;
    }

    @Override
    public String toString() {
        return patient_name + " " +  adres_name + " " +  woonplaats_name;
    }
}

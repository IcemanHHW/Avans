package nl.kampmeijer.fgt1_fysio.classes;

public class FysioAfspraak {
    private int fysio_id;
    private int patient_id;
    private int datum_id;

    private String fysio_name;
    private String patient_name;
    private String datum_date;

    public FysioAfspraak() {

    }

    public int getFysio_id() {
        return fysio_id;
    }

    public void setFysio_id(int fysio_id) {
        this.fysio_id = fysio_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getDatum_id() {
        return datum_id;
    }

    public void setDatum_id(int datum_id) {
        this.datum_id = datum_id;
    }

    public String getFysio_name() {
        return fysio_name;
    }

    public void setFysio_name(String fysio_name) {
        this.fysio_name = fysio_name;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getDatum_date() {
        return datum_date;
    }

    public void setDatum_date(String datum_date) {
        this.datum_date = datum_date;
    }

    @Override
    public String toString() {
        return fysio_name + " " +  patient_name + " " +  datum_date;
    }
}

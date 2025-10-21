package nl.kampmeijer.fgt1_fysio.classes;

public class FysioAfspraak {
    private int fysio_id;
    private int patient_id;
    private int datum_id;

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
}

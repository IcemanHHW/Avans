package nl.kampmeijer.fgt1_fysio.classes;

public class FysioMetSpec {
    private int fysio_id;
    private int spec_id;

    private String fysio_name;
    private String spec_type;

    public FysioMetSpec() {

    }

    public int getFysio_id() {
        return fysio_id;
    }

    public void setFysio_id(int fysio_id) {
        this.fysio_id = fysio_id;
    }

    public int getSpec_id() {
        return spec_id;
    }

    public void setSpec_id(int spec_id) {
        this.spec_id = spec_id;
    }

    public String getSpec_type() {
        return spec_type;
    }

    public void setSpec_type(String spec_type) {
        this.spec_type = spec_type;
    }

    public String getFysio_name() {
        return fysio_name;
    }

    public void setFysio_name(String fysio_name) {
        this.fysio_name = fysio_name;
    }

    @Override
    public String toString() {
        return fysio_name + " " +  spec_type;
    }
}

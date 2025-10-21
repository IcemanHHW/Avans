package nl.kampmeijer.fgt1_fysio;

public class Specialisatie {
    private int id;
    private String spec;

    public Specialisatie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    @Override
    public String toString() {
        return spec;
    }
}

package nl.kampmeijer.kassavoorbeeld;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Kassa {

    private TextField invoer, subtotaal;
    private Button opslaan, afrekenen;

    private double dblInvoer, dblSubtotaal;

    public Kassa() {
        invoer = new TextField();
        subtotaal = new TextField();
        opslaan = new Button("Opslaan");
        afrekenen = new Button("Afrekenen");

        dblInvoer = 0;
        dblSubtotaal = 0;

        opslaan.setOnAction(e -> {
            telOp();
        });

        afrekenen.setOnAction(e -> {
            // haal waarde van subtotaal op
            double totaal = getSubtotaal();
            // druk de waarde af in het textfield subtotaal
            subtotaal.setText(totaal + "");
        });
    }

    public void telOp() {
        // lees de invoer
        String strInvoer = invoer.getText();
        // verander invoer in een getal
        dblInvoer = Double.parseDouble(strInvoer);
        // tel getal op bij subtotaal
        dblSubtotaal += dblInvoer;
    }

    public double getSubtotaal() {
        // geef de waarde vam het subtotaal terug
        return dblSubtotaal;
    };


}

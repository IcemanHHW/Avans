package nl.kampmeijer.fxoef0403;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Oef0403 {
    private Label jaartal;
    private TextField bevolking;
    private Button button;
    private int intJaartal;
    private double dblBevolking;
    private final double dblGroei = 1.65;

    public Oef0403(GridPane grid) {
        intJaartal = 1960;
        dblBevolking = 3e9;
        String dblBevolkingRdbl = String.format("%,.0f", dblBevolking);

        jaartal = new Label("" + intJaartal);
        bevolking = new TextField(dblBevolkingRdbl);
        bevolking.setEditable(false);
        button = new Button("Jaar erbij");

        button.setOnAction(e -> {
            intJaartal++;
            jaartal.setText("" + intJaartal);

            dblBevolking = dblGroei * dblBevolking;
            bevolking.setText(String.format("%,.0f", dblBevolking));
        });

        grid.add(jaartal, 0, 0);
        grid.add(bevolking, 1, 0);
        grid.add(button, 0, 1);
    }
}

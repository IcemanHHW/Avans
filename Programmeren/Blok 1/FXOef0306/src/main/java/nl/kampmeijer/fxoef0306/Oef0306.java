package nl.kampmeijer.fxoef0306;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class Oef0306 {
    private final Button plusknop, productknop, verschilknop, quotientknop, restknop, wisknop;
    private final TextField invoervak1, invoervak2, resultaatvak;

    public Oef0306(FlowPane root) {
        plusknop = new Button("+");
        productknop = new Button("*");
        verschilknop = new Button("-");
        quotientknop = new Button("/");
        restknop = new Button("%");
        wisknop = new Button("Wis uit");
        invoervak1 = new TextField();
        invoervak2 = new TextField();
        resultaatvak = new TextField();

        invoervak1.setAlignment(Pos.CENTER_RIGHT);
        invoervak2.setAlignment(Pos.CENTER_RIGHT);
        resultaatvak.setAlignment(Pos.CENTER_RIGHT);

        plusknop.setOnAction(event -> {
            String invoer1 = invoervak1.getText();
            int getal1 = Integer.parseInt(invoer1);
            String invoer2 = invoervak2.getText();
            int getal2 = Integer.parseInt(invoer2);

            int resultaat = getal1 + getal2;
            resultaatvak.setText(""+(resultaat));
        });

        productknop.setOnAction(event -> {
            String invoer1 = invoervak1.getText();
            int getal1 = Integer.parseInt(invoer1);
            String invoer2 = invoervak2.getText();
            int getal2 = Integer.parseInt(invoer2);

            int resultaat = getal1 * getal2;
            resultaatvak.setText(""+(resultaat));
        });

        verschilknop.setOnAction(event -> {
            String invoer1 = invoervak1.getText();
            int getal1 = Integer.parseInt(invoer1);
            String invoer2 = invoervak2.getText();
            int getal2 = Integer.parseInt(invoer2);

            int resultaat = getal1 - getal2;
            resultaatvak.setText(""+(resultaat));
        });

        quotientknop.setOnAction(event -> {
            String invoer1 = invoervak1.getText();
            int getal1 = Integer.parseInt(invoer1);
            String invoer2 = invoervak2.getText();
            int getal2 = Integer.parseInt(invoer2);

            int resultaat = getal1 / getal2;
            resultaatvak.setText(""+(resultaat));
        });

        restknop.setOnAction(event -> {
            String invoer1 = invoervak1.getText();
            int getal1 = Integer.parseInt(invoer1);
            String invoer2 = invoervak2.getText();
            int getal2 = Integer.parseInt(invoer2);

            int resultaat = getal1 % getal2;
            resultaatvak.setText(""+(resultaat));
        });

        wisknop.setOnAction(event -> {
            invoervak1.clear();
            invoervak2.clear();
            resultaatvak.clear();
        });

        root.getChildren().addAll(invoervak1, invoervak2, resultaatvak, plusknop, productknop, verschilknop, quotientknop, restknop, wisknop);
    }
}
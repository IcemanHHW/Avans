package nl.kampmeijer.fxoef0404;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Oef0404 {
    private final Label bedragLabel, subtotaalLabel, btwLabel, totaalExBtwLabel, totaalLabel;
    private final TextField bedragField, subtotaalField, btwField, totaalExBtwField, totaalField;
    private final Button totaalButton, resetButton;
    private final Kassa kassa;

    public Oef0404(GridPane gridPane) {
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        bedragLabel = new Label("Bedrag:");
        subtotaalLabel = new Label("Subtotaal:");
        btwLabel = new Label("Btw:");
        totaalExBtwLabel = new Label("Totaal ex Btw:");
        totaalLabel = new Label("Totaal:");
        bedragField = new TextField();
        subtotaalField = new TextField();
        btwField = new TextField();
        totaalExBtwField = new TextField();
        totaalField = new TextField();
        totaalButton = new Button("Totaal");
        resetButton = new Button("Reset");

        subtotaalField.setEditable(false);
        btwField.setEditable(false);
        totaalExBtwField.setEditable(false);
        totaalField.setEditable(false);

        kassa = new Kassa();

        bedragField.setOnAction(event -> {
            String invoer = bedragField.getText();
            double dblInvoer = Double.parseDouble(invoer);

            kassa.telOp(dblInvoer);

            double st = kassa.getSubtotaal();
            double btw = kassa.berekenBTW();

            subtotaalField.setText(String.format("%.2f", st));
            btwField.setText(String.format("%.2f", btw));
            bedragField.setText("");
        });

        resetButton.setOnAction(event -> {
            bedragField.clear();
            subtotaalField.clear();
            btwField.clear();
            totaalExBtwField.clear();
            totaalField.clear();
            kassa.reset();
        });

        totaalButton.setOnAction(event -> {
            double ttl = kassa.getSubtotaal();
            double ttlexbtw = kassa.berekenSubtotaalExBTW();

            totaalField.setText(String.format("%.2f", ttl));
            totaalExBtwField.setText(String.format("%.2f", ttlexbtw));
        });

        gridPane.add(bedragLabel, 0, 0);
        gridPane.add(bedragField, 0, 1);
        gridPane.add(subtotaalLabel, 1,0);
        gridPane.add(subtotaalField, 1,1);
        gridPane.add(totaalButton, 1, 2);
        gridPane.add(btwLabel, 2, 0);
        gridPane.add(btwField, 2, 1);
        gridPane.add(totaalExBtwLabel, 3, 0);
        gridPane.add(totaalExBtwField, 3, 1);
        gridPane.add(totaalLabel, 4, 0);
        gridPane.add(totaalField, 4, 1);
        gridPane.add(resetButton, 4, 3);
    }
}

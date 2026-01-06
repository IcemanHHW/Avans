package nl.kampmeijer.fgt2.views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AdminView {
    private final Button bouwbedrijfButton = new Button("Bouwbedrijven");
    private final Button woontorenButton = new Button("Woontorens");

    public AdminView( GridPane root,
                       Runnable onBouwbedrijf,
                       Runnable onWoontoren) {

        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);

        Label titleLabel = new Label("Admin Menu");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 20));

        Label basicDataLabel = new Label("Basis Data:");
        basicDataLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        bouwbedrijfButton.setPrefWidth(250);
        woontorenButton.setPrefWidth(250);

        bouwbedrijfButton.setOnAction(_ -> onBouwbedrijf.run());
        woontorenButton.setOnAction(_ -> onWoontoren.run());

        int row = 0;

        root.add(titleLabel, 0, row++, 2, 1);

        row++;
        root.add(basicDataLabel, 0, row++, 2, 1);
        root.add(bouwbedrijfButton, 0, row++);
        root.add(woontorenButton, 0, row++);
    }
}
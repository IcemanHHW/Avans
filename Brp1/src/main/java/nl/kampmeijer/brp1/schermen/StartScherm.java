package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.jetbrains.annotations.NotNull;

public class StartScherm {
    private final Button inschrijvenButton = new Button("Inschrijven");
    private final Button adminButton = new Button("Admin");

    public StartScherm(@NotNull GridPane root, Runnable onInschrijven, Runnable onAdmin) {
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);

        VBox content = new VBox(30);
        content.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welkom bij Taarten Bakken in Lottum");
        welcomeLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        Label descriptionLabel = new Label("Meld je aan voor een taartbak workshop!");
        descriptionLabel.setFont(Font.font("System", 16));

        inschrijvenButton.setPrefSize(200, 50);
        inschrijvenButton.setStyle("-fx-font-size: 16px;");

        adminButton.setPrefSize(200, 50);
        adminButton.setStyle("-fx-font-size: 16px;");

        inschrijvenButton.setOnAction(_ -> onInschrijven.run());
        adminButton.setOnAction(_ -> onAdmin.run());

        content.getChildren().addAll(welcomeLabel, descriptionLabel, inschrijvenButton, adminButton);
        root.add(content, 0, 0);
    }
}
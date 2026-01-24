package nl.kampmeijer.brp2.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.jetbrains.annotations.NotNull;

public class StartView {

    public StartView(@NotNull GridPane root, Runnable onApp, Runnable onAdmin) {
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);

        VBox content = new VBox(30);
        content.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Gemeente Monumenten Maastricht");
        welcomeLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        Button aanvraagButton = new Button("Nieuwe aanvraag");
        aanvraagButton.setPrefSize(200, 50);
        aanvraagButton.setStyle("-fx-font-size: 16px;");

        Button adminButton = new Button("Admin");
        adminButton.setPrefSize(200, 50);
        adminButton.setStyle("-fx-font-size: 16px;");

        aanvraagButton.setOnAction(_ -> onApp.run());
        adminButton.setOnAction(_ -> onAdmin.run());

        content.getChildren().addAll(welcomeLabel, aanvraagButton, adminButton);
        root.add(content, 0, 0);
    }
}
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

public class AdminView {
    public AdminView(
            @NotNull GridPane root,
            Runnable onCategorie,
            Runnable onOnderdeel,
            Runnable onLocatie,
            Runnable onBinnenLocatieOnderdeel,
            Runnable onBuitenLocatieOnderdeel,
            Runnable onAanvraagGemeenteMonumentOverview,
            Runnable onBack
    ) {

        root.setPadding(new Insets(20));
        root.setHgap(40);
        root.setVgap(20);

        Button backButton = new Button("Terug");
        Button categorieButton = new Button("Categorieën");
        Button onderdeelButton = new Button("Onderdelen");
        Button locatieButton = new Button("Locaties");
        Button binnenLocatieOnderdeelButton = new Button("BinnenLocatiesOnderdelen");
        Button buitenLocatieOnderdeelButton = new Button("BuitenLocatiesOnderdelen");
        Button aanvraagGemeenteMonumentButton = new Button("Aanvragen Overzicht");

        Label adminLabel = new Label("ADMIN");
        adminLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        backButton.setPrefWidth(250);
        categorieButton.setPrefWidth(250);
        onderdeelButton.setPrefWidth(250);
        locatieButton.setPrefWidth(250);
        binnenLocatieOnderdeelButton.setPrefWidth(250);
        buitenLocatieOnderdeelButton.setPrefWidth(250);
        aanvraagGemeenteMonumentButton.setPrefWidth(250);

        backButton.setOnAction(_ -> onBack.run());
        aanvraagGemeenteMonumentButton.setOnAction(_ -> onAanvraagGemeenteMonumentOverview.run());
        categorieButton.setOnAction(_ -> onCategorie.run());
        onderdeelButton.setOnAction(_ -> onOnderdeel.run());
        locatieButton.setOnAction(_ -> onLocatie.run());
        binnenLocatieOnderdeelButton.setOnAction(_ -> onBinnenLocatieOnderdeel.run());
        buitenLocatieOnderdeelButton.setOnAction(_ -> onBuitenLocatieOnderdeel.run());


        VBox leftBox = new VBox(10, aanvraagGemeenteMonumentButton);
        leftBox.setAlignment(Pos.TOP_CENTER);

        VBox rightBox = new VBox(
                10,
                categorieButton,
                onderdeelButton,
                locatieButton,
                binnenLocatieOnderdeelButton,
                buitenLocatieOnderdeelButton
        );
        rightBox.setAlignment(Pos.TOP_CENTER);

        root.add(backButton, 0, 0);
        root.add(leftBox, 0, 1);
        root.add(adminLabel, 1, 1);
        root.add(rightBox, 2, 1);
    }
}

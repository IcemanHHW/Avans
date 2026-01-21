package nl.kampmeijer.brp2.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.brp2.models.AanVraagGemeenteMonument;
import nl.kampmeijer.brp2.services.AanvraagGemeenteMonumentService;
import org.jetbrains.annotations.NotNull;

public class AanvraagGemeenteMonumentOverviewView {
    private final TextField categorieNaamField = new TextField(), locatieOnderdeelField = new TextField(), gemeenteMonumentField = new TextField();
    private final Label validationLabel = new Label();
    private final ListView<AanVraagGemeenteMonument> listview = new ListView<>();
    private final AanvraagGemeenteMonumentService aanvraagGemeenteMonumentService = new AanvraagGemeenteMonumentService();

    public AanvraagGemeenteMonumentOverviewView(@NotNull GridPane root) {
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);

        VBox formBox = new VBox();
        Button backButton = new Button("Terug");
        Label gemeenteMonumentLabel = new Label("Adres");
        Label categorieLabel = new Label("Categorie");
        Label locatieOnderdeellabel = new Label("Locatie / Onderdeel / Info");

        gemeenteMonumentLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        categorieLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        locatieOnderdeellabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        validationLabel.setStyle("-fx-text-fill: red;");


        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        //backButton.setOnAction(_ -> onBack.run());

        formBox.getChildren().addAll(
                listview,
                validationLabel,
                gemeenteMonumentLabel, categorieLabel, locatieOnderdeellabel,
                gemeenteMonumentField, categorieNaamField, locatieOnderdeelField
        );

        root.add(formBox, 1, 1);
        root.add(backButton, 0, 0);

        listview.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(AanVraagGemeenteMonument item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });

        loadAanvragenGemeenteMonumenten();
    }

    private void loadAanvragenGemeenteMonumenten() {
        try {
            listview.getItems().addAll((AanVraagGemeenteMonument) aanvraagGemeenteMonumentService.getAll());
        } catch (RuntimeException e) {
            switch (e.getMessage()) {
                case "DATABASE_LOAD_ERROR" ->
                        validationLabel.setText("Databasefout bij laden van AanvragenGemeenteMonumenten");
                case "DATABASE_NULL_ERROR" ->
                        validationLabel.setText("Interne fout bij laden van AanvragenGemeenteMonumenten");
                default ->
                        validationLabel.setText("Onverwachte fout opgetreden");
            }
        }
    }


}

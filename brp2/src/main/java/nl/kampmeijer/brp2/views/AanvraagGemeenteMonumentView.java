package nl.kampmeijer.brp2.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.brp2.models.*;
import nl.kampmeijer.brp2.services.*;
import org.jetbrains.annotations.NotNull;

public class AanvraagGemeenteMonumentView {
    private final Label validationLabel = new Label();
    private final ComboBox<Categorie> categorieComboBox = new ComboBox<>();
    private final ComboBox<Locatie> locatieComboBox = new ComboBox<>();
    private final ComboBox<BinnenLocatieOnderdeel> binnenLocatieOnderdeelComboBox = new ComboBox<>();
    private final ComboBox<BuitenLocatieOnderdeel> buitenLocatieOnderdeelComboBox = new ComboBox<>();
    private final ComboBox<GemeenteMonument> gemeenteMonumentComboBox = new ComboBox<>();
    private final CategorieService categorieService = new CategorieService();
    private final LocatieService locatieService = new LocatieService();
    private final BinnenLocatieOnderdeelService binnenLocatieOnderdeelService = new BinnenLocatieOnderdeelService();
    private final BuitenLocatieOnderdeelService buitenLocatieOnderdeelService = new BuitenLocatieOnderdeelService();
    private final AanvraagGemeenteMonumentService aanvraagGemeenteMonumentService = new AanvraagGemeenteMonumentService();

    public AanvraagGemeenteMonumentView(@NotNull GridPane root) {
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);

        VBox formBox = new VBox();
        Button backButton = new Button("Terug");
        Label gemeenteMonumentLabel = new Label("Adres");
        Label categorieLabel = new Label("Categorie");
        Label locatieLabel = new Label("Locatie");
        Label binnenLocatieOnderdeellabel = new Label("Onderdeel / Ruimte / Verdieping");
        Label buitenLocatieOnderdeellabel = new Label("Onderdeel / Gevel / Blootstelling");
        Button addButton = new Button("Versturen");

        gemeenteMonumentLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        categorieLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        locatieLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        binnenLocatieOnderdeellabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        buitenLocatieOnderdeellabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        //backButton.setOnAction(_ -> onBack.run());
        addButton.setPrefSize(120, 30);
        addButton.setStyle("-fx-font-size: 16px;");

        binnenLocatieOnderdeellabel.setVisible(false);
        binnenLocatieOnderdeellabel.setVisible(false);
        buitenLocatieOnderdeellabel.setVisible(false);
        buitenLocatieOnderdeellabel.setVisible(false);

        formBox.setAlignment(Pos.CENTER);
        formBox.getChildren().addAll(
                validationLabel,
                gemeenteMonumentLabel, gemeenteMonumentComboBox,
                categorieLabel, categorieComboBox,
                locatieLabel, locatieComboBox,
                binnenLocatieOnderdeellabel, binnenLocatieOnderdeelComboBox,
                buitenLocatieOnderdeellabel, buitenLocatieOnderdeelComboBox,
                addButton
        );

        root.add(formBox, 1, 1);
        root.add(backButton, 0, 0);

        loadCategorieen();
        loadLocaties();
        loadBinnenLocatiesOnderdelen();
        loadBuitenLocatiesOnderdelen();

        locatieComboBox.setOnAction(_ -> showLocatieOnderdeel(binnenLocatieOnderdeellabel, buitenLocatieOnderdeellabel));
        addButton.setOnAction(_ -> addAanvraagGemeenteMonument());
    }

    private void showLocatieOnderdeel(Label binnenLocatieOnderdeellabel, Label buitenLocatieOnderdeellabel) {
        Locatie selectedLocatie = locatieComboBox.getSelectionModel().getSelectedItem();
        if (selectedLocatie.getLocatieNaam().equals("Binnenshuis")) {
            binnenLocatieOnderdeellabel.setVisible(true);
            binnenLocatieOnderdeelComboBox.setVisible(true);
            buitenLocatieOnderdeellabel.setVisible(false);
            buitenLocatieOnderdeellabel.setVisible(false);
        } else if (selectedLocatie.getLocatieNaam().equals("Buitenshuis")) {
            buitenLocatieOnderdeellabel.setVisible(true);
            buitenLocatieOnderdeelComboBox.setVisible(true);
            binnenLocatieOnderdeellabel.setVisible(false);
            binnenLocatieOnderdeellabel.setVisible(false);
        } else {
            binnenLocatieOnderdeellabel.setVisible(false);
            binnenLocatieOnderdeellabel.setVisible(false);
            buitenLocatieOnderdeellabel.setVisible(false);
            buitenLocatieOnderdeellabel.setVisible(false);
        }
    }

    private void addAanvraagGemeenteMonument() {
        Categorie selectedCategorie = categorieComboBox.getSelectionModel().getSelectedItem();
        Locatie selectedLocatie = locatieComboBox.getSelectionModel().getSelectedItem();
        BinnenLocatieOnderdeel selectedBinnenLocatieOnderdeel = binnenLocatieOnderdeelComboBox.getSelectionModel().getSelectedItem();
        BuitenLocatieOnderdeel selectedBuitenLocatieOnderdeel = buitenLocatieOnderdeelComboBox.getSelectionModel().getSelectedItem();
        GemeenteMonument selectedGemeenteMonument = gemeenteMonumentComboBox.getSelectionModel().getSelectedItem();

        LocatieOnderdeel locatieOnderdeel;

        if (selectedCategorie == null) {
            validationLabel.setText("Categorie is verplicht");
            return;
        }

        if (selectedLocatie.getLocatieNaam().equals("Binnenshuis")) {
            if (selectedBinnenLocatieOnderdeel == null) {
                validationLabel.setText("Onderdeel / Ruimte / Verdieping is verplicht");
                return;
            }
            locatieOnderdeel = binnenLocatieOnderdeelComboBox.getSelectionModel().getSelectedItem();
        }  else if (selectedLocatie.getLocatieNaam().equals("Buitenshuis")) {
            if (selectedBuitenLocatieOnderdeel == null) {
                validationLabel.setText("Onderdeel / Gevel / Blootstelling is verplicht");
                return;
            }
            locatieOnderdeel = buitenLocatieOnderdeelComboBox.getSelectionModel().getSelectedItem();
        } else {
            validationLabel.setText("Locatie is verplicht");
            return;
        }

        if (selectedGemeenteMonument == null) {
            validationLabel.setText("Adres is verplicht");
            return;
        }

        try {
            if (aanvraagGemeenteMonumentService.add(selectedCategorie, locatieOnderdeel, selectedGemeenteMonument)) {
                validationLabel.setText("Aanvraag verstuurd");
                validationLabel.setStyle("-fx-text-fill: green; -fx-font-size: 18px;");
                categorieComboBox.getSelectionModel().clearSelection();
                binnenLocatieOnderdeelComboBox.getSelectionModel().clearSelection();
                buitenLocatieOnderdeelComboBox.getSelectionModel().clearSelection();
                gemeenteMonumentComboBox.getSelectionModel().clearSelection();
            }
        } catch (RuntimeException e) {
            if ("INVALID_INPUT".equals(e.getMessage())) {
                validationLabel.setText("Ongeldige invoer");
            } else {
                validationLabel.setText("Onverwachte fout opgetreden");
            }
        }
    }

    private void loadCategorieen() {
        try {
            categorieComboBox.getItems().addAll(categorieService.getAll());
        } catch (RuntimeException e) {
            switch (e.getMessage()) {
                case "DATABASE_LOAD_ERROR" ->
                        validationLabel.setText("Databasefout bij laden van Categorieën");
                case "DATABASE_NULL_ERROR" ->
                        validationLabel.setText("Interne fout bij laden van Categorieën");
                default ->
                        validationLabel.setText("Onverwachte fout opgetreden");
            }
        }
    }

    private void loadLocaties() {
        try {
            locatieComboBox.getItems().addAll(locatieService.getAll());
        } catch (RuntimeException e) {
            switch (e.getMessage()) {
                case "DATABASE_LOAD_ERROR" ->
                        validationLabel.setText("Databasefout bij laden van Locaties");
                case "DATABASE_NULL_ERROR" ->
                        validationLabel.setText("Interne fout bij laden van Locaties");
                default ->
                        validationLabel.setText("Onverwachte fout opgetreden");
            }
        }
    }

    private void loadBinnenLocatiesOnderdelen() {
        try {
            binnenLocatieOnderdeelComboBox.getItems().addAll((BinnenLocatieOnderdeel) binnenLocatieOnderdeelService.getAll());
        } catch (RuntimeException e) {
            switch (e.getMessage()) {
                case "DATABASE_LOAD_ERROR" ->
                        validationLabel.setText("Databasefout bij laden van BinnenLocatiesOnderdelen");
                case "DATABASE_NULL_ERROR" ->
                        validationLabel.setText("Interne fout bij laden van BinnenLocatiesOnderdelen");
                default ->
                        validationLabel.setText("Onverwachte fout opgetreden");
            }
        }
    }

    private void loadBuitenLocatiesOnderdelen() {
        try {
            buitenLocatieOnderdeelComboBox.getItems().addAll((BuitenLocatieOnderdeel) buitenLocatieOnderdeelService.getAll());
        } catch (RuntimeException e) {
            switch (e.getMessage()) {
                case "DATABASE_LOAD_ERROR" ->
                        validationLabel.setText("Databasefout bij laden van BuitenLocatiesOnderdelen");
                case "DATABASE_NULL_ERROR" ->
                        validationLabel.setText("Interne fout bij laden van BuitenLocatiesOnderdelen");
                default ->
                        validationLabel.setText("Onverwachte fout opgetreden");
            }
        }
    }
}

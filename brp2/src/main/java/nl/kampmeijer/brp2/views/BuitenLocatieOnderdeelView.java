package nl.kampmeijer.brp2.views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.brp2.models.BuitenLocatieOnderdeel;
import nl.kampmeijer.brp2.models.Locatie;
import nl.kampmeijer.brp2.models.Onderdeel;
import nl.kampmeijer.brp2.services.BuitenLocatieOnderdeelService;
import nl.kampmeijer.brp2.services.OnderdeelService;
import org.jetbrains.annotations.NotNull;

import static nl.kampmeijer.brp2.helpers.ValidationHelper.validateLength;

public class BuitenLocatieOnderdeelView {
    private final TextField gevelNaamField = new TextField(), blootstellingNaamField = new TextField();
    private final Label validationLabel = new Label();
    private final ComboBox<Onderdeel> onderdeelComboBox = new ComboBox<>();
    private final ListView<BuitenLocatieOnderdeel> listview = new ListView<>();
    private final OnderdeelService onderdeelService = new OnderdeelService();
    private final BuitenLocatieOnderdeelService buitenLocatieOnderdeelService = new BuitenLocatieOnderdeelService();

    public BuitenLocatieOnderdeelView(@NotNull GridPane root, Runnable onBack) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        Button backButton = new Button("Terug");
        root.add(backButton, 0, 0);
        root.add(listview, 0, 1, 1, 6);
        Label onderdeelLabel = new Label("Onderdeel:");
        root.add(onderdeelLabel, 1, 1);
        Label gevelNaamLabel = new Label("Gevel:");
        root.add(gevelNaamLabel, 2, 1);
        Label blootstellingnaamLabel = new Label("Blootstelling:");
        root.add(blootstellingnaamLabel, 3, 1);
        root.add(onderdeelComboBox, 1, 2);
        root.add(gevelNaamField, 2, 2);
        root.add(blootstellingNaamField, 3, 2);
        Button addButton = new Button("Toevoegen");
        root.add(addButton, 3, 2);
        Button updateButton = new Button("Aanpassen");
        root.add(updateButton, 3, 3);
        Button deleteButton = new Button("Verwijderen");
        root.add(deleteButton, 3, 4);
        root.add(validationLabel, 2, 3);

        validationLabel.setStyle("-fx-text-fill: red;");

        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(_ -> onBack.run());

        onderdeelLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        gevelNaamLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        blootstellingnaamLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        listview.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(BuitenLocatieOnderdeel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.locatieOnderdeelInfo());
            }
        });

        loadBuitenLocatiesOnderdelen();
        loadOnderdelen();

        addButton.setOnAction(_ -> addBuitenLocatieOnderdeel());
        updateButton.setOnAction(_ -> updateBuitenLocatieOnderdeel());
        deleteButton.setOnAction(_ -> deleteBuitenLocatieOnderdeel());
    }

    private void loadBuitenLocatiesOnderdelen() {
        try {
            listview.getItems().addAll(buitenLocatieOnderdeelService.getAll());
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

    private void addBuitenLocatieOnderdeel() {
        Locatie locatie = new Locatie("Buitenshuis");
        Onderdeel onderdeelNaamBox = onderdeelComboBox.getSelectionModel().getSelectedItem();
        String gevelNaamInput = gevelNaamField.getText().trim();
        String blootstellingNaamInput = blootstellingNaamField.getText().trim();

        if (onderdeelNaamBox == null) {
            validationLabel.setText("Onderdeel is verplicht");
            return;
        }

        if (!validateLength(gevelNaamField, 2, 50, "Gevel is verplicht (2–50 tekens)", validationLabel)) {
            return;
        }

        if (!validateLength(gevelNaamField, 2, 100, "Blootstelling is verplicht (2–100 tekens)", validationLabel)) {
            return;
        }

        try {
            BuitenLocatieOnderdeel newBLO = buitenLocatieOnderdeelService.add(locatie, onderdeelNaamBox, gevelNaamInput, blootstellingNaamInput);
            listview.getItems().add(newBLO);
            clearForm();
        } catch (RuntimeException e) {
            if ("INVALID_INPUT".equals(e.getMessage())) {
                validationLabel.setText("Ongeldige invoer");
            } else {
                validationLabel.setText("Onverwachte fout opgetreden");
            }
        }
    }

    private void updateBuitenLocatieOnderdeel() {
        BuitenLocatieOnderdeel selected = listview.getSelectionModel().getSelectedItem();
        Onderdeel onderdeelNaamBox = onderdeelComboBox.getSelectionModel().getSelectedItem();
        String gevelNaamInput = gevelNaamField.getText().trim();
        String blootstellingNaamInput = blootstellingNaamField.getText().trim();

        if (selected == null) {
            validationLabel.setText("Selecteer eerst een BuitenLocatieOnderdeel");
            return;
        }

        if (onderdeelNaamBox == null) {
            validationLabel.setText("Nieuw Onderdeel is verplicht");
            return;
        }

        if (!validateLength(gevelNaamField, 2, 50, "Voer een geldige nieuwe Gevel naam in (2–50 tekens)", validationLabel)) {
            return;
        }

        if (!validateLength(gevelNaamField, 2, 100, "Voer een geldige nieuwe Blootstelling naam in (2–100 tekens)", validationLabel)) {
            return;
        }

        try {
            if (BuitenLocatieOnderdeelService.update(selected.getLo_id(), onderdeelNaamBox.getOnderdeelNaam(), gevelNaamInput, blootstellingNaamInput)) {
                selected.setOnderdeel(onderdeelNaamBox);
                selected.setGevelNaam(gevelNaamInput);
                selected.setBlootstellingNaam(blootstellingNaamInput);
                listview.refresh();
                onderdeelComboBox.getSelectionModel().clearSelection();
                gevelNaamField.clear();
                blootstellingNaamField.clear();
                validationLabel.setText("");
            }
        } catch (RuntimeException e) {
            validationLabel.setText("Fout bij aanpassen BuitenLocatieOnderdeel");
        }
    }

    private void deleteBuitenLocatieOnderdeel() {
        BuitenLocatieOnderdeel selected = listview.getSelectionModel().getSelectedItem();

        if (selected == null) {
            validationLabel.setText("Selecteer eerst een BuitenLocatieOnderdeel");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bevestigen");
        alert.setHeaderText("BuitenLocatieOnderdeel verwijderen");
        alert.setContentText(
                "Weet je zeker dat je '" + selected.getOnderdeel().getOnderdeelNaam() + " " + selected.getBlootstellingNaam() + " " + selected.getGevelNaam() +  "' wilt verwijderen?"
        );

        if (alert.showAndWait().orElse(null) != javafx.scene.control.ButtonType.OK) {
            return;
        }

        try {
            if (BuitenLocatieOnderdeelService.delete(selected.getLo_id())) {
                listview.getItems().remove(selected);
                validationLabel.setText("");
            }
        } catch (RuntimeException e) {
            validationLabel.setText("Fout bij verwijderen BuitenLocatieOnderdeel");
        }
    }

    private void loadOnderdelen() {
        try {
            onderdeelComboBox.getItems().addAll(onderdeelService.getAll());
        } catch (RuntimeException e) {
            switch (e.getMessage()) {
                case "DATABASE_LOAD_ERROR" ->
                        validationLabel.setText("Databasefout bij laden van Onderdelen");
                case "DATABASE_NULL_ERROR" ->
                        validationLabel.setText("Interne fout bij laden van Onderdelen");
                default ->
                        validationLabel.setText("Onverwachte fout opgetreden");
            }
        }
    }

    private void clearForm() {
        onderdeelComboBox.getSelectionModel().clearSelection();
        gevelNaamField.clear();
        blootstellingNaamField.clear();
        validationLabel.setText("");
    }
}

package nl.kampmeijer.brp2.views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.brp2.models.BinnenLocatieOnderdeel;
import nl.kampmeijer.brp2.models.Locatie;
import nl.kampmeijer.brp2.models.Onderdeel;
import nl.kampmeijer.brp2.services.BinnenLocatieOnderdeelService;
import nl.kampmeijer.brp2.services.OnderdeelService;
import org.jetbrains.annotations.NotNull;

import static nl.kampmeijer.brp2.helpers.ValidationHelper.validateLength;

public class BinnenLocatieOnderdeelView {
    private final TextField ruimteNaamField = new TextField(), verdiepingNummerField = new TextField();
    private final Label validationLabel = new Label();
    private final ComboBox<Onderdeel> onderdeelComboBox = new ComboBox<>();
    private final ListView<BinnenLocatieOnderdeel> listview = new ListView<>();
    private final OnderdeelService onderdeelService = new OnderdeelService();
    private final BinnenLocatieOnderdeelService binnenLocatieOnderdeelService = new BinnenLocatieOnderdeelService();

    public BinnenLocatieOnderdeelView(@NotNull GridPane root) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        Button backButton = new Button("Terug");
        root.add(backButton, 0, 0);
        root.add(listview, 0, 1, 1, 6);
        Label onderdeelLabel = new Label("Onderdeel:");
        root.add(onderdeelLabel, 1, 1);
        Label ruimteNaamLabel = new Label("Ruimte:");
        root.add(ruimteNaamLabel, 2, 1);
        Label verdiepingNummerlabel = new Label("Verdieping:");
        root.add(verdiepingNummerlabel, 3, 1);
        root.add(onderdeelComboBox, 1, 2);
        root.add(ruimteNaamField, 2, 2);
        root.add(verdiepingNummerField, 3, 2);
        Button addButton = new Button("Toevoegen");
        root.add(addButton, 1, 2);
        Button updateButton = new Button("Aanpassen");
        root.add(updateButton, 1, 3);
        Button deleteButton = new Button("Verwijderen");
        root.add(deleteButton, 1, 4);
        root.add(validationLabel, 2, 3);

        validationLabel.setStyle("-fx-text-fill: red;");

        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        //backButton.setOnAction(_ -> onBack.run());

        onderdeelLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        ruimteNaamLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        verdiepingNummerlabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        listview.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(BinnenLocatieOnderdeel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.locatieOnderdeelInfo());
            }
        });

        loadBinnenLocatiesOnderdelen();
        loadOnderdelen();

        addButton.setOnAction(_ -> addBinnenLocatieOnderdeel());
        updateButton.setOnAction(_ -> updateBinnenLocatieOnderdeel());
        deleteButton.setOnAction(_ -> deleteBinnenLocatieOnderdeel());
    }

    private void loadBinnenLocatiesOnderdelen() {
        try {
            listview.getItems().addAll((BinnenLocatieOnderdeel) binnenLocatieOnderdeelService.getAll());
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

    private void addBinnenLocatieOnderdeel() {
        Locatie locatie = new Locatie("Binnenshuis");
        Onderdeel onderdeelNaamBox = onderdeelComboBox.getSelectionModel().getSelectedItem();
        String ruimteNaamInput = ruimteNaamField.getText().trim();
        String verdiepingNummerInput = verdiepingNummerField.getText().trim();

        if (onderdeelNaamBox == null) {
            validationLabel.setText("Onderdeel is verplicht");
            return;
        }

        if (!validateLength(ruimteNaamField, 2, 50, "Ruimte is verplicht (2–50 tekens)", validationLabel)) {
            return;
        }

        if (!validateLength(verdiepingNummerField, 2, 50, "Verdieping is verplicht (2–50 tekens)", validationLabel)) {
            return;
        }

        try {
            BinnenLocatieOnderdeel newBLO = binnenLocatieOnderdeelService.add(locatie, onderdeelNaamBox, ruimteNaamInput, verdiepingNummerInput);
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

    private void updateBinnenLocatieOnderdeel() {
        BinnenLocatieOnderdeel selected = listview.getSelectionModel().getSelectedItem();
        Onderdeel onderdeelNaamBox = onderdeelComboBox.getSelectionModel().getSelectedItem();
        String ruimteNaamInput = ruimteNaamField.getText().trim();
        String verdiepingNummerInput = verdiepingNummerField.getText().trim();

        if (selected == null) {
            validationLabel.setText("Selecteer eerst een BinnenLocatieOnderdeel");
            return;
        }

        if (onderdeelNaamBox == null) {
            validationLabel.setText("Nieuw Onderdeel is verplicht");
            return;
        }

        if (!validateLength(ruimteNaamField, 2, 50, "Voer een geldige nieuwe Ruimte naam in (2–50 tekens)", validationLabel)) {
            return;
        }

        if (!validateLength(ruimteNaamField, 2, 50, "Voer een geldige nieuwe Verdiepingnummer in (2–50 tekens)", validationLabel)) {
            return;
        }

        try {
            if (BinnenLocatieOnderdeelService.update(selected.getLo_id(), onderdeelNaamBox.getOnderdeelNaam(), ruimteNaamInput, verdiepingNummerInput)) {
                selected.setOnderdeel(onderdeelNaamBox);
                selected.setRuimteNaam(ruimteNaamInput);
                selected.setVerdiepingNummer(verdiepingNummerInput);
                listview.refresh();
                clearForm();
            }
        } catch (RuntimeException e) {
            validationLabel.setText("Fout bij aanpassen BinnenLocatieOnderdeel");
        }
    }

    private void deleteBinnenLocatieOnderdeel() {
        BinnenLocatieOnderdeel selected = listview.getSelectionModel().getSelectedItem();

        if (selected == null) {
            validationLabel.setText("Selecteer eerst een BinnenLocatieOnderdeel");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bevestigen");
        alert.setHeaderText("BinnenLocatieOnderdeel verwijderen");
        alert.setContentText(
                "Weet je zeker dat je '" + selected.getOnderdeel().getOnderdeelNaam() + " " + selected.getRuimteNaam() + " " + selected.getVerdiepingNummer() +  "' wilt verwijderen?"
        );

        if (alert.showAndWait().orElse(null) != javafx.scene.control.ButtonType.OK) {
            return;
        }

        try {
            if (BinnenLocatieOnderdeelService.delete(selected.getLo_id())) {
                listview.getItems().remove(selected);
                validationLabel.setText("");
            }
        } catch (RuntimeException e) {
            validationLabel.setText("Fout bij verwijderen BinnenLocatieOnderdeel");
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
        ruimteNaamField.clear();
        verdiepingNummerField.clear();
        validationLabel.setText("");
    }
}

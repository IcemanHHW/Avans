package nl.kampmeijer.fgt2.views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.fgt2.models.Bewoner;
import nl.kampmeijer.fgt2.models.Woontoren;

import java.sql.ResultSet;
import static nl.kampmeijer.fgt2.database.DatabaseHelper.*;

public class BewonerView {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen"), backButton = new Button("Terug");
    private final Label bsnLabel = new Label("BSN:"), nameLabel = new Label("Naam:"), woontorenlabel = new Label("Woontoren:"), appartementNummerLabel = new Label("Huisnummer:"), verdiepingLabel = new Label("Verdieping:");
    private final Label validationLabel = new Label();
    private final TextField bsnField = new TextField(), nameField = new TextField(), appartementNummerField = new TextField(), verdiepingField = new TextField();
    private final ComboBox<Woontoren> woontorenComboBox = new ComboBox<>();
    private final ListView<Bewoner> listview = new ListView<>();

    /**
     * Constructs the Bewoner view and initializes the UI.
     *
     * @param root   the root GridPane
     * @param onBack callback executed when the back button is pressed
     */
    public BewonerView(GridPane root, Runnable onBack) {

        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(backButton, 0, 0);
        root.add(listview, 0, 1, 1, 6);
        root.add(bsnLabel, 1, 1);
        root.add(nameLabel, 2, 1);
        root.add(woontorenlabel, 3, 1);
        root.add(appartementNummerLabel, 4, 1);
        root.add(verdiepingLabel, 5, 1);
        root.add(bsnField, 1, 2);
        root.add(nameField, 2, 2);
        root.add(woontorenComboBox, 3, 2);
        root.add(appartementNummerField, 4, 2);
        root.add(verdiepingField, 5, 2);
        root.add(updateButton, 1, 3);
        root.add(deleteButton, 1, 4);
        root.add(validationLabel, 2, 3);

        validationLabel.setStyle("-fx-text-fill: red;");

        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(_ -> onBack.run());

        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        loadBewoners();
        loadWoontorens();

        addButton.setOnAction(_ -> addBewoner());
        updateButton.setOnAction(_ -> updateBewoner());
        deleteButton.setOnAction(_ -> deleteBewoner());
    }

    /**
     * Loads all bewoners from the database into the ListView.
     */
    private void loadBewoners() {
        try {
            ResultSet r = getData("SELECT * FROM bewoners");

            while (r.next()) {
                Bewoner b = new Bewoner();
                b.setBsn(r.getString("bsn"));
                b.setNm(r.getString("nm"));
                b.setPprtmntnr(r.getString("pprtmntnr"));
                b.setVrdpng(r.getString("vrdpng"));
                b.setWntrnnm(r.getString("wntrnnm"));
                listview.getItems().add(b);
            }
        } catch (java.sql.SQLException e) {
            System.err.println("SQL-fout bij ophalen bewoners: " + e.getMessage());
            validationLabel.setText("Databasefout bij laden van bewoners.");
        } catch (NullPointerException e) {
            System.err.println("Null-waarde bij ophalen bewoners.");
            validationLabel.setText("Interne fout bij laden van gegevens.");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            validationLabel.setText("Onverwachte fout opgetreden.");
        }
    }

    /**
     * Adds a new bewoner to the database and ListView.
     */
    private void addBewoner() {
        String bsnInput = bsnField.getText().trim();
        if (isValidInput(bsnInput)) {
            validationLabel.setText("BSN is verplicht (2–50 tekens).");
            return;
        }

        String nameInput = nameField.getText().trim();
        if (isValidInput(bsnInput)) {
            validationLabel.setText("BSN is verplicht (2–50 tekens).");
            return;
        }

        String appartementNummerInput = appartementNummerField.getText().trim();
        if (isValidInput2(bsnInput)) {
            validationLabel.setText("Huisnummer is verplicht (1–3 tekens).");
            return;
        }

        String verdiepingInput = appartementNummerField.getText().trim();
        if (isValidInput2(bsnInput)) {
            validationLabel.setText("Huisnummer is verplicht (1–3 tekens).");
            return;
        }

        Woontoren woontoren = woontorenComboBox.getSelectionModel().getSelectedItem();
        if (woontoren == null) {
            validationLabel.setText("Woontoren is verplicht.");
            return;
        }

        try {
            int result = insertData("INSERT INTO bouwbedrijven (bsn, nm, pprtmntnr, vrdpng, wntrnnm) VALUES ('" + bsnInput + ", " + nameInput + ", " + appartementNummerInput + ", " + verdiepingInput + ", " + woontoren.getWntrnnm() "')");
            if (result > 0) {
                Bewoner b = new Bewoner();
                b.setBsn(bsnInput);
                b.setNm(nameInput);
                b.setPprtmntnr(verdiepingInput);
                b.setVrdpng(verdiepingInput);
                b.setWntrnnm(woontoren.getWntrnnm());
                listview.getItems().add(b);
                bsnField.clear();
                nameField.clear();
                appartementNummerField.clear();
                verdiepingField.clear();
                woontorenComboBox.getSelectionModel().clearSelection();
                validationLabel.setText("");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Ongeldige invoer: " + e.getMessage());
            validationLabel.setText("Ongeldige invoer.");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            validationLabel.setText("Onverwachte fout opgetreden.");
        }
    }

    /**
     * Updates the selected bouwbedrijf with a new name.
     */
    private void updateBewoner() {
        Bewoner selected = listview.getSelectionModel().getSelectedItem();
        String input = textField.getText().trim();
        if (selected == null) {
            validationLabel.setText("Selecteer eerst een bewoner.");
            return;
        }
        if (isValidInput(input)) {
            validationLabel.setText("Voer een geldige nieuwe naam in.");
            return;
        }
        try {
            int result = updateData("UPDATE bouwbedrijven SET nm = '" + input + "' WHERE bsn = '" + selected.getBsn() + "'");
            if (result > 0) {
                selected.setNm(input);
                listview.refresh();
                textField.clear();
                validationLabel.setText("");
            }
        } catch (NullPointerException e) {
            System.err.println("Null bij aanpassen bouwbedrijf.");
            validationLabel.setText("Interne fout opgetreden.");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            validationLabel.setText("Onverwachte fout opgetreden.");
        }
    }

    /**
     * Deletes the selected bewoner from the database and ListView.
     */
    private void deleteBewoner() {
        Bewoner selected = listview.getSelectionModel().getSelectedItem();

        if (selected == null) {
            validationLabel.setText("Selecteer eerst een bewoner.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bevestigen");
        alert.setHeaderText("Bewoner verwijderen");
        alert.setContentText(
                "Weet je zeker dat je '" + selected.getNm() + "' wilt verwijderen?"
        );

        if (alert.showAndWait().orElse(null) != javafx.scene.control.ButtonType.OK) {
            return;
        }

        try {
            int result = updateData("DELETE FROM bouwbedrijven WHERE bsn = '" + selected.getBsn() + "'");
            if (result > 0) {
                listview.getItems().remove(selected);
                validationLabel.setText("");
            }
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            validationLabel.setText("Onverwachte fout opgetreden.");
        }
    }

    /**
     * Loads all woontorens from the database into the comboBox.
     */
    private void loadWoontorens() {
        try {
            ResultSet r = getData("SELECT * FROM woontorens");

            while (r.next()) {
                Woontoren w = new Woontoren();
                w.setWntrnnm(r.getString("wntrnnm"));
                woontorenComboBox.getItems().add(w);
            }
        } catch (java.sql.SQLException e) {
            System.err.println("SQL-fout bij ophalen woontorens: " + e.getMessage());
            validationLabel.setText("Databasefout bij laden van woontorens.");
        } catch (NullPointerException e) {
            System.err.println("Null-waarde bij ophalen woontorens.");
            validationLabel.setText("Interne fout bij laden van gegevens.");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            validationLabel.setText("Onverwachte fout opgetreden.");
        }
    }

    /**
     * Validates the input
     *
     * @param input the input string
     * @return true if valid, false otherwise
     */
    private boolean isValidInput(String input) {
        return input != null && input.length() >= 2 && input.length() <= 50;
    }

    /**
     * Validates the input
     *
     * @param input the input string
     * @return true if valid, false otherwise
     */
    private boolean isValidInput2(String input) {
        return input != null && !input.isEmpty() && input.length() <= 3;
    }
}

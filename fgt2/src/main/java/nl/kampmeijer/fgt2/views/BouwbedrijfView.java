package nl.kampmeijer.fgt2.views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.fgt2.models.Bouwbedrijf;
import java.sql.ResultSet;
import static nl.kampmeijer.fgt2.database.DatabaseHelper.*;

public class BouwbedrijfView {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen"), backButton = new Button("Terug");
    private final Label bouwbedrijfLabel = new Label("Naam:");
    private final Label validationLabel = new Label();
    private final TextField textField = new TextField();
    private final ListView<Bouwbedrijf> listview = new ListView<>();

    /**
     * Constructs the Bouwbedrijf view and initializes the UI.
     *
     * @param root   the root GridPane
     * @param onBack callback executed when the back button is pressed
     */
    public BouwbedrijfView(GridPane root, Runnable onBack) {

        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(backButton, 0, 0);
        root.add(listview, 0, 1, 1, 6);
        root.add(bouwbedrijfLabel, 1, 1);
        root.add(textField, 1, 2);
        root.add(addButton, 1, 3);
        root.add(updateButton, 1, 4);
        root.add(deleteButton, 1, 5);
        root.add(validationLabel, 1, 6);

        validationLabel.setStyle("-fx-text-fill: red;");

        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(_ -> onBack.run());

        bouwbedrijfLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        loadBouwbedrijven();

        addButton.setOnAction(_ -> addBouwbedrijf());
        updateButton.setOnAction(_ -> updateBouwbedrijf());
        deleteButton.setOnAction(_ -> deleteBouwbedrijf());
    }

    /**
     * Loads all bouwbedrijven from the database into the ListView.
     */
    private void loadBouwbedrijven() {
        try {
            ResultSet r = getData("SELECT * FROM bouwbedrijven");

            while (r.next()) {
                Bouwbedrijf b = new Bouwbedrijf();
                b.setBwbrfnm(r.getString("bwbrfnm"));
                listview.getItems().add(b);
            }
        } catch (java.sql.SQLException e) {
            System.err.println("SQL-fout bij ophalen bouwbedrijven: " + e.getMessage());
            validationLabel.setText("Databasefout bij laden van bouwbedrijven.");
        } catch (NullPointerException e) {
            System.err.println("Null-waarde bij ophalen bouwbedrijven.");
            validationLabel.setText("Interne fout bij laden van gegevens.");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            validationLabel.setText("Onverwachte fout opgetreden.");
        }
    }

    /**
     * Adds a new bouwbedrijf to the database and ListView.
     */
    private void addBouwbedrijf() {
        String input = textField.getText().trim();
        if (isValidInput(input)) {
            validationLabel.setText("Naam is verplicht (2–50 tekens).");
            return;
        }

        try {
            int result = insertData("INSERT INTO bouwbedrijven (bwbrfnm) VALUES ('" + input + "')");
            if (result > 0) {
                Bouwbedrijf b = new Bouwbedrijf();
                b.setBwbrfnm(input);
                listview.getItems().add(b);
                textField.clear();
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
    private void updateBouwbedrijf() {
        Bouwbedrijf selected = listview.getSelectionModel().getSelectedItem();
        String input = textField.getText().trim();
        if (selected == null) {
            validationLabel.setText("Selecteer eerst een bouwbedrijf.");
            return;
        }
        if (isValidInput(input)) {
            validationLabel.setText("Voer een geldige nieuwe naam in.");
            return;
        }
        try {
            int result = updateData("UPDATE bouwbedrijven SET bwbrfnm = '" + input + "' WHERE bwbrfnm = '" + selected.getBwbrfnm() + "'");
            if (result > 0) {
                selected.setBwbrfnm(input);
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
     * Deletes the selected bouwbedrijf from the database and ListView.
     */
    private void deleteBouwbedrijf() {
        Bouwbedrijf selected = listview.getSelectionModel().getSelectedItem();

        if (selected == null) {
            validationLabel.setText("Selecteer eerst een bouwbedrijf.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bevestigen");
        alert.setHeaderText("Bouwbedrijf verwijderen");
        alert.setContentText(
                "Weet je zeker dat je '" + selected.getBwbrfnm() + "' wilt verwijderen?"
        );

        if (alert.showAndWait().orElse(null) != javafx.scene.control.ButtonType.OK) {
            return;
        }

        try {
            int result = updateData("DELETE FROM bouwbedrijven WHERE bwbrfnm = '" + selected.getBwbrfnm() + "'");
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
     * Validates the input
     *
     * @param input the input string
     * @return true if valid, false otherwise
     */
    private boolean isValidInput(String input) {
        return input != null && input.length() >= 2 && input.length() <= 50;
    }
}

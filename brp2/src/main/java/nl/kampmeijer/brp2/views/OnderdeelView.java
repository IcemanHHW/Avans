package nl.kampmeijer.brp2.views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.brp2.models.Onderdeel;
import org.jetbrains.annotations.NotNull;
import java.sql.ResultSet;
import static nl.kampmeijer.brp2.database.DatabaseHelper.*;

public class OnderdeelView {
    private final Label validationLabel = new Label();
    private final TextField textField = new TextField();
    private final ListView<Onderdeel> listview = new ListView<>();

    public OnderdeelView(@NotNull GridPane root) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        Button backButton = new Button("Terug");
        root.add(backButton, 0, 0);
        root.add(listview, 0, 1, 1, 6);
        Label onderdeelLabel = new Label("Onderdeel:");
        root.add(onderdeelLabel, 1, 1);
        root.add(textField, 1, 2);
        Button addButton = new Button("Toevoegen");
        root.add(addButton, 1, 3);
        Button updateButton = new Button("Aanpassen");
        root.add(updateButton, 1, 4);
        Button deleteButton = new Button("Verwijderen");
        root.add(deleteButton, 1, 5);
        root.add(validationLabel, 1, 6);

        validationLabel.setStyle("-fx-text-fill: red;");

        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        //backButton.setOnAction(_ -> onBack.run());

        onderdeelLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        loadOnderdelen();

        addButton.setOnAction(_ -> addOnderdeel());
        updateButton.setOnAction(_ -> updateOnderdeel());
        deleteButton.setOnAction(_ -> deleteOnderdeel());
    }

    private void loadOnderdelen() {
        try {
            ResultSet r = getData("SELECT * FROM onderdelen");

            while (r.next()) {
                Onderdeel o = new Onderdeel(r.getString("onderdeelNaam"));
                listview.getItems().add(o);
            }
        } catch (java.sql.SQLException e) {
            System.err.println("SQL-fout bij ophalen onderdelen: " + e.getMessage());
            validationLabel.setText("Databasefout bij laden van onderdelen");
        } catch (NullPointerException e) {
            System.err.println("Null-waarde bij ophalen onderdelen");
            validationLabel.setText("Interne fout bij laden van gegevens");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            validationLabel.setText("Onverwachte fout opgetreden");
        }
    }

    private void addOnderdeel() {
        String input = textField.getText().trim();

        if (isValidInput(input)) {
            validationLabel.setText("Onderdeel is verplicht (2–50 tekens)");
            return;
        }

        try {
            int result = insertData("INSERT INTO onderdelen (onderdeelNaam) VALUES ('" + input + "')");
            if (result > 0) {
                Onderdeel o = new Onderdeel(input);
                listview.getItems().add(o);
                textField.clear();
                validationLabel.setText("");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Ongeldige invoer: " + e.getMessage());
            validationLabel.setText("Ongeldige invoer");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            validationLabel.setText("Onverwachte fout opgetreden");
        }
    }

    private void updateOnderdeel() {
        Onderdeel selected = listview.getSelectionModel().getSelectedItem();
        String input = textField.getText().trim();
        if (selected == null) {
            validationLabel.setText("Selecteer eerst een onderdeel");
            return;
        }
        if (isValidInput(input)) {
            validationLabel.setText("Voer een geldige nieuwe onderdeel naam in.");
            return;
        }
        try {
            int result = updateData("UPDATE onderdelen SET onderdeelNaam = '" + input + "' WHERE onderdeelNaam = '" + selected.getOnderdeelNaam() + "'");
            if (result > 0) {
                selected.setOnderdeelNaam(input);
                listview.refresh();
                textField.clear();
                validationLabel.setText("");
            }
        } catch (NullPointerException e) {
            System.err.println("Null bij aanpassen onderdeel");
            validationLabel.setText("Interne fout opgetreden");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            validationLabel.setText("Onverwachte fout opgetreden");
        }
    }

    private void deleteOnderdeel() {
        Onderdeel selected = listview.getSelectionModel().getSelectedItem();

        if (selected == null) {
            validationLabel.setText("Selecteer eerst een onderdeel");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bevestigen");
        alert.setHeaderText("Onderdeel verwijderen");
        alert.setContentText(
                "Weet je zeker dat je '" + selected.getOnderdeelNaam() + "' wilt verwijderen?"
        );

        if (alert.showAndWait().orElse(null) != javafx.scene.control.ButtonType.OK) {
            return;
        }

        try {
            int result = updateData("DELETE FROM onderdelen WHERE onderdeelNaam = '" + selected.getOnderdeelNaam() + "'");
            if (result > 0) {
                listview.getItems().remove(selected);
                validationLabel.setText("");
            }
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            validationLabel.setText("Onverwachte fout opgetreden");
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

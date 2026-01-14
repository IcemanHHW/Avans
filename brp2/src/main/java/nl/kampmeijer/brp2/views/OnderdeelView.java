package nl.kampmeijer.brp2.views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.brp2.models.Onderdeel;
import nl.kampmeijer.brp2.services.OnderdeelService;
import org.jetbrains.annotations.NotNull;

public class OnderdeelView {
    private final Label validationLabel = new Label();
    private final TextField textField = new TextField();
    private final ListView<Onderdeel> listview = new ListView<>();
    private final OnderdeelService onderdeelService = new OnderdeelService();

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
            listview.getItems().addAll(onderdeelService.getAll());
        } catch (RuntimeException e) {
            switch (e.getMessage()) {
                case "DATABASE_LOAD_ERROR" ->
                        validationLabel.setText("Databasefout bij laden van onderdelen");
                case "DATABASE_NULL_ERROR" ->
                        validationLabel.setText("Interne fout bij laden van gegevens");
                default ->
                        validationLabel.setText("Onverwachte fout opgetreden");
            }
        }
    }

    private void addOnderdeel() {
        String input = textField.getText().trim();

        if (isValidInput(input)) {
            validationLabel.setText("Onderdeel is verplicht (2–50 tekens)");
            return;
        }

        try {
            if (onderdeelService.add(input)) {
                listview.getItems().add(new Onderdeel(input));
                textField.clear();
                validationLabel.setText("");
            }
        } catch (RuntimeException e) {
            if ("INVALID_INPUT".equals(e.getMessage())) {
                validationLabel.setText("Ongeldige invoer");
            } else {
                validationLabel.setText("Onverwachte fout opgetreden");
            }
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
            validationLabel.setText("Voer een geldige nieuwe onderdeel naam in");
            return;
        }

        try {
            if (onderdeelService.update(selected.getOnderdeelNaam(), input)) {
                selected.setOnderdeelNaam(input);
                listview.refresh();
                textField.clear();
                validationLabel.setText("");
            }
        } catch (RuntimeException e) {
            validationLabel.setText("Fout bij aanpassen onderdeel");
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
            if (onderdeelService.delete(selected.getOnderdeelNaam())) {
                listview.getItems().remove(selected);
                validationLabel.setText("");
            }
        } catch (RuntimeException e) {
            validationLabel.setText("Fout bij verwijderen onderdeel");
        }
    }

    /**
     * Validates the input
     *
     * @param input the input string
     * @return true if valid, false otherwise
     */
    private boolean isValidInput(String input) {

        return input == null || input.length() < 2 || input.length() > 50;
    }
}

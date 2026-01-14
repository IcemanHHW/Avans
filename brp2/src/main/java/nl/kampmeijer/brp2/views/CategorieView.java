package nl.kampmeijer.brp2.views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.brp2.models.Categorie;
import org.jetbrains.annotations.NotNull;
import nl.kampmeijer.brp2.services.CategorieService;

public class CategorieView {
    private final Label validationLabel = new Label();
    private final TextField textField = new TextField();
    private final ListView<Categorie> listview = new ListView<>();
    private final CategorieService categorieService = new CategorieService();

    public CategorieView(@NotNull GridPane root) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        Button backButton = new Button("Terug");
        root.add(backButton, 0, 0);
        root.add(listview, 0, 1, 1, 6);
        Label categorieLabel = new Label("Categorie:");
        root.add(categorieLabel, 1, 1);
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

        categorieLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        loadCategorieen();

        addButton.setOnAction(_ -> addCategorie());
        updateButton.setOnAction(_ -> updateCategorie());
        deleteButton.setOnAction(_ -> deleteCategorie());
    }

    private void loadCategorieen() {
        try {
            listview.getItems().addAll(categorieService.getAll());
        } catch (RuntimeException e) {
            switch (e.getMessage()) {
                case "DATABASE_LOAD_ERROR" ->
                        validationLabel.setText("Databasefout bij laden van Categorieën");
                case "DATABASE_NULL_ERROR" ->
                        validationLabel.setText("Interne fout bij laden van gegevens");
                default ->
                        validationLabel.setText("Onverwachte fout opgetreden");
            }
        }
    }

    private void addCategorie() {
        String input = textField.getText().trim();

        if (isValidInput(input)) {
            validationLabel.setText("Categorie is verplicht (2–50 tekens)");
            return;
        }

        try {
            if (categorieService.add(input)) {
                listview.getItems().add(new Categorie(input));
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

    private void updateCategorie() {
        Categorie selected = listview.getSelectionModel().getSelectedItem();
        String input = textField.getText().trim();

        if (selected == null) {
            validationLabel.setText("Selecteer eerst een Categorie");
            return;
        }

        if (isValidInput(input)) {
            validationLabel.setText("Voer een geldige nieuwe Categorie naam in");
            return;
        }

        try {
            if (categorieService.update(selected.getCategorieNaam(), input)) {
                selected.setCategorieNaam(input);
                listview.refresh();
                textField.clear();
                validationLabel.setText("");
            }
        } catch (RuntimeException e) {
            validationLabel.setText("Fout bij aanpassen Categorie");
        }
    }

    private void deleteCategorie() {
        Categorie selected = listview.getSelectionModel().getSelectedItem();

        if (selected == null) {
            validationLabel.setText("Selecteer eerst een Categorie");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bevestigen");
        alert.setHeaderText("Categorie verwijderen");
        alert.setContentText(
                "Weet je zeker dat je '" + selected.getCategorieNaam() + "' wilt verwijderen?"
        );

        if (alert.showAndWait().orElse(null) != javafx.scene.control.ButtonType.OK) {
            return;
        }

        try {
            if (categorieService.delete(selected.getCategorieNaam())) {
                listview.getItems().remove(selected);
                validationLabel.setText("");
            }
        } catch (RuntimeException e) {
            validationLabel.setText("Fout bij verwijderen Categorie");
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

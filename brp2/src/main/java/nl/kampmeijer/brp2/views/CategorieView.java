package nl.kampmeijer.brp2.views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.brp2.models.Categorie;
import org.jetbrains.annotations.NotNull;
import java.sql.ResultSet;
import static nl.kampmeijer.brp2.database.DatabaseHelper.*;

public class CategorieView {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen"), backButton = new Button("Terug");
    private final Label categorieLabel = new Label("Categorie:");
    private final Label validationLabel = new Label();
    private final TextField textField = new TextField();
    private final ListView<Categorie> listview = new ListView<>();

    public CategorieView(@NotNull GridPane root) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(backButton, 0, 0);
        root.add(listview, 0, 1, 1, 6);
        root.add(categorieLabel, 1, 1);
        root.add(textField, 1, 2);
        root.add(addButton, 1, 3);
        root.add(updateButton, 1, 4);
        root.add(deleteButton, 1, 5);
        root.add(validationLabel, 1, 6);

        validationLabel.setStyle("-fx-text-fill: red;");

        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        //backButton.setOnAction(_ -> onBack.run());

        categorieLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        loadCategories();

        addButton.setOnAction(_ -> addCategorie());
        updateButton.setOnAction(_ -> updateCategorie());
        deleteButton.setOnAction(_ -> deleteCategorie());
    }

    private void loadCategories() {
        try {
            ResultSet r = getData("SELECT * FROM categories");

            while (r.next()) {
                Categorie c = new Categorie(r.getString("categorieNaam"));
                c.setCategorieNaam(r.getString("categorieNaam"));
                listview.getItems().add(c);
            }
        } catch (java.sql.SQLException e) {
            System.err.println("SQL-fout bij ophalen categorieën: " + e.getMessage());
            validationLabel.setText("Databasefout bij laden van categorieën.");
        } catch (NullPointerException e) {
            System.err.println("Null-waarde bij ophalen categorieën.");
            validationLabel.setText("Interne fout bij laden van gegevens.");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            validationLabel.setText("Onverwachte fout opgetreden.");
        }
    }

    private void addCategorie() {
        String input = textField.getText().trim();

        try {
            int result = insertData("INSERT INTO categories (categorieNaam) VALUES ('" + input + "')");
            if (result > 0) {
                Categorie c = new Categorie(input);
                listview.getItems().add(c);
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
}

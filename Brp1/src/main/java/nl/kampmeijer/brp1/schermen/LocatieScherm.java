package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.brp1.models.Locatie;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;

import static nl.kampmeijer.brp1.database.DatabaseHelper.*;

public class LocatieScherm {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen"), backButton = new Button("Terug");
    private final Label locatieLabel = new Label("Locatie:");
    private final TextField textField = new TextField();
    private final ListView<Locatie> listview = new ListView<>();
    private final Label validationLabel = new Label();

    public LocatieScherm(@NotNull GridPane root, Runnable onBack) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(backButton, 0, 0);
        root.add(listview, 0, 1, 1, 5);
        root.add(locatieLabel, 1, 1);
        root.add(textField, 1, 2);
        root.add(addButton, 1, 3);
        root.add(updateButton, 1, 4);
        root.add(deleteButton, 1, 5);
        root.add(validationLabel, 2, 3);

        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(_ -> onBack.run());

        locatieLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
        validationLabel.setVisible(false);

        ResultSet r;
        ArrayList<Locatie> allLocaties = new ArrayList<>();
        try {
            r = getData("select * from locaties");
            while (r.next()) {
                Locatie l = new Locatie();
                l.setId(r.getInt("id"));
                l.setLocatienaam(r.getString("locatienaam"));
                allLocaties.add(l);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van locaties");
            se.printStackTrace();
        }

        for (Locatie l : allLocaties) {
            listview.getItems().add(l);
        }

        addButton.setOnAction(_ -> {
            String input = textField.getText();

            StringBuilder errorMsg = new StringBuilder();

            if (input.isEmpty()) errorMsg.append("Locatie is leeg");

            if (!errorMsg.isEmpty()) {
                validationLabel.setText(errorMsg.toString());
                validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                validationLabel.setVisible(true);
                return;
            }

            if (!input.isEmpty()) {
                int iResult = insertData("INSERT INTO locaties(locatienaam) values ('" + input + "')");
                System.out.println(iResult + " rij toegevoegd");
                if (iResult > 0) {
                    Locatie newLocatie = new Locatie();
                    newLocatie.setLocatienaam(input);
                    listview.getItems().add(newLocatie);
                    textField.clear();
                    validationLabel.setText("Locatie is toegevoegd");
                    validationLabel.setStyle("-fx-text-fill: green; -fx-font-size: 18px;");
                    validationLabel.setVisible(true);
                } else {
                    validationLabel.setText("Er is iets misgegaan bij het opslaan");
                    validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                    validationLabel.setVisible(true);
                }
            }
        });

        updateButton.setOnAction(_ -> {
            String input = textField.getText();
            Locatie selectedItem = listview.getSelectionModel().getSelectedItem();

            StringBuilder errorMsg = new StringBuilder();

            if (input.isEmpty()) errorMsg.append("Locatie is leeg");
            if (selectedItem == null) errorMsg.append("Selecteer eerst een item om aan te passen");

            if (!errorMsg.isEmpty()) {
                validationLabel.setText(errorMsg.toString());
                validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                validationLabel.setVisible(true);
                return;
            }

            if (!input.isEmpty()) {
                int iResult = updateData("UPDATE locaties SET locatienaam = '" + input + "' WHERE id = " + selectedItem.getId());
                System.out.println(iResult + " rij aangepast");
                if (iResult > 0) {
                    selectedItem.setLocatienaam(input);
                    listview.refresh();
                    textField.clear();
                    validationLabel.setText("Locatie is aangepast");
                    validationLabel.setStyle("-fx-text-fill: green; -fx-font-size: 18px;");
                    validationLabel.setVisible(true);
                } else {
                    validationLabel.setText("Er is iets misgegaan bij het opslaan");
                    validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                    validationLabel.setVisible(true);
                }
            }
        });

        deleteButton.setOnAction(_ -> {
            Locatie selectedItem = listview.getSelectionModel().getSelectedItem();

            StringBuilder errorMsg = new StringBuilder();

            if (selectedItem == null) errorMsg.append("Selecteer eerst een item om aan te passen");

            if (!errorMsg.isEmpty()) {
                validationLabel.setText(errorMsg.toString());
                validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                validationLabel.setVisible(true);
                return;
            }

            int iResult = updateData("DELETE FROM locaties WHERE id = " + selectedItem.getId());
            System.out.println(iResult + " rij verwijderd");
            if (iResult > 0) {
                validationLabel.setText("Verwijderen is gelukt");
                validationLabel.setStyle("-fx-text-fill: green; -fx-font-size: 18px;");
                validationLabel.setVisible(true);
                listview.getItems().remove(selectedItem);
                listview.refresh();
            } else {
                validationLabel.setText("Er is iets misgegaan bij het verwijderen");
                validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                validationLabel.setVisible(true);
            }
        });
    }
}
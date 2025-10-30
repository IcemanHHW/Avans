package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.brp1.models.Soort;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;

import static nl.kampmeijer.brp1.database.DatabaseHelper.*;

public class SoortScherm {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen"), backButton = new Button("Terug");
    private final Label soortLabel = new Label("Soort:");
    private final TextField textField = new TextField();
    private final ListView<Soort> listview = new ListView<>();
    private final Label validationLabel = new Label();

    public SoortScherm(@NotNull GridPane root, Runnable onBack) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(backButton, 0, 0);
        root.add(listview, 0, 1, 1, 5);
        root.add(soortLabel, 1, 1);
        root.add(textField, 1, 2);
        root.add(addButton, 1, 3);
        root.add(updateButton, 1, 4);
        root.add(deleteButton, 1, 5);
        root.add(validationLabel, 2, 3);

        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(_ -> onBack.run());

        soortLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
        validationLabel.setVisible(false);

        ResultSet r;
        ArrayList<Soort> allSoorts = new ArrayList<>();
        try {
            r = getData("select * from soorten");
            while (r.next()) {
                Soort s = new Soort();
                s.setId(r.getInt("id"));
                s.setSoortnaam(r.getString("soortnaam"));
                allSoorts.add(s);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van soorten");
            se.printStackTrace();
        }

        for (Soort s : allSoorts) {
            listview.getItems().add(s);
        }

        addButton.setOnAction(_ -> {
            String input = textField.getText();

            StringBuilder errorMsg = new StringBuilder();

            if (input.isEmpty()) errorMsg.append("Buurtbewoner is leeg");

            if (!errorMsg.isEmpty()) {
                validationLabel.setText(errorMsg.toString());
                validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                validationLabel.setVisible(true);
                return;
            }

            if (!input.isEmpty()) {
                int iResult = insertData("INSERT INTO soorten(soortnaam) values ('" + input + "')");
                System.out.println(iResult + " rij toegevoegd");
                if (iResult > 0) {
                    Soort newSoort = new Soort();
                    newSoort.setSoortnaam(input);
                    listview.getItems().add(newSoort);
                    textField.clear();
                    validationLabel.setText("Soort is toegevoegd");
                    validationLabel.setStyle("-fx-text-fill: green; -fx-font-size: 18px;");
                    validationLabel.setVisible(true);
                } else  {
                    validationLabel.setText("Er is iets misgegaan bij het opslaan");
                    validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                    validationLabel.setVisible(true);
                }
            }
        });

        updateButton.setOnAction(_ -> {
            String input = textField.getText();
            Soort selectedItem = listview.getSelectionModel().getSelectedItem();

            StringBuilder errorMsg = new StringBuilder();

            if (input.isEmpty()) errorMsg.append("Soort is leeg");
            if (selectedItem == null) errorMsg.append("Selecteer eerst een item om aan te passen");

            if (!errorMsg.isEmpty()) {
                validationLabel.setText(errorMsg.toString());
                validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                validationLabel.setVisible(true);
                return;
            }

            if (!input.isEmpty()) {
                int iResult = updateData("UPDATE soorten SET soortnaam = '" + input + "' WHERE id = " + selectedItem.getId());
                System.out.println(iResult + " rij aangepast");
                if (iResult > 0) {
                    selectedItem.setSoortnaam(input);
                    listview.refresh();
                    textField.clear();
                    validationLabel.setText("Soort is aangepast");
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
            Soort selectedItem = listview.getSelectionModel().getSelectedItem();

            StringBuilder errorMsg = new StringBuilder();

            if (selectedItem == null) errorMsg.append("Selecteer eerst een item om aan te passen");

            if (!errorMsg.isEmpty()) {
                validationLabel.setText(errorMsg.toString());
                validationLabel.setVisible(true);
                return;
            }

            int iResult = updateData("DELETE FROM soorten WHERE id = " + selectedItem.getId());
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
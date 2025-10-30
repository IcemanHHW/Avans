package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.brp1.models.Buurtbewoner;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;

import static nl.kampmeijer.brp1.database.DatabaseHelper.*;

public class BuurtbewonerScherm {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen"), backButton = new Button("Terug");
    private final Label buurtbewonerLabel = new Label("Buurtbewoner:");
    private final TextField textField = new TextField();
    private final ListView<Buurtbewoner> listview = new ListView<>();
    private final Label validationLabel = new Label();

    public BuurtbewonerScherm(@NotNull GridPane root, Runnable onBack) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(backButton, 0, 0);
        root.add(listview, 0, 1, 1, 5);
        root.add(buurtbewonerLabel, 1, 1);
        root.add(textField, 1, 2);
        root.add(addButton, 1, 3);
        root.add(updateButton, 1, 4);
        root.add(deleteButton, 1, 5);
        root.add(validationLabel, 2, 3);

        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(_ -> onBack.run());

        buurtbewonerLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
        validationLabel.setVisible(false);

        ResultSet r;
        ArrayList<Buurtbewoner> allBuurtbewoners = new ArrayList<>();
        try {
            r = getData("select * from buurtbewoners");
            while (r.next()) {
                Buurtbewoner b = new Buurtbewoner();
                b.setId(r.getInt("id"));
                b.setBuurtbewonernaam(r.getString("buurtbewonernaam"));
                allBuurtbewoners.add(b);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van buurtbewoners");
            se.printStackTrace();
        }

        for (Buurtbewoner b : allBuurtbewoners) {
            listview.getItems().add(b);
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
                int iResult = insertData("INSERT INTO buurtbewoners(buurtbewonernaam) values ('" + input + "')");
                if (iResult > 0) {
                    Buurtbewoner newBuurtbewoner = new Buurtbewoner();
                    newBuurtbewoner.setBuurtbewonernaam(input);
                    listview.getItems().add(newBuurtbewoner);
                    textField.clear();
                    validationLabel.setText("Buurtbewoner is toegevoegd");
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
            Buurtbewoner selectedItem = listview.getSelectionModel().getSelectedItem();

            StringBuilder errorMsg = new StringBuilder();

            if (input.isEmpty()) errorMsg.append("Buurtbewoner is leeg");
            if (selectedItem == null) errorMsg.append("Selecteer eerst een item om aan te passen");

            if (!errorMsg.isEmpty()) {
                validationLabel.setText(errorMsg.toString());
                validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                validationLabel.setVisible(true);
                return;
            }

            if (!input.isEmpty()) {
                int iResult = updateData("UPDATE buurtbewoners SET buurtbewonernaam = '" + input + "' WHERE id = " + selectedItem.getId());
                System.out.println(iResult + " rij aangepast");
                if (iResult > 0) {
                    selectedItem.setBuurtbewonernaam(input);
                    listview.refresh();
                    textField.clear();
                    validationLabel.setText("Buurtbewoner is aangepast");
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
            Buurtbewoner selectedItem = listview.getSelectionModel().getSelectedItem();

            StringBuilder errorMsg = new StringBuilder();

            if (selectedItem == null) errorMsg.append("Selecteer eerst een item om aan te passen");

            if (!errorMsg.isEmpty()) {
                validationLabel.setText(errorMsg.toString());
                validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                validationLabel.setVisible(true);
                return;
            }

            int iResult = updateData("DELETE FROM buurtbewoners WHERE id = " + selectedItem.getId());
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
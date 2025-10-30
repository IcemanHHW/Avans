package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.brp1.models.Starttijd;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static nl.kampmeijer.brp1.database.DatabaseHelper.*;

public class StarttijdScherm {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen"), backButton = new Button("Terug");
    private final Label starttijdLabel = new Label("Starttijd:");
    private final TextField timeField = new TextField();
    private final ListView<Starttijd> listview = new ListView<>();
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final Label validationLabel = new Label();

    public StarttijdScherm(@NotNull GridPane root, Runnable onBack) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(backButton, 0, 0);
        root.add(listview, 0, 1, 1, 5);
        root.add(starttijdLabel, 1, 1);
        root.add(timeField, 1, 2);
        root.add(addButton, 1, 3);
        root.add(updateButton, 1, 4);
        root.add(deleteButton, 1, 5);
        root.add(validationLabel, 2, 3);

        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(_ -> onBack.run());

        starttijdLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
        validationLabel.setVisible(false);

        // Only allow HH:mm format
        timeField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("([01]?\\d|2[0-3]):[0-5]?\\d?")) { // Regex for HH:mm format
                return change;
            }
            return null;
        }));

        ResultSet r;
        ArrayList<Starttijd> allStarttijds = new ArrayList<>();
        try {
            r = getData("select * from starttijden");
            while (r.next()) {
                Starttijd s = new Starttijd();
                s.setId(r.getInt("id"));
                s.setStarttijd(r.getString("starttijd"));
                allStarttijds.add(s);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van starttijden");
            se.printStackTrace();
        }

        for (Starttijd s : allStarttijds) {
            listview.getItems().add(s);
        }

        addButton.setOnAction(_ -> {
            String input = timeField.getText();

            StringBuilder errorMsg = new StringBuilder();

            if (input.isEmpty()) errorMsg.append("Starttijd is leeg");

            if (!errorMsg.isEmpty()) {
                validationLabel.setText(errorMsg.toString());
                validationLabel.setVisible(true);
                return;
            }

            if (!input.isEmpty()) {
                try {
                    LocalTime time = LocalTime.parse(input, timeFormatter);
                    int iResult = insertData("INSERT INTO starttijden(starttijd) VALUES ('" + time + "')");
                    System.out.println(iResult + " rij toegevoegd");
                    if (iResult > 0) {
                        Starttijd newStarttijd = new Starttijd();
                        newStarttijd.setStarttijd(time.toString());
                        listview.getItems().add(newStarttijd);
                        timeField.clear();
                        validationLabel.setText("Starttijd is toegevoegd");
                        validationLabel.setStyle("-fx-text-fill: green; -fx-font-size: 18px;");
                        validationLabel.setVisible(true);
                    } else  {
                        validationLabel.setText("Er is iets misgegaan bij het opslaan");
                        validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                        validationLabel.setVisible(true);
                    }
                } catch (Exception e) {
                    validationLabel.setText("Ongeldige tijd: " + input);
                    validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px");
                    validationLabel.setVisible(true);
                }
            }
        });

        updateButton.setOnAction(_ -> {
            String input = timeField.getText();
            Starttijd selectedItem = listview.getSelectionModel().getSelectedItem();
            StringBuilder errorMsg = new StringBuilder();

            if (input.isEmpty()) errorMsg.append("Buurtbewoner is leeg");
            if (selectedItem == null) errorMsg.append("Selecteer eerst een item om aan te passen");

            if (!errorMsg.isEmpty()) {
                validationLabel.setText(errorMsg.toString());
                validationLabel.setVisible(true);
                return;
            }

            if (!input.isEmpty()) {
                try {
                    LocalTime time = LocalTime.parse(input, timeFormatter);
                    int iResult = updateData("UPDATE starttijden SET starttijd = '" + time + "' WHERE id = " + selectedItem.getId());
                    System.out.println(iResult + " rij aangepast");
                    if (iResult > 0) {
                        selectedItem.setStarttijd(time.toString());
                        listview.refresh();
                        timeField.clear();
                        validationLabel.setText("Starttijd is aangepast");
                        validationLabel.setStyle("-fx-text-fill: green; -fx-font-size: 18px;");
                        validationLabel.setVisible(true);
                    } else {
                        validationLabel.setText("Er is iets misgegaan bij het opslaan");
                        validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                        validationLabel.setVisible(true);
                    }
                } catch (Exception e) {
                    validationLabel.setText("Ongeldige tijd: " + input);
                    validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                    validationLabel.setVisible(true);
                }
            }
        });

        deleteButton.setOnAction(_ -> {
            Starttijd selectedItem = listview.getSelectionModel().getSelectedItem();

            StringBuilder errorMsg = new StringBuilder();

            if (selectedItem == null) errorMsg.append("Selecteer eerst een item om aan te passen");

            if (!errorMsg.isEmpty()) {
                validationLabel.setText(errorMsg.toString());
                validationLabel.setVisible(true);
                return;
            }

            int iResult = updateData("DELETE FROM starttijden WHERE id = " + selectedItem.getId());
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
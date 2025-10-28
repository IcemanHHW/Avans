package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import nl.kampmeijer.brp1.models.Starttijd;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static nl.kampmeijer.brp1.database.DatabaseHelper.*;

public class StarttijdScherm {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen");
    private final TextField timeField = new TextField();
    private final ListView<Starttijd> listview = new ListView<>();
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public StarttijdScherm(@NotNull GridPane root) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(listview, 0, 0, 1, 4);
        root.add(timeField, 1, 0);
        root.add(addButton, 1, 1);
        root.add(updateButton, 1, 2);
        root.add(deleteButton, 1, 3);

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
                    }
                } catch (Exception e) {
                    System.out.println("Ongeldige tijd: " + input);
                }
            }
        });

        updateButton.setOnAction(_ -> {
            String input = timeField.getText();
            Starttijd selectedItem = listview.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om aan te passen.");
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
                    }
                } catch (Exception e) {
                    System.out.println("Ongeldige tijd: " + input);
                }
            }
        });

        deleteButton.setOnAction(_ -> {
            Starttijd selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om te verwijderen");
                return;
            }

            int iResult = updateData("DELETE FROM starttijden WHERE id = " + selectedItem.getId());
            System.out.println(iResult + " rij verwijderd");
            if (iResult > 0) {
                listview.getItems().remove(selectedItem);
                listview.refresh();
            }
        });
    }
}
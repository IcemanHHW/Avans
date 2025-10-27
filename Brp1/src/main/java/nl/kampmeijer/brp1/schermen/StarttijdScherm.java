package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import nl.kampmeijer.brp1.models.Starttijd;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;

import static nl.kampmeijer.brp1.database.DatabaseHelper.*;

public class StarttijdScherm {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen");
    private final TextField textField = new TextField();
    private final ListView<Starttijd> listview = new ListView<>();

    public StarttijdScherm(@NotNull GridPane root) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(listview, 0, 0, 1, 4);
        root.add(textField, 1, 0);
        root.add(addButton, 1, 1);
        root.add(updateButton, 1, 2);
        root.add(deleteButton, 1, 3);

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
            String input = textField.getText();

            if (!input.isEmpty()) {
                int iResult = insertData("INSERT INTO starttijden(starttijd) values ('" + input + "')");
                System.out.println(iResult + " rij toegevoegd");
                if (iResult > 0) {
                    Starttijd newStarttijd = new Starttijd();
                    newStarttijd.setStarttijd(input);
                    listview.getItems().add(newStarttijd);
                    textField.clear();
                }
            }
        });

        updateButton.setOnAction(_ -> {
            String input = textField.getText();
            Starttijd selectedItem = listview.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om aan te passen.");
                return;
            }

            if (!input.isEmpty()) {
                int iResult = updateData("UPDATE starttijden SET starttijd = '" + input + "' WHERE id = " + selectedItem.getId());
                System.out.println(iResult + " rij aangepast");
                if (iResult > 0) {
                    selectedItem.setStarttijd(input);
                    listview.refresh();
                    textField.clear();
                }
            }
        });

        deleteButton.setOnAction(_ -> {
            Starttijd selectedItem = listview.getSelectionModel().getSelectedItem();
            int iResult = updateData("DELETE FROM starttijden WHERE id = " + selectedItem.getId());
            System.out.println(iResult + " rij verwijderd");
            if (iResult > 0) {
                listview.getItems().remove(selectedItem);
                listview.refresh();
            }
        });
    }
}
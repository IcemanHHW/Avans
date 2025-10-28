package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import nl.kampmeijer.brp1.models.Locatie;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;

import static nl.kampmeijer.brp1.database.DatabaseHelper.*;

public class LocatieScherm {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen");
    private final TextField textField = new TextField();
    private final ListView<Locatie> listview = new ListView<>();

    public LocatieScherm(@NotNull GridPane root) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(listview, 0, 0, 1, 4);
        root.add(textField, 1, 0);
        root.add(addButton, 1, 1);
        root.add(updateButton, 1, 2);
        root.add(deleteButton, 1, 3);

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

            if (!input.isEmpty()) {
                int iResult = insertData("INSERT INTO locaties(locatienaam) values ('" + input + "')");
                System.out.println(iResult + " rij toegevoegd");
                if (iResult > 0) {
                    Locatie newLocatie = new Locatie();
                    newLocatie.setLocatienaam(input);
                    listview.getItems().add(newLocatie);
                    textField.clear();
                }
            }
        });

        updateButton.setOnAction(_ -> {
            String input = textField.getText();
            Locatie selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om aan te passen.");
                return;
            }

            if (!input.isEmpty()) {
                int iResult = updateData("UPDATE locaties SET locatienaam = '" + input + "' WHERE id = " + selectedItem.getId());
                System.out.println(iResult + " rij aangepast");
                if (iResult > 0) {
                    selectedItem.setLocatienaam(input);
                    listview.refresh();
                    textField.clear();
                }
            }
        });

        deleteButton.setOnAction(_ -> {
            Locatie selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om te verwijderen");
                return;
            }

            int iResult = updateData("DELETE FROM locaties WHERE id = " + selectedItem.getId());
            System.out.println(iResult + " rij verwijderd");
            if (iResult > 0) {
                listview.getItems().remove(selectedItem);
                listview.refresh();
            }
        });
    }
}
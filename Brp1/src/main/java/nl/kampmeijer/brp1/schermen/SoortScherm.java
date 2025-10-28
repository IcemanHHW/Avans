package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import nl.kampmeijer.brp1.models.Soort;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;

import static nl.kampmeijer.brp1.database.DatabaseHelper.*;

public class SoortScherm {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen");
    private final TextField textField = new TextField();
    private final ListView<Soort> listview = new ListView<>();

    public SoortScherm(@NotNull GridPane root) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(listview, 0, 0, 1, 4);
        root.add(textField, 1, 0);
        root.add(addButton, 1, 1);
        root.add(updateButton, 1, 2);
        root.add(deleteButton, 1, 3);

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

            if (!input.isEmpty()) {
                int iResult = insertData("INSERT INTO soorten(soortnaam) values ('" + input + "')");
                System.out.println(iResult + " rij toegevoegd");
                if (iResult > 0) {
                    Soort newSoort = new Soort();
                    newSoort.setSoortnaam(input);
                    listview.getItems().add(newSoort);
                    textField.clear();
                }
            }
        });

        updateButton.setOnAction(_ -> {
            String input = textField.getText();
            Soort selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om aan te passen.");
                return;
            }

            if (!input.isEmpty()) {
                int iResult = updateData("UPDATE soorten SET soortnaam = '" + input + "' WHERE id = " + selectedItem.getId());
                System.out.println(iResult + " rij aangepast");
                if (iResult > 0) {
                    selectedItem.setSoortnaam(input);
                    listview.refresh();
                    textField.clear();
                }
            }
        });

        deleteButton.setOnAction(_ -> {
            Soort selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om te verwijderen");
                return;
            }

            int iResult = updateData("DELETE FROM soorten WHERE id = " + selectedItem.getId());
            System.out.println(iResult + " rij verwijderd");
            if (iResult > 0) {
                listview.getItems().remove(selectedItem);
                listview.refresh();
            }
        });
    }
}
package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import nl.kampmeijer.brp1.models.Variant;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;

import static nl.kampmeijer.brp1.database.DatabaseHelper.*;

public class VariantScherm {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen");
    private final TextField textField = new TextField();
    private final ListView<Variant> listview = new ListView<>();

    public VariantScherm(@NotNull GridPane root) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(listview, 0, 0, 1, 4);
        root.add(textField, 1, 0);
        root.add(addButton, 1, 1);
        root.add(updateButton, 1, 2);
        root.add(deleteButton, 1, 3);

        ResultSet r;
        ArrayList<Variant> allVariants = new ArrayList<>();
        try {
            r = getData("select * from varianten");
            while (r.next()) {
                Variant v = new Variant();
                v.setId(r.getInt("id"));
                v.setVariantnaam(r.getString("variantnaam"));
                allVariants.add(v);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van varianten");
            se.printStackTrace();
        }

        for (Variant v : allVariants) {
            listview.getItems().add(v);
        }

        addButton.setOnAction(_ -> {
            String input = textField.getText();

            if (!input.isEmpty()) {
                int iResult = insertData("INSERT INTO varianten(variantnaam) values ('" + input + "')");
                System.out.println(iResult + " rij toegevoegd");
                if (iResult > 0) {
                    Variant newVariant = new Variant();
                    newVariant.setVariantnaam(input);
                    listview.getItems().add(newVariant);
                    textField.clear();
                }
            }
        });

        updateButton.setOnAction(_ -> {
            String input = textField.getText();
            Variant selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om aan te passen.");
                return;
            }

            if (!input.isEmpty()) {
                int iResult = updateData("UPDATE varianten SET variantnaam = '" + input + "' WHERE id = " + selectedItem.getId());
                System.out.println(iResult + " rij aangepast");
                if (iResult > 0) {
                    selectedItem.setVariantnaam(input);
                    listview.refresh();
                    textField.clear();
                }
            }
        });

        deleteButton.setOnAction(_ -> {
            Variant selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om aan te passen.");
                return;
            }

            int iResult = updateData("DELETE FROM varianten WHERE id = " + selectedItem.getId());
            System.out.println(iResult + " rij verwijderd");
            if (iResult > 0) {
                listview.getItems().remove(selectedItem);
                listview.refresh();
            }
        });
    }
}
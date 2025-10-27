package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import nl.kampmeijer.brp1.models.Buurtbewoner;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;

import static nl.kampmeijer.brp1.database.DatabaseHelper.*;

public class BuurtbewonerScherm {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen");
    private final TextField textField = new TextField();
    private final ListView<Buurtbewoner> listview = new ListView<>();

    public BuurtbewonerScherm(@NotNull GridPane root) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(listview, 0, 0, 1, 4);
        root.add(textField, 1, 0);
        root.add(addButton, 1, 1);
        root.add(updateButton, 1, 2);
        root.add(deleteButton, 1, 3);

        ResultSet r;
        ArrayList<Buurtbewoner> allBuurtbewoners = new ArrayList<>();
        try {
            r = getData("select * from datums");
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

            if (!input.isEmpty()) {
                int iResult = insertData("INSERT INTO buurtbewoners(buurtbewonernaam) values ('" + input + "')");
                System.out.println(iResult + " rij toegevoegd");
                if (iResult > 0) {
                    Buurtbewoner newBuurtbewoner = new Buurtbewoner();
                    newBuurtbewoner.setBuurtbewonernaam(input);
                    listview.getItems().add(newBuurtbewoner);
                    textField.clear();
                }
            }
        });

        updateButton.setOnAction(_ -> {
            String input = textField.getText();
            Buurtbewoner selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om aan te passen.");
                return;
            }

            if (!input.isEmpty()) {
                int iResult = updateData("UPDATE buurtbewoners SET buurtbewonernaam = '" + input + "' WHERE id = " + selectedItem.getId());
                System.out.println(iResult + " rij aangepast");
                if (iResult > 0) {
                    selectedItem.setBuurtbewonernaam(input);
                    listview.refresh();
                    textField.clear();
                }
            }
        });

        deleteButton.setOnAction(_ -> {
            Buurtbewoner selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om aan te passen.");
                return;
            }

            int iResult = updateData("DELETE FROM buurtbewoners WHERE id = " + selectedItem.getId());
            System.out.println(iResult + " rij verwijderd");
            if (iResult > 0) {
                listview.getItems().remove(selectedItem);
                listview.refresh();
            }
        });
    }
}
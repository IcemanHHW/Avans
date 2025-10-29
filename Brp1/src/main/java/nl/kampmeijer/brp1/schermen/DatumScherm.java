package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.brp1.models.Datum;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import static nl.kampmeijer.brp1.database.DatabaseHelper.*;

public class DatumScherm {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen"), backButton = new Button("Terug");
    private final Label datumLabel = new Label("Datum:");
    private DatePicker datePicker = new DatePicker();
    private final ListView<Datum> listview = new ListView<>();

    public DatumScherm(@NotNull GridPane root, Runnable onBack) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(backButton, 0, 0);
        root.add(listview, 0, 1, 1, 5);
        root.add(datumLabel, 1, 1);
        root.add(datePicker, 1, 2);
        root.add(addButton, 1, 3);
        root.add(updateButton, 1, 4);
        root.add(deleteButton, 1, 5);

        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(_ -> onBack.run());

        datumLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        ResultSet r;
        ArrayList<Datum> allDatums = new ArrayList<>();
        try {
            r = getData("select * from datums");
            while (r.next()) {
                Datum d = new Datum();
                d.setId(r.getInt("id"));
                d.setDatum(r.getString("datum"));
                allDatums.add(d);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van datums");
            se.printStackTrace();
        }

        for (Datum d : allDatums) {
            listview.getItems().add(d);
        }

        addButton.setOnAction(_ -> {
            LocalDate input = datePicker.getValue();

            if (input != null) {
                int iResult = insertData("INSERT INTO datums(datum) values ('" + input + "')");
                System.out.println(iResult + " rij toegevoegd");
                if (iResult > 0) {
                    Datum newDatum = new Datum();
                    newDatum.setDatum(input.toString());
                    listview.getItems().add(newDatum);
                    datePicker.setValue(null);
                }
            }
        });

        updateButton.setOnAction(_ -> {
            LocalDate input = datePicker.getValue();
            Datum selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om aan te passen.");
                return;
            }

            if (input != null) {
                int iResult = updateData("UPDATE datums SET datum = '" + input + "' WHERE id = " + selectedItem.getId());
                System.out.println(iResult + " rij aangepast");
                if (iResult > 0) {
                    selectedItem.setDatum(String.valueOf(input));
                    listview.refresh();
                    datePicker.setValue(null);
                }
            }
        });

        deleteButton.setOnAction(_ -> {
            Datum selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om te verwijderen");
                return;
            }

            int iResult = updateData("DELETE FROM datums WHERE id = " + selectedItem.getId());
            System.out.println(iResult + " rij verwijderd");
            if (iResult > 0) {
                listview.getItems().remove(selectedItem);
                listview.refresh();
            }
        });
    }
}
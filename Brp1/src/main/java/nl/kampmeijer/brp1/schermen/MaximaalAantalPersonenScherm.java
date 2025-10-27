package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import nl.kampmeijer.brp1.models.MaximaalAantalPersonen;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;

import static nl.kampmeijer.brp1.database.DatabaseHelper.*;

public class MaximaalAantalPersonenScherm {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen");
    private final TextField numberField = new TextField();
    private final ListView<MaximaalAantalPersonen> listview = new ListView<>();

    public MaximaalAantalPersonenScherm(@NotNull GridPane root) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(listview, 0, 0, 1, 4);
        root.add(numberField, 1, 0);
        root.add(addButton, 1, 1);
        root.add(updateButton, 1, 2);
        root.add(deleteButton, 1, 3);

        // Only allow numbers
        numberField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // regex for numbers
                return change;
            }
            return null;
        }));

        ResultSet r;
        ArrayList<MaximaalAantalPersonen> allMAPS = new ArrayList<>();
        try {
            r = getData("select * from maximaalaantalpersonen");
            while (r.next()) {
                MaximaalAantalPersonen m = new MaximaalAantalPersonen();
                m.setId(r.getInt("id"));
                m.setMaximimumnummer(r.getInt("maximumnummer"));
                allMAPS.add(m);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van maximaalaantalpersonen");
            se.printStackTrace();
        }

        for (MaximaalAantalPersonen m : allMAPS) {
            listview.getItems().add(m);
        }

        addButton.setOnAction(_ -> {
            String input = numberField.getText();

            if (!input.isEmpty()) {
                try {
                    int number = Integer.parseInt(input); // Ensure it's an integer
                    int iResult = insertData("INSERT INTO maximaalaantalpersonen(maximumnummer) VALUES (" + number + ")");
                    System.out.println(iResult + " rij toegevoegd");

                    if (iResult > 0) {
                        MaximaalAantalPersonen newMAP = new MaximaalAantalPersonen();
                        newMAP.setMaximimumnummer(number);
                        listview.getItems().add(newMAP);
                        numberField.clear();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ongeldige invoer: voer een geheel getal in.");
                }
            }
        });

        updateButton.setOnAction(_ -> {
            String input = numberField.getText();
            MaximaalAantalPersonen selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om aan te passen.");
                return;
            }

            if (!input.isEmpty()) {
                try {
                    int number = Integer.parseInt(input); // Ensure it's an integer
                    int iResult = updateData("UPDATE maximaalaantalpersonen SET maximumnummer = " + number + " WHERE id = " + selectedItem.getId());
                    System.out.println(iResult + " rij aangepast");

                    if (iResult > 0) {
                        selectedItem.setMaximimumnummer(number);
                        listview.refresh();
                        numberField.clear();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ongeldige invoer: voer een geheel getal in.");
                }
            }
        });

        deleteButton.setOnAction(_ -> {
            MaximaalAantalPersonen selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om te verwijderen.");
                return;
            }

            int iResult = updateData("DELETE FROM maximaalaantalpersonen WHERE id = " + selectedItem.getId());
            System.out.println(iResult + " rij verwijderd");
            if (iResult > 0) {
                listview.getItems().remove(selectedItem);
                listview.refresh();
            }
        });

    }
}
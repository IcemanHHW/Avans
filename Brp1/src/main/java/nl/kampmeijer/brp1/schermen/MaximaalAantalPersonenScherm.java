package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.brp1.models.MaximaalAantalPersonen;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;

import static nl.kampmeijer.brp1.database.DatabaseHelper.*;

public class MaximaalAantalPersonenScherm {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen"), backButton = new Button("Terug");
    private final Label mapLabel = new Label("Maximaal aantal personen:");
    private final TextField numberField = new TextField();
    private final ListView<MaximaalAantalPersonen> listview = new ListView<>();

    public MaximaalAantalPersonenScherm(@NotNull GridPane root, Runnable onBack) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(backButton, 0, 0);
        root.add(listview, 0, 1, 1, 5);
        root.add(mapLabel, 1, 1);
        root.add(numberField, 1, 2);
        root.add(addButton, 1, 3);
        root.add(updateButton, 1, 4);
        root.add(deleteButton, 1, 5);

        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(_ -> onBack.run());

        mapLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

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
            r = getData("select * from maximaalaantelpersonen");
            while (r.next()) {
                MaximaalAantalPersonen m = new MaximaalAantalPersonen();
                m.setId(r.getInt("id"));
                m.setMaximumnummer(r.getInt("maximumnummer"));
                allMAPS.add(m);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van maximaalaantelpersonen");
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
                    int iResult = insertData("INSERT INTO maximaalaantelpersonen(maximumnummer) VALUES (" + number + ")");
                    System.out.println(iResult + " rij toegevoegd");

                    if (iResult > 0) {
                        MaximaalAantalPersonen newMAP = new MaximaalAantalPersonen();
                        newMAP.setMaximumnummer(number);
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
                    int iResult = updateData("UPDATE maximaalaantelpersonen SET maximumnummer = " + number + " WHERE id = " + selectedItem.getId());
                    System.out.println(iResult + " rij aangepast");

                    if (iResult > 0) {
                        selectedItem.setMaximumnummer(number);
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

            int iResult = updateData("DELETE FROM maximaalaantelpersonen WHERE id = " + selectedItem.getId());
            System.out.println(iResult + " rij verwijderd");
            if (iResult > 0) {
                listview.getItems().remove(selectedItem);
                listview.refresh();
            }
        });

    }
}
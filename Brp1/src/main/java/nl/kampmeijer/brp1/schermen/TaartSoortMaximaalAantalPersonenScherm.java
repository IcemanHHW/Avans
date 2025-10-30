package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.brp1.models.*;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;

import static nl.kampmeijer.brp1.database.DatabaseHelper.*;

public class TaartSoortMaximaalAantalPersonenScherm {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen"), backButton = new Button("Terug");
    private final Label soortLabel = new Label("Soort:"), mapLabel = new Label("Maximaal aantel personen:");
    private final ComboBox<Soort> soortBox = new ComboBox<>();
    private final ComboBox<MaximaalAantalPersonen> mapBox = new ComboBox<>();
    private final ListView<TaartSoortMaximaalAantalPersonen> listview = new ListView<>();
    private final Label validationLabel = new Label();

    public TaartSoortMaximaalAantalPersonenScherm(@NotNull GridPane root, Runnable onBack) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(backButton, 0, 0);
        root.add(listview, 0, 1, 1, 5);
        root.add(soortLabel, 1, 1);
        root.add(mapLabel, 2, 1);
        root.add(soortBox, 1, 2);
        root.add(mapBox, 2, 2);
        root.add(addButton, 1, 3);
        root.add(updateButton, 1, 4);
        root.add(deleteButton, 1, 5);
        root.add(validationLabel, 2, 3);

        soortLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        mapLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
        validationLabel.setVisible(false);

        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(_ -> onBack.run());

        ResultSet r;
        ArrayList<TaartSoortMaximaalAantalPersonen> allTSMAPs = new ArrayList<>();
        try {
            r = getData("""
        SELECT tsmap.soort_id,
               tsmap.maxpers_id,
               s.soortnaam AS soort_name,
               map.maximumnummer AS maxpers_number
        FROM taartsoortenmaximaalaantelpersonen tsmap
        JOIN soorten s ON (s.id = tsv.soort_id)
        JOIN maximaalaantelpersonen map ON (map.id = tsmap.maxpers_id)
    """);
            while (r.next()) {
                TaartSoortMaximaalAantalPersonen tsmap = new TaartSoortMaximaalAantalPersonen();
                tsmap.setSoort_id(r.getInt("soort_id"));
                tsmap.setMaxpers_id(r.getInt("maxpers_id"));
                tsmap.setSoort_name(r.getString("soort_name"));
                tsmap.setMaxpers_number(r.getInt("maxpers_number"));
                allTSMAPs.add(tsmap);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van taartsoortenmaximaalaantelpersonen");
            se.printStackTrace();
        }

        for (TaartSoortMaximaalAantalPersonen tsmap : allTSMAPs) {
            listview.getItems().add(tsmap);
        }

        loadSoorts();
        loadMaxPers();

        addButton.setOnAction(_ -> {
            Soort soort = soortBox.getSelectionModel().getSelectedItem();
            MaximaalAantalPersonen maximaalAantalPersonen = mapBox.getSelectionModel().getSelectedItem();

            StringBuilder errorMsg = new StringBuilder();

            if (soort == null) errorMsg.append("Selecteer een soort\n");
            if (maximaalAantalPersonen == null) errorMsg.append("Selecteer een maximumnummer\n");

            if (!errorMsg.isEmpty()) {
                validationLabel.setText(errorMsg.toString());
                validationLabel.setVisible(true);
                return;
            }

            if (soort != null && maximaalAantalPersonen != null) {
                int iResult = insertData("INSERT INTO taartsoortenmaximaalaantelpersonen(soort_id, maxpers_id) " +
                        "VALUES (" + soort.getId() + ", " + maximaalAantalPersonen.getId() + ")");
                System.out.println(iResult + " rij toegevoegd");
                if (iResult > 0) {
                    TaartSoortMaximaalAantalPersonen newTSMAP = new TaartSoortMaximaalAantalPersonen();
                    newTSMAP.setSoort_name(soort.getSoortnaam());
                    newTSMAP.setMaxpers_number(maximaalAantalPersonen.getMaximumnummer());
                    listview.getItems().add(newTSMAP);
                    soortBox.getSelectionModel().clearSelection();
                    mapBox.getSelectionModel().clearSelection();
                    validationLabel.setText("Nieuwe combinatie is toegevoegd");
                    validationLabel.setStyle("-fx-text-fill: green; -fx-font-size: 18px;");
                    validationLabel.setVisible(true);
                } else  {
                    validationLabel.setText("Er is iets misgegaan bij het opslaan");
                    validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                    validationLabel.setVisible(true);
                }
            }
        });

        updateButton.setOnAction(_ -> {
            Soort soort = soortBox.getSelectionModel().getSelectedItem();
            MaximaalAantalPersonen maximaalAantalPersonen = mapBox.getSelectionModel().getSelectedItem();
            TaartSoortMaximaalAantalPersonen selectedItem = listview.getSelectionModel().getSelectedItem();

            StringBuilder errorMsg = new StringBuilder();


            if (soort == null) errorMsg.append("Selecteer een soort\n");
            if (maximaalAantalPersonen == null) errorMsg.append("Selecteer een maximumnummer\n");
            if (selectedItem == null) {
                errorMsg.append("Selecteer eerst een item om aan te passen");
                return;
            }

            if (!errorMsg.isEmpty()) {
                validationLabel.setText(errorMsg.toString());
                validationLabel.setVisible(true);
                return;
            }

            if (soort != null && maximaalAantalPersonen != null) {
                int iResult = updateData(
                        "UPDATE taartsoortenmaximaalaantelpersonen SET " +
                        "soort_id = " + soort.getId() + ", " +
                        "maxpers_id = " + maximaalAantalPersonen.getId() +
                        " WHERE soort_id = " + selectedItem.getSoort_id() +
                        " AND maxpers_id = " + selectedItem.getMaxpers_id()
                );
                System.out.println(iResult + " rij aangepast");
                if (iResult > 0) {
                    selectedItem.setSoort_name(soort.getSoortnaam());
                    selectedItem.setMaxpers_number(maximaalAantalPersonen.getMaximumnummer());
                    listview.refresh();
                    soortBox.getSelectionModel().clearSelection();
                    mapBox.getSelectionModel().clearSelection();
                    validationLabel.setText("Combinatie is aangepast");
                    validationLabel.setStyle("-fx-text-fill: green; -fx-font-size: 18px;");
                    validationLabel.setVisible(true);
                } else  {
                    validationLabel.setText("Er is iets misgegaan bij het aanpassen");
                    validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                    validationLabel.setVisible(true);
                }
            }
        });

        deleteButton.setOnAction(_ -> {
            TaartSoortMaximaalAantalPersonen selectedItem = listview.getSelectionModel().getSelectedItem();

            StringBuilder errorMsg = new StringBuilder();

            if (selectedItem == null) {
                errorMsg.append("Selecteer eerst een item om te verwijderen");
                return;
            }

            int iResult = updateData(
                    "DELETE FROM taartsoortenmaximaalaantelpersonen " +
                            "WHERE soort_id = " + selectedItem.getSoort_id() +
                            " AND maxpers_id = " + selectedItem.getMaxpers_id()
            );
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
    private void loadSoorts() {
        ResultSet r;
        try {
            r = getData("select * from soorten");
            while (r.next()) {
                Soort s = new Soort();
                s.setId(r.getInt("id"));
                s.setSoortnaam(r.getString("soortnaam"));
                soortBox.getItems().add(s);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van soorten");
            se.printStackTrace();
        }
    }

    private void loadMaxPers() {
        ResultSet r;
        try {
            r = getData("select * from maximaalaantelpersonen");
            while (r.next()) {
                MaximaalAantalPersonen map = new MaximaalAantalPersonen();
                map.setId(r.getInt("id"));
                map.setMaximumnummer(r.getInt("maximumnummer"));
                mapBox.getItems().add(map);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van maximaalaantelpersonen");
            se.printStackTrace();
        }
    }
}
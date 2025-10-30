package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.brp1.models.Soort;
import nl.kampmeijer.brp1.models.Variant;
import nl.kampmeijer.brp1.models.TaartSoortVariant;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;

import static nl.kampmeijer.brp1.database.DatabaseHelper.*;

public class TaartSoortVariantScherm {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen"), backButton = new Button("Terug");
    private final Label soortLabel = new Label("Soort:"), variantLabel = new Label("Variant:");
    private final ComboBox<Soort> soortBox = new ComboBox<>();
    private final ComboBox<Variant> variantBox = new ComboBox<>();
    private final ListView<TaartSoortVariant> listview = new ListView<>();
    private final Label validationLabel = new Label();

    public TaartSoortVariantScherm(@NotNull GridPane root, Runnable onBack) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(backButton, 0, 0);
        root.add(listview, 0, 1, 1, 5);
        root.add(soortLabel, 1, 1);
        root.add(variantLabel, 2, 1);
        root.add(soortBox, 1, 2);
        root.add(variantBox, 2, 2);
        root.add(addButton, 1, 3);
        root.add(updateButton, 1, 4);
        root.add(deleteButton, 1, 5);
        root.add(validationLabel, 2, 3);

        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(_ -> onBack.run());

        soortLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        variantLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
        validationLabel.setVisible(false);

        ResultSet r;
        ArrayList<TaartSoortVariant> allTSVs = new ArrayList<>();
        try {
            r = getData("""
        SELECT tsv.soort_id,
               tsv.variant_id,
               s.soortnaam AS soort_name,
               v.variantnaam AS variant_name
        FROM taartsoortenvarianten tsv
        JOIN soorten s ON (s.id = tsv.soort_id)
        JOIN varianten v ON (v.id = tsv.variant_id)
    """);
            while (r.next()) {
                TaartSoortVariant tsv = new TaartSoortVariant();
                tsv.setSoort_id(r.getInt("soort_id"));
                tsv.setVariant_id(r.getInt("variant_id"));
                tsv.setSoort_name(r.getString("soort_name"));
                tsv.setVariant_name(r.getString("variant_name"));
                allTSVs.add(tsv);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van taartsoortenvarianten");
            se.printStackTrace();
        }

        for (TaartSoortVariant tsv : allTSVs) {
            listview.getItems().add(tsv);
        }

        loadSoorts();
        loadVariants();

        addButton.setOnAction(_ -> {
            Soort soort = soortBox.getSelectionModel().getSelectedItem();
            Variant variant = variantBox.getSelectionModel().getSelectedItem();

            StringBuilder errorMsg = new StringBuilder();

            if (soort == null) errorMsg.append("Selecteer een soort\n");
            if (variant == null) errorMsg.append("Selecteer een variat\n");

            if (!errorMsg.isEmpty()) {
                validationLabel.setText(errorMsg.toString());
                validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                validationLabel.setVisible(true);
                return;
            }

            if (soort != null && variant != null) {
                int iResult = insertData("INSERT INTO taartsoortenvarianten(soort_id, variant_id) " +
                        "VALUES (" + soort.getId() + ", " + variant.getId() + ")");
                System.out.println(iResult + " rij toegevoegd");
                if (iResult > 0) {
                    TaartSoortVariant newTSV = new TaartSoortVariant();
                    newTSV.setSoort_name(soort.getSoortnaam());
                    newTSV.setVariant_name(variant.getVariantnaam());
                    listview.getItems().add(newTSV);
                    soortBox.getSelectionModel().clearSelection();
                    variantBox.getSelectionModel().clearSelection();
                }
            }
        });

        updateButton.setOnAction(_ -> {
            Soort soort = soortBox.getSelectionModel().getSelectedItem();
            Variant variant = variantBox.getSelectionModel().getSelectedItem();
            TaartSoortVariant selectedItem = listview.getSelectionModel().getSelectedItem();

            StringBuilder errorMsg = new StringBuilder();

            if (soort == null) errorMsg.append("Selecteer een soort\n");
            if (variant == null) errorMsg.append("Selecteer een variant\n");
            if (selectedItem == null) {
                errorMsg.append("Selecteer eerst een item om aan te passen");
                return;
            }

            if (!errorMsg.isEmpty()) {
                validationLabel.setText(errorMsg.toString());
                validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                validationLabel.setVisible(true);
                return;
            }

            if (soort != null && variant != null) {
                int iResult = updateData(
                        "UPDATE taartsoortenvarianten SET " +
                        "soort_id = " + soort.getId() + ", " +
                        "variant_id = " + variant.getId() +
                        " WHERE soort_id = " + selectedItem.getSoort_id() +
                        " AND variant_id = " + selectedItem.getVariant_id()
                );
                System.out.println(iResult + " rij aangepast");
                if (iResult > 0) {
                    selectedItem.setSoort_name(soort.getSoortnaam());
                    selectedItem.setVariant_name(variant.getVariantnaam());
                    listview.refresh();
                    soortBox.getSelectionModel().clearSelection();
                    variantBox.getSelectionModel().clearSelection();
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
            TaartSoortVariant selectedItem = listview.getSelectionModel().getSelectedItem();

            StringBuilder errorMsg = new StringBuilder();

            if (selectedItem == null) {
                errorMsg.append("Selecteer eerst een item om te verwijderen");
                return;
            }

            if (!errorMsg.isEmpty()) {
                validationLabel.setText(errorMsg.toString());
                validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                validationLabel.setVisible(true);
                return;
            }

            int iResult = updateData(
                    "DELETE FROM taartsoortenvarianten " +
                            "WHERE soort_id = " + selectedItem.getSoort_id() +
                            " AND variant_id = " + selectedItem.getVariant_id()
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

    private void loadVariants() {
        ResultSet r;
        try {
            r = getData("select * from varianten");
            while (r.next()) {
                Variant v = new Variant();
                v.setId(r.getInt("id"));
                v.setVariantnaam(r.getString("variantnaam"));
                variantBox.getItems().add(v);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van varianten");
            se.printStackTrace();
        }
    }
}
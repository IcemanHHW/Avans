package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import nl.kampmeijer.brp1.models.Soort;
import nl.kampmeijer.brp1.models.Variant;
import nl.kampmeijer.brp1.models.TaartSoortVariant;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;

import static nl.kampmeijer.brp1.database.DatabaseHelper.*;

public class TaartSoortVariantScherm {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen");
    private final ComboBox<Soort> soortBox = new ComboBox<>();
    private final ComboBox<Variant> variantBox = new ComboBox<>();
    private final ListView<TaartSoortVariant> listview = new ListView<>();

    public TaartSoortVariantScherm(@NotNull GridPane root) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(listview, 0, 0, 1, 4);
        root.add(soortBox, 1, 0);
        root.add(variantBox, 2, 0);
        root.add(addButton, 1, 1);
        root.add(updateButton, 1, 2);
        root.add(deleteButton, 1, 3);

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

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om aan te passen.");
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
                }
            }
        });

        deleteButton.setOnAction(_ -> {
            TaartSoortVariant selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om te verwijderen");
                return;
            }

            int iResult = updateData(
                    "DELETE FROM taartsoortenvarianten " +
                            "WHERE soort_id = " + selectedItem.getSoort_id() +
                            " AND variant_id = " + selectedItem.getVariant_id()
            );
            System.out.println(iResult + " rij verwijderd");
            if (iResult > 0) {
                listview.getItems().remove(selectedItem);
                listview.refresh();
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
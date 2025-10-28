package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import nl.kampmeijer.brp1.models.*;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.ArrayList;

import static nl.kampmeijer.brp1.database.DatabaseHelper.*;

public class InschrijvingBuurtbewonerScherm {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen");
    private final ComboBox<Soort> soortBox = new ComboBox<>();
    private final ComboBox<Variant> variantBox = new ComboBox<>();
    private final ComboBox<Datum> datumComboBox = new ComboBox<>();
    private final ComboBox<Starttijd> starttijdComboBox = new ComboBox<>();
    private final ComboBox<Locatie> locatieComboBox = new ComboBox<>();
    private final ComboBox<Buurtbewoner> buurtbewonerComboBox = new ComboBox<>();
    private final ListView<InschrijvingBuurtbewoner> listview = new ListView<>();

    public InschrijvingBuurtbewonerScherm(@NotNull GridPane root) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(listview, 0, 0, 1, 4);
        root.add(soortBox, 1, 0);
        root.add(variantBox, 2, 0);
        root.add(datumComboBox, 3, 0);
        root.add(starttijdComboBox, 4, 0);
        root.add(locatieComboBox, 5, 0);
        root.add(buurtbewonerComboBox, 6, 0);
        root.add(addButton, 1, 1);
        root.add(updateButton, 1, 2);
        root.add(deleteButton, 1, 3);

        ResultSet r;
        ArrayList<InschrijvingBuurtbewoner> allIBs = new ArrayList<>();
        try {
            r = getData("""
        SELECT ib.buurtbewoner_id,
               ib.soort_id,
               ib.variant_id,
               ib.datum_id,
               ib.starttijd_id,
               ib.locatie_id,
               b.buurtbewonernaam AS buurtbewoner_name,
               s.soortnaam AS soort_name,
               v.variantnaam AS variant_name,
               d.datum AS datum_date,
               st.starttijd AS starttijd_date,
               l.locatienaam AS locatie_name
        FROM inschrijvingenbuurtbewoners ib
        JOIN buurtbewoners b ON (b.id = ib.buurtbewoner_id)
        JOIN soorten s ON (s.id = ib.soort_id)
        JOIN variant v ON (v.id = ib.variant_id)
        JOIN datums d ON (d.id = ib.datum_id)
        JOIN starttijden st ON (st.id = ib.starttijd_id)
        JOIN locaties l ON (l.id = ib.locatie_id)
    """);
            while (r.next()) {
                InschrijvingBuurtbewoner IB = new InschrijvingBuurtbewoner();
                IB.setBuurtbewoner_id(r.getInt("buurtbewoner_id"));
                IB.setSoort_id(r.getInt("soort_id"));
                IB.setVariant_id(r.getInt("variant_id"));
                IB.setDatum_id(r.getInt("datum_id"));
                IB.setStarttijd_id(r.getInt("starttijd_id"));
                IB.setLocatie_id(r.getInt("locatie_id"));
                IB.setBuurtbewoner_name(r.getString("buurtbewoner_name"));
                IB.setSoort_name(r.getString("soort_name"));
                IB.setVariant_name(r.getString("variant_name"));
                IB.setDatum_date(r.getString("datum_date"));
                IB.setStarttijd_time(r.getString("starttijd_time"));
                IB.setLocatie_name(r.getString("locatie_name"));
                allIBs.add(IB);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van inschrijvingenbuurtbewoners");
            se.printStackTrace();
        }

        for (InschrijvingBuurtbewoner IB : allIBs) {
            listview.getItems().add(IB);
        }

        loadBuurtbewoners();
        loadSoorts();
        loadVariants();
        loadDatums();
        loadStarttijds();
        loadLocaties();

        addButton.setOnAction(_ -> {
            Soort soort = soortBox.getSelectionModel().getSelectedItem();
            Variant variant = variantBox.getSelectionModel().getSelectedItem();
            Datum datum = datumComboBox.getSelectionModel().getSelectedItem();
            Starttijd starttijd = starttijdComboBox.getSelectionModel().getSelectedItem();
            Locatie locatie = locatieComboBox.getSelectionModel().getSelectedItem();
            Buurtbewoner buurtbewoner = buurtbewonerComboBox.getSelectionModel().getSelectedItem();

            if (soort != null && variant != null && datum != null && starttijd != null && locatie != null && buurtbewoner != null) {
                int iResult = insertData(
                        "INSERT INTO inschrijvingenbuurtbewoners(buurtbewoner_id, soort_id, variant_id, datum_id, starttijd_id, locatie_id" + ") " +
                                "VALUES (" + buurtbewoner.getId() + ", " + soort.getId() + ", " + variant.getId() + ", " + datum.getId() + ", " + starttijd.getId() + ", " + locatie.getId() +  ")");
                System.out.println(iResult + " rij toegevoegd");

                if (iResult > 0) {
                    InschrijvingBuurtbewoner newIB = new InschrijvingBuurtbewoner();
                    newIB.setSoort_name(soort.getSoortnaam());
                    newIB.setVariant_name(variant.getVariantnaam());
                    newIB.setDatum_date(datum.getDatum());
                    newIB.setStarttijd_time(starttijd.getStarttijd());
                    newIB.setLocatie_name(locatie.getLocatienaam());
                    newIB.setBuurtbewoner_name(buurtbewoner.getBuurtbewonernaam());
                    listview.getItems().add(newIB);
                    soortBox.getSelectionModel().clearSelection();
                    variantBox.getSelectionModel().clearSelection();
                    datumComboBox.getSelectionModel().clearSelection();
                    starttijdComboBox.getSelectionModel().clearSelection();
                    locatieComboBox.getSelectionModel().clearSelection();
                    buurtbewonerComboBox.getSelectionModel().clearSelection();
                }
            }
        });


        updateButton.setOnAction(_ -> {
            Soort soort = soortBox.getSelectionModel().getSelectedItem();
            Variant variant = variantBox.getSelectionModel().getSelectedItem();
            Datum datum = datumComboBox.getSelectionModel().getSelectedItem();
            Starttijd starttijd = starttijdComboBox.getSelectionModel().getSelectedItem();
            Locatie locatie = locatieComboBox.getSelectionModel().getSelectedItem();
            Buurtbewoner buurtbewoner = buurtbewonerComboBox.getSelectionModel().getSelectedItem();
            InschrijvingBuurtbewoner selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om aan te passen.");
                return;
            }

            if (soort != null && variant != null && datum != null && starttijd != null && locatie != null && buurtbewoner != null) {
                int iResult = updateData(
                        "UPDATE inschrijvingenbuurtbewoners SET " +
                                "buurtbewoner_id = " +  buurtbewoner.getId() + ", " +
                                "soort_id = " + soort.getId() + ", " +
                                "variant_id = " + variant.getId() + ", " +
                                "datum_id = " + datum.getId() + ", " +
                                "starttijd_id = " + starttijd.getId() + ", " +
                                "locatie_id = " + locatie.getId() +
                                " WHERE buurtbewoner_id = " + selectedItem.getBuurtbewoner_id() +
                                " AND soort_id = " + selectedItem.getSoort_id() +
                                " AND variant_id = " + selectedItem.getVariant_id() +
                                " AND datum_id = " + selectedItem.getDatum_id() +
                                " AND starttijd_id = " + selectedItem.getStarttijd_id() +
                                " AND locatie_id = " + selectedItem.getLocatie_id()
                );
                System.out.println(iResult + " rij aangepast");
                if (iResult > 0) {
                    selectedItem.setSoort_name(soort.getSoortnaam());
                    selectedItem.setVariant_name(variant.getVariantnaam());
                    selectedItem.setDatum_date(datum.getDatum());
                    selectedItem.setStarttijd_time(starttijd.getStarttijd());
                    selectedItem.setLocatie_name(locatie.getLocatienaam());
                    selectedItem.setBuurtbewoner_name(buurtbewoner.getBuurtbewonernaam());
                    listview.refresh();
                    soortBox.getSelectionModel().clearSelection();
                    variantBox.getSelectionModel().clearSelection();
                    datumComboBox.getSelectionModel().clearSelection();
                    starttijdComboBox.getSelectionModel().clearSelection();
                    locatieComboBox.getSelectionModel().clearSelection();
                    buurtbewonerComboBox.getSelectionModel().clearSelection();
                }
            }
        });

        deleteButton.setOnAction(_ -> {
            InschrijvingBuurtbewoner selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om aan te passen.");
                return;
            }

            int iResult = updateData(
                    "DELETE FROM inschrijvingenbuurtbewoners " +
                            "WHERE buurtbewoner_id = " + selectedItem.getBuurtbewoner_id() +
                            " AND soort_id = " + selectedItem.getSoort_id() +
                            " AND variant_id = " + selectedItem.getVariant_id() +
                            " AND datum_id = " + selectedItem.getDatum_id() +
                            " AND starttijd_id = " + selectedItem.getStarttijd_id() +
                            " AND locatie_id = " + selectedItem.getLocatie_id()
            );
            System.out.println(iResult + " rij verwijderd");
            if (iResult > 0) {
                listview.getItems().remove(selectedItem);
                listview.refresh();
            }
        });
    }

    private void loadBuurtbewoners() {
        ResultSet r;
        try {
            r = getData("select * from buurtbewoners");
            while (r.next()) {
                Buurtbewoner b = new Buurtbewoner();
                b.setId(r.getInt("id"));
                b.setBuurtbewonernaam(r.getString("buurtbewonernaam"));
                buurtbewonerComboBox.getItems().add(b);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van buurtbewoners");
            se.printStackTrace();
        }
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

    private void loadDatums() {
        ResultSet r;
        try {
            r = getData("select * from datums");
            while (r.next()) {
                Datum d = new Datum();
                d.setId(r.getInt("id"));
                d.setDatum(r.getString("datum"));
                datumComboBox.getItems().add(d);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van datums");
            se.printStackTrace();
        }
    }

    private void loadStarttijds() {
        ResultSet r;
        try {
            r = getData("select * from starttijden");
            while (r.next()) {
                Starttijd s = new Starttijd();
                s.setId(r.getInt("id"));
                s.setStarttijd(r.getString("starttijd"));
                starttijdComboBox.getItems().add(s);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van starttijden");
            se.printStackTrace();
        }
    }

    private void loadLocaties() {
        ResultSet r;
        try {
            r = getData("select * from locaties");
            while (r.next()) {
                Locatie l = new Locatie();
                l.setId(r.getInt("id"));
                l.setLocatienaam(r.getString("locatienaam"));
                locatieComboBox.getItems().add(l);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van locaties");
            se.printStackTrace();
        }
    }
}
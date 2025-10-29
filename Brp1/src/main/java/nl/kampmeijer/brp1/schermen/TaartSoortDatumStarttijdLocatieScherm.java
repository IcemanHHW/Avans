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

public class TaartSoortDatumStarttijdLocatieScherm {
    private final Button addButton = new Button("Toevoegen"), updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen");
    private final ComboBox<Soort> soortBox = new ComboBox<>();
    private final ComboBox<Datum> datumComboBox = new ComboBox<>();
    private final ComboBox<Starttijd> starttijdComboBox = new ComboBox<>();
    private final ComboBox<Locatie> locatieComboBox = new ComboBox<>();
    private final ListView<TaartSoortDatumStarttijdLocatie> listview = new ListView<>();

    public TaartSoortDatumStarttijdLocatieScherm(@NotNull GridPane root) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(listview, 0, 0, 1, 4);
        root.add(soortBox, 1, 0);
        root.add(datumComboBox, 2, 0);
        root.add(starttijdComboBox, 3, 0);
        root.add(locatieComboBox, 4, 0);
        root.add(addButton, 1, 1);
        root.add(updateButton, 1, 2);
        root.add(deleteButton, 1, 3);

        ResultSet r;
        ArrayList<TaartSoortDatumStarttijdLocatie> allTSDSLs = new ArrayList<>();
        try {
            r = getData("""
        SELECT tsdsl.soort_id,
               tsdsl.datum_id,
               tsdsl.starttijd_id,
               tsdsl.locatie_id,
               s.soortnaam AS soort_name,
               d.datum AS datum_date,
               st.starttijd AS starttijd_date,
               l.locatienaam AS locatie_name
        FROM taartsoortendatumsstarttijdenlocaties tsdsl
        JOIN soorten s ON (s.id = tsdsl.soort_id)
        JOIN datums d ON (d.id = tsdsl.datum_id)
        JOIN starttijden st ON (st.id = tsdsl.starttijd_id)
        JOIN locaties l ON (l.id = tsdsl.locatie_id)
    """);
            while (r.next()) {
                TaartSoortDatumStarttijdLocatie TSDSL = new TaartSoortDatumStarttijdLocatie();
                TSDSL.setSoort_id(r.getInt("soort_id"));
                TSDSL.setDatum_id(r.getInt("datum_id"));
                TSDSL.setStarttijd_id(r.getInt("starttijd_id"));
                TSDSL.setLocatie_id(r.getInt("locatie_id"));
                TSDSL.setSoort_name(r.getString("soort_name"));
                TSDSL.setDatum_date(r.getString("datum_date"));
                TSDSL.setStarttijd_time(r.getString("starttijd_time"));
                TSDSL.setLocatie_name(r.getString("locatie_name"));
                allTSDSLs.add(TSDSL);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van taartsoortendatumsstarttijdenlocaties");
            se.printStackTrace();
        }

        for (TaartSoortDatumStarttijdLocatie TSDSL : allTSDSLs) {
            listview.getItems().add(TSDSL);
        }

        loadSoorts();
        loadDatums();
        loadStarttijds();
        loadLocaties();

        addButton.setOnAction(_ -> {
            Soort soort = soortBox.getSelectionModel().getSelectedItem();
            Datum datum = datumComboBox.getSelectionModel().getSelectedItem();
            Starttijd starttijd = starttijdComboBox.getSelectionModel().getSelectedItem();
            Locatie locatie = locatieComboBox.getSelectionModel().getSelectedItem();

            if (soort != null && datum != null && starttijd != null && locatie != null) {
                int iResult = insertData("INSERT INTO taartsoortendatumsstarttijdenlocaties(soort_id, datum_id, starttijd_id, locatie_id) " +
                        "VALUES (" + soort.getId() + ", " + datum.getId() + ", " + starttijd.getId() + ", " + locatie.getId() + ")");
                System.out.println(iResult + " rij toegevoegd");

                if (iResult > 0) {
                    TaartSoortDatumStarttijdLocatie newTSDSL = new TaartSoortDatumStarttijdLocatie();
                    newTSDSL.setSoort_name(soort.getSoortnaam());
                    newTSDSL.setDatum_date(datum.getDatum());
                    newTSDSL.setStarttijd_time(starttijd.getStarttijd());
                    newTSDSL.setLocatie_name(locatie.getLocatienaam());
                    listview.getItems().add(newTSDSL);
                    soortBox.getSelectionModel().clearSelection();
                    datumComboBox.getSelectionModel().clearSelection();
                }
            }
        });

        updateButton.setOnAction(_ -> {
            Soort soort = soortBox.getSelectionModel().getSelectedItem();
            Datum datum = datumComboBox.getSelectionModel().getSelectedItem();
            Starttijd starttijd = starttijdComboBox.getSelectionModel().getSelectedItem();
            Locatie locatie = locatieComboBox.getSelectionModel().getSelectedItem();
            TaartSoortDatumStarttijdLocatie selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om aan te passen.");
                return;
            }

            if (soort != null && datum != null && starttijd != null && locatie != null) {
                int iResult = updateData(
                        "UPDATE taartsoortendatumsstarttijdenlocaties SET " +
                            "soort_id = " + soort.getId() + ", " +
                            "datum_id = " + datum.getId() + ", " +
                            "starttijd_id = " + starttijd.getId() + ", " +
                            "locatie_id = " + locatie.getId() +
                            " WHERE soort_id = " + selectedItem.getSoort_id() +
                            " AND datum_id = " + selectedItem.getDatum_id() +
                            " AND starttijd_id = " + selectedItem.getStarttijd_id() +
                            " AND locatie_id = " + selectedItem.getLocatie_id()
                );
                System.out.println(iResult + " rij aangepast");
                if (iResult > 0) {
                    selectedItem.setSoort_name(soort.getSoortnaam());
                    selectedItem.setDatum_date(datum.getDatum());
                    selectedItem.setStarttijd_time(starttijd.getStarttijd());
                    selectedItem.setLocatie_name(locatie.getLocatienaam());
                    listview.refresh();
                    soortBox.getSelectionModel().clearSelection();
                    datumComboBox.getSelectionModel().clearSelection();
                }
            }
        });

        deleteButton.setOnAction(_ -> {
            TaartSoortDatumStarttijdLocatie selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om te verwijderen");
                return;
            }

            int iResult = updateData(
                    "DELETE FROM taartsoortendatumsstarttijdenlocaties " +
                            "WHERE soort_id = " + selectedItem.getSoort_id() +
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
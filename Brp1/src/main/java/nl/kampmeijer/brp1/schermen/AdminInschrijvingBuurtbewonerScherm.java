package nl.kampmeijer.brp1.schermen;

import javafx.application.Application;
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

public class AdminInschrijvingBuurtbewonerScherm  {
    private final Button updateButton = new Button("Aanpassen"), deleteButton = new Button("Verwijderen"), backButton = new Button("Terug");
    private final Label soortLabel = new Label("Soort:"), variantLabel = new Label("Variant:"), optieLabel = new Label("Beschikbare momenten:"), buurtbewonerLabel = new Label("Buurtbewoner:");
    private final ComboBox<Soort> soortBox = new ComboBox<>();
    private final ComboBox<Variant> variantBox = new ComboBox<>();
    private final ComboBox<SoortOptie> optieComboBox = new ComboBox<>();
    private final ComboBox<Buurtbewoner> buurtbewonerComboBox = new ComboBox<>();
    private final ListView<InschrijvingBuurtbewoner> listview = new ListView<>();

    public AdminInschrijvingBuurtbewonerScherm(@NotNull GridPane root, Runnable onBack) {
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        root.add(backButton, 0, 0);
        root.add(listview, 0, 1, 1, 5);
        root.add(buurtbewonerLabel, 1, 1);
        root.add(soortLabel, 2, 1);
        root.add(variantLabel, 3, 1);
        root.add(optieLabel, 4, 1);
        root.add(buurtbewonerComboBox, 1, 2);
        root.add(soortBox, 2, 2);
        root.add(variantBox, 3, 2);
        root.add(optieComboBox, 4, 2);
        root.add(updateButton, 1, 3);
        root.add(deleteButton, 1, 4);

        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(_ -> onBack.run());

        soortLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        variantLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        optieLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        buurtbewonerLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

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

        // When a Soort is selected, load its valid options
        soortBox.setOnAction(_ -> {
            Soort selectedSoort = soortBox.getSelectionModel().getSelectedItem();
            if (selectedSoort != null) {
                loadOptiesForSoort(selectedSoort.getId());
            } else {
                optieComboBox.getItems().clear();
            }
        });

        updateButton.setOnAction(_ -> {
            Soort soort = soortBox.getSelectionModel().getSelectedItem();
            Variant variant = variantBox.getSelectionModel().getSelectedItem();
            SoortOptie optie = optieComboBox.getSelectionModel().getSelectedItem();
            Buurtbewoner buurtbewoner = buurtbewonerComboBox.getSelectionModel().getSelectedItem();
            InschrijvingBuurtbewoner selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om aan te passen.");
                return;
            }

            if (soort != null && variant != null && optie != null && buurtbewoner != null) {
                int iResult = updateData(
                        "UPDATE inschrijvingenbuurtbewoners SET " +
                                "buurtbewoner_id = " + buurtbewoner.getId() + ", " +
                                "soort_id = " + soort.getId() + ", " +
                                "variant_id = " + variant.getId() + ", " +
                                "datum_id = " + optie.getDatum().getId() + ", " +
                                "starttijd_id = " + optie.getStarttijd().getId() + ", " +
                                "locatie_id = " + optie.getLocatie().getId() +
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
                    selectedItem.setDatum_date(optie.getDatum().getDatum());
                    selectedItem.setStarttijd_time(optie.getStarttijd().getStarttijd());
                    selectedItem.setLocatie_name(optie.getLocatie().getLocatienaam());
                    selectedItem.setBuurtbewoner_name(buurtbewoner.getBuurtbewonernaam());
                    listview.refresh();
                    soortBox.getSelectionModel().clearSelection();
                    variantBox.getSelectionModel().clearSelection();
                    optieComboBox.getSelectionModel().clearSelection();
                    buurtbewonerComboBox.getSelectionModel().clearSelection();
                }
            }
        });

        deleteButton.setOnAction(_ -> {
            InschrijvingBuurtbewoner selectedItem = listview.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                System.out.println("Selecteer eerst een item om te verwijderen.");
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

    /**
     * Loads all available combinations of Datum, Starttijd, and Locatie for the given Soort
     * that have not yet reached their maximum number of participants.
     * Fills the {@code optieComboBox} with these available options.
     * <p>
     * This method queries the <strong>taartsoortdatumstarttijdlocatie</strong> table and joins it with the
     * <strong>inschrijvingenbuurtbewoners</strong> and <strong>taartsoortmaxaantalpersonen</strong> tables to count current registrations
     * and ensure only available slots are displayed.
     *
     * @param soortId The ID of the Soort for which to load options.
     */
    private void loadOptiesForSoort(int soortId) {
        optieComboBox.getItems().clear();
        try {
            ResultSet r = getData("""
                SELECT tsdl.soort_id,
                       tsdl.datum_id,
                       tsdl.starttijd_id,
                       tsdl.locatie_id,
                       s.soortnaam AS soort_name,
                       d.datum AS datum_date,
                       st.starttijd AS starttijd_time,
                       l.locatienaam AS locatie_name,
                       COUNT(ib.buurtbewoner_id) AS current_count,
                       tsmap.maxpers_number
                FROM taartsoortdatumstarttijdlocatie tsdl
                JOIN soorten s ON s.id = tsdl.soort_id
                JOIN datums d ON d.id = tsdl.datum_id
                JOIN starttijden st ON st.id = tsdl.starttijd_id
                JOIN locaties l ON l.id = tsdl.locatie_id
                LEFT JOIN inschrijvingenbuurtbewoners ib
                    ON ib.soort_id = tsdl.soort_id
                    AND ib.datum_id = tsdl.datum_id
                    AND ib.starttijd_id = tsdl.starttijd_id
                    AND ib.locatie_id = tsdl.locatie_id
                JOIN taartsoortmaxaantalpersonen tsmap
                ON tsmap.soort_id = tsdl.soort_id
                WHERE tsdl.soort_id =\s""" + soortId + """
                GROUP BY tsdl.soort_id,
                         tsdl.datum_id,
                         tsdl.starttijd_id,
                         tsdl.locatie_id,
                         s.soortnaam,
                         d.datum,
                         st.starttijd,
                         l.locatienaam,
                         tsmap.maxpers_number
                HAVING COUNT(ib.buurtbewoner_id) < tsmap.maxpers_number
            """);

            while (r.next()) {
                Datum d = new Datum();
                d.setId(r.getInt("datum_id"));
                d.setDatum(r.getString("datum_date"));

                Starttijd st = new Starttijd();
                st.setId(r.getInt("starttijd_id"));
                st.setStarttijd(r.getString("starttijd_time"));

                Locatie l = new Locatie();
                l.setId(r.getInt("locatie_id"));
                l.setLocatienaam(r.getString("locatie_name"));

                SoortOptie optie = new SoortOptie(d, st, l, r.getInt("current_count"), r.getInt("maxpers_number"));
                optieComboBox.getItems().add(optie);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van opties voor soort");
            se.printStackTrace();
        }
    }
}
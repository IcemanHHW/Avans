package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.brp1.models.*;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import java.sql.ResultSet;

import static nl.kampmeijer.brp1.database.DatabaseHelper.*;

public class InschrijvingBuurtbewonerScherm {
    private final Button addButton = new Button("Inschrijven"), backButton = new Button("Terug");
    private final Label soortLabel = new Label("Soort:"), variantLabel = new Label("Variant:"), optieLabel = new Label("Beschikbare momenten:"), buurtbewonerLabel = new Label("Buurtbewoner:");
    private final ComboBox<Soort> soortBox = new ComboBox<>();
    private final ComboBox<Variant> variantBox = new ComboBox<>();
    private final ComboBox<SoortOptie> optieComboBox = new ComboBox<>();
    private final ComboBox<Buurtbewoner> buurtbewonerComboBox = new ComboBox<>();
    private final VBox formBox = new VBox();

    public InschrijvingBuurtbewonerScherm(@NotNull GridPane root, Runnable onBack) {
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);

        soortLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        variantLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        optieLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        buurtbewonerLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        // Form layout
        formBox.setAlignment(Pos.CENTER);
        formBox.getChildren().addAll(
                soortLabel, soortBox,
                variantLabel, variantBox,
                optieLabel, optieComboBox,
                buurtbewonerLabel, buurtbewonerComboBox,
                addButton
        );

        // Button style
        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(_ -> onBack.run());

        addButton.setPrefSize(120, 30);
        addButton.setStyle("-fx-font-size: 16px;");

        root.add(formBox, 1, 1);
        root.add(backButton, 0, 0);


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

        addButton.setOnAction(_ -> {
            Soort soort = soortBox.getSelectionModel().getSelectedItem();
            Variant variant = variantBox.getSelectionModel().getSelectedItem();
            SoortOptie optie = optieComboBox.getSelectionModel().getSelectedItem();
            Buurtbewoner buurtbewoner = buurtbewonerComboBox.getSelectionModel().getSelectedItem();

            if (soort != null && variant != null && optie != null && buurtbewoner != null) {
                int iResult = insertData("INSERT INTO inschrijvingenbuurtbewoners (buurtbewoner_id, soort_id, variant_id, datum_id, starttijd_id, locatie_id) " +
                                "VALUES (" + buurtbewoner.getId() + ", " + soort.getId() + ", " + variant.getId() + ", " + optie.getDatum().getId() + ", " + optie.getStarttijd().getId() + ", " + optie.getLocatie().getId() + ")");
                System.out.println(iResult + " rij toegevoegd");

                if (iResult > 0) {
                    soortBox.getSelectionModel().clearSelection();
                    variantBox.getSelectionModel().clearSelection();
                    optieComboBox.getSelectionModel().clearSelection();
                    buurtbewonerComboBox.getSelectionModel().clearSelection();
                }
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
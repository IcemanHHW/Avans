package nl.kampmeijer.brp1.schermen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.jetbrains.annotations.NotNull;

public class AdminScherm {
    private final Button buurtbewonerButton = new Button("Buurtbewoners");
    private final Button soortButton = new Button("Soorten");
    private final Button variantButton = new Button("Varianten");
    private final Button datumButton = new Button("Datums");
    private final Button starttijdButton = new Button("Starttijden");
    private final Button locatieButton = new Button("Locaties");
    private final Button maxPersonenButton = new Button("Maximaal aantal personen");
    private final Button soortVariantButton = new Button("TaartSoortVariant");
    private final Button soortMaxPersonenButton = new Button("TaartSoortMaximaalAantalPersonen");
    private final Button soortDatumStarttijdLocatieButton = new Button("TaartSoortDatumStarttijdLocatie");
    private final Button inschrijvingenButton = new Button("Inschrijvingen Buurtbewoners");
    private final Button backButton = new Button("Terug");

    public AdminScherm(@NotNull GridPane root,
                       Runnable onBuurtbewoner,
                       Runnable onSoort,
                       Runnable onVariant,
                       Runnable onDatum,
                       Runnable onStarttijd,
                       Runnable onLocatie,
                       Runnable onMaxPersonen,
                       Runnable onSoortVariant,
                       Runnable onSoortMaxPersonen,
                       Runnable onSoortDatumStarttijdLocatie,
                       Runnable onInschrijvingen,
                       Runnable onBack) {

        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);

        Label titleLabel = new Label("Admin Menu");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 20));

        Label basisDataLabel = new Label("Basis Data:");
        basisDataLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        Label koppelLabel = new Label("Koppelingen:");
        koppelLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        // Set button sizes
        buurtbewonerButton.setPrefWidth(250);
        soortButton.setPrefWidth(250);
        variantButton.setPrefWidth(250);
        datumButton.setPrefWidth(250);
        starttijdButton.setPrefWidth(250);
        locatieButton.setPrefWidth(250);
        maxPersonenButton.setPrefWidth(250);
        soortVariantButton.setPrefWidth(250);
        soortMaxPersonenButton.setPrefWidth(250);
        soortDatumStarttijdLocatieButton.setPrefWidth(250);
        inschrijvingenButton.setPrefWidth(250);
        backButton.setPrefWidth(250);

        // Add event handlers
        buurtbewonerButton.setOnAction(_ -> onBuurtbewoner.run());
        soortButton.setOnAction(_ -> onSoort.run());
        variantButton.setOnAction(_ -> onVariant.run());
        datumButton.setOnAction(_ -> onDatum.run());
        starttijdButton.setOnAction(_ -> onStarttijd.run());
        locatieButton.setOnAction(_ -> onLocatie.run());
        maxPersonenButton.setOnAction(_ -> onMaxPersonen.run());
        soortVariantButton.setOnAction(_ -> onSoortVariant.run());
        soortMaxPersonenButton.setOnAction(_ -> onSoortMaxPersonen.run());
        soortDatumStarttijdLocatieButton.setOnAction(_ -> onSoortDatumStarttijdLocatie.run());
        inschrijvingenButton.setOnAction(_ -> onInschrijvingen.run());
        backButton.setOnAction(_ -> onBack.run());

        // Layout
        int row = 0;

        root.add(backButton, 0, row++);
        row++; // Empty row

        root.add(titleLabel, 0, row++, 2, 1);

        row++; // Empty row
        root.add(basisDataLabel, 0, row++, 2, 1);
        root.add(buurtbewonerButton, 0, row++);
        root.add(soortButton, 0, row++);
        root.add(variantButton, 0, row++);
        root.add(datumButton, 0, row++);
        root.add(starttijdButton, 0, row++);
        root.add(locatieButton, 0, row++);
        root.add(maxPersonenButton, 0, row++);

        row++; // Empty row
        root.add(koppelLabel, 0, row++, 2, 1);
        root.add(soortVariantButton, 0, row++);
        root.add(soortMaxPersonenButton, 0, row++);
        root.add(soortDatumStarttijdLocatieButton, 0, row++);
        root.add(inschrijvingenButton, 0, row++);
    }
}
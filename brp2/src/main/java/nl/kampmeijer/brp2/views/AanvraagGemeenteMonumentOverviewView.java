package nl.kampmeijer.brp2.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.kampmeijer.brp2.models.AanVraagGemeenteMonument;
import nl.kampmeijer.brp2.models.GemeenteMonument;
import nl.kampmeijer.brp2.services.AanvraagGemeenteMonumentService;
import org.jetbrains.annotations.NotNull;
import java.util.Map;

/**
 * Overview view for {@link AanVraagGemeenteMonument} entities.
 * <p>
 * This view displays a list of aanvragen, detailed information for the selected
 * aanvraag, and visual charts showing statistics per category and location type
 * (binnenshuis vs buitenshuis).
 */
public class AanvraagGemeenteMonumentOverviewView {
    private final TextField categorieNaamField = new TextField(), locatieOnderdeelField = new TextField(), gemeenteMonumentField = new TextField();
    private final Label validationLabel = new Label();
    private final ListView<AanVraagGemeenteMonument> listview = new ListView<>();
    private final AanvraagGemeenteMonumentService aanvraagGemeenteMonumentService = new AanvraagGemeenteMonumentService();

    public AanvraagGemeenteMonumentOverviewView(@NotNull GridPane root, Runnable onBack) {
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);

        Button backButton = new Button("Terug");
        Label gemeenteMonumentLabel = new Label("Adres");
        Label categorieLabel = new Label("Categorie");
        Label locatieOnderdeellabel = new Label("Locatie / Onderdeel / Info");

        gemeenteMonumentLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        categorieLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        locatieOnderdeellabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        validationLabel.setStyle("-fx-text-fill: red;");
        backButton.setPrefSize(100, 20);
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(_ -> onBack.run());

        gemeenteMonumentField.setEditable(false);
        categorieNaamField.setEditable(false);
        locatieOnderdeelField.setEditable(false);

        VBox detailsBox = new VBox(8,
                gemeenteMonumentLabel, gemeenteMonumentField,
                categorieLabel, categorieNaamField,
                locatieOnderdeellabel, locatieOnderdeelField
        );

        VBox leftBox = new VBox(10,
                listview, validationLabel, detailsBox
        );
        leftBox.setPrefWidth(400);

        HBox chartsBox = new HBox(20,
                createCategorieChart(),
                createBinnenBuitenChart()
        );

        root.add(backButton, 0, 0);
        root.add(leftBox, 1, 1);
        root.add(chartsBox, 2, 1);

        listview.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(AanVraagGemeenteMonument item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });

        listview.getSelectionModel().selectedItemProperty().addListener((_, _, selected) -> showDetails(selected));

        loadAanvragenGemeenteMonumenten();
    }

    private void loadAanvragenGemeenteMonumenten() {
        try {
            listview.getItems().addAll(aanvraagGemeenteMonumentService.getAll());
        } catch (RuntimeException e) {
            switch (e.getMessage()) {
                case "DATABASE_LOAD_ERROR" ->
                        validationLabel.setText("Databasefout bij laden van AanvragenGemeenteMonumenten");
                case "DATABASE_NULL_ERROR" ->
                        validationLabel.setText("Interne fout bij laden van AanvragenGemeenteMonumenten");
                default ->
                        validationLabel.setText("Onverwachte fout opgetreden");
            }
        }
    }

    /**
     * Displays the details of the selected aanvraag.
     *
     * @param aanvraag the selected {@link AanVraagGemeenteMonument},
     *                 or {@code null} if no selection is made
     */
    private void showDetails(AanVraagGemeenteMonument aanvraag) {
        if (aanvraag == null) {
            clearFields();
            return;
        }

        categorieNaamField.setText(aanvraag.getCategorie().getCategorieNaam());
        locatieOnderdeelField.setText(aanvraag.getLocatieOnderdeel().locatieOnderdeelInfo());

        GemeenteMonument monument = aanvraag.getGemeenteMonument();
        if (monument != null) {
            gemeenteMonumentField.setText(
                    monument.getStraat() + " "
                            + monument.getHuisNr()
                            + (monument.getHuisLetter() != null ? monument.getHuisLetter() : "")
                            + ", " + monument.getPostcode()
                            + " " + monument.getPlaats()
            );
        } else {
            gemeenteMonumentField.setText("Onbekend monument");
        }
    }

    /**
     * Creates a bar chart showing the number of aanvragen per category.
     *
     * @return a {@link BarChart} containing category statistics
     */
    private @NotNull BarChart<String, Number> createCategorieChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Aanvragen per categorie");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        Map<String, Integer> data = aanvraagGemeenteMonumentService.countByCategorie();

        data.forEach((categorie, count) ->
                series.getData().add(new XYChart.Data<>(categorie, count))
        );

        chart.getData().add(series);
        chart.setLegendVisible(false);
        return chart;
    }

    /**
     * Creates a pie chart showing the distribution between binnenshuis
     * and buitenshuis aanvragen.
     *
     * @return a {@link PieChart} containing location type statistics
     */
    private @NotNull PieChart createBinnenBuitenChart() {
        PieChart chart = new PieChart();
        chart.setTitle("Binnenshuis of buitenshuis");
        Map<String, Integer> data = aanvraagGemeenteMonumentService.countBinnenVsBuiten();
        data.forEach((label, count) ->
                chart.getData().add(new PieChart.Data(label, count))
        );
        return chart;
    }

    private void clearFields() {
        gemeenteMonumentField.clear();
        categorieNaamField.clear();
        locatieOnderdeelField.clear();
    }
}

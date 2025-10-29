package nl.kampmeijer.brp1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.kampmeijer.brp1.schermen.*;
import org.jetbrains.annotations.NotNull;

public class Main extends Application {
    private Stage primaryStage;
    private Scene scene;

    @Override
    public void start(@NotNull Stage stage) {
        this.primaryStage = stage;

        GridPane root = new GridPane();
        scene = new Scene(root, 900, 600);

        stage.setScene(scene);
        stage.setTitle("Taarten bakken in Lottum");

        showStartScherm();
        stage.show();
    }

    private void showStartScherm() {
        GridPane root = new GridPane();
        new StartScherm(root, this::showInschrijvenScherm, this::showAdminScherm);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Start");
    }

    private void showInschrijvenScherm() {
        GridPane root = new GridPane();
        new InschrijvingBuurtbewonerScherm(root, this::showStartScherm);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Inschrijven");
    }

    private void showAdminScherm() {
        GridPane root = new GridPane();
        new AdminScherm(root,
                this::showBuurtbewonerScherm,
                this::showSoortScherm,
                this::showVariantScherm,
                this::showDatumScherm,
                this::showStarttijdScherm,
                this::showLocatieScherm,
                this::showMaxPersonenScherm,
                this::showSoortVariantScherm,
                this::showSoortMaxPersonenScherm,
                this::showSoortDatumStarttijdLocatieScherm,
                this::showInschrijvingenScherm,
                this::showStartScherm
        );
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Admin");
    }

    private void showBuurtbewonerScherm() {
        GridPane root = new GridPane();
        new BuurtbewonerScherm(root);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Buurtbewoners");
    }

    private void showSoortScherm() {
        GridPane root = new GridPane();
        new SoortScherm(root);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Soorten");
    }

    private void showVariantScherm() {
        GridPane root = new GridPane();
        new VariantScherm(root);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Varianten");
    }

    private void showDatumScherm() {
        GridPane root = new GridPane();
        new DatumScherm(root);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Datums");
    }

    private void showStarttijdScherm() {
        GridPane root = new GridPane();
        new StarttijdScherm(root);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Starttijden");
    }

    private void showLocatieScherm() {
        GridPane root = new GridPane();
        new LocatieScherm(root);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Locaties");
    }

    private void showMaxPersonenScherm() {
        GridPane root = new GridPane();
        new MaximaalAantalPersonenScherm(root);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Max Personen");
    }

    private void showSoortVariantScherm() {
        GridPane root = new GridPane();
        new TaartSoortVariantScherm(root);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Soort-Variant");
    }

    private void showSoortMaxPersonenScherm() {
        GridPane root = new GridPane();
        new TaartSoortMaximaalAantalPersonenScherm(root);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Soort-Max Personen");
    }

    private void showSoortDatumStarttijdLocatieScherm() {
        GridPane root = new GridPane();
        new TaartSoortDatumStarttijdLocatieScherm(root);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Soort-Datum-Starttijd-Locatie");
    }

    private void showInschrijvingenScherm() {
        GridPane root = new GridPane();
        new AdminInschrijvingBuurtbewonerScherm(root);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Alle Inschrijvingen");
    }

    public static void main(String[] args) {
        launch();
    }
}
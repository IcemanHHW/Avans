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
        scene = new Scene(root, 900, 900);
        stage.setScene(scene);
        stage.setTitle("Taarten bakken in Lottum");
        showStartScherm();
        stage.setMaximized(true);
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
                this::showAdminInschrijvingBuurtbewonerScherm,
                this::showStartScherm
        );
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Admin");
    }

    private void showAdminInschrijvingBuurtbewonerScherm() {
        GridPane root = new GridPane();
        new AdminInschrijvingBuurtbewonerScherm(root, this::showAdminScherm);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Inschrijvingen");
    }

    private void showBuurtbewonerScherm() {
        GridPane root = new GridPane();
        new BuurtbewonerScherm(root, this::showAdminScherm);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Buurtbewoners");
    }

    private void showSoortScherm() {
        GridPane root = new GridPane();
        new SoortScherm(root, this::showAdminScherm);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Soorten");
    }

    private void showVariantScherm() {
        GridPane root = new GridPane();
        new VariantScherm(root, this::showAdminScherm);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Varianten");
    }

    private void showDatumScherm() {
        GridPane root = new GridPane();
        new DatumScherm(root, this::showAdminScherm);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Datums");
    }

    private void showStarttijdScherm() {
        GridPane root = new GridPane();
        new StarttijdScherm(root, this::showAdminScherm);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Starttijden");
    }

    private void showLocatieScherm() {
        GridPane root = new GridPane();
        new LocatieScherm(root, this::showAdminScherm);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Locaties");
    }

    private void showMaxPersonenScherm() {
        GridPane root = new GridPane();
        new MaximaalAantalPersonenScherm(root, this::showAdminScherm);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - Maximaal Aantal Personen");
    }

    private void showSoortVariantScherm() {
        GridPane root = new GridPane();
        new TaartSoortVariantScherm(root, this::showAdminScherm);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - TaartSoortVariant");
    }

    private void showSoortMaxPersonenScherm() {
        GridPane root = new GridPane();
        new TaartSoortMaximaalAantalPersonenScherm(root, this::showAdminScherm);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - TaartSoortMaximaalAantalPersonen");
    }

    private void showSoortDatumStarttijdLocatieScherm() {
        GridPane root = new GridPane();
        new TaartSoortDatumStarttijdLocatieScherm(root, this::showAdminScherm);
        scene.setRoot(root);
        primaryStage.setTitle("Taarten bakken in Lottum - TaartSoortDatumStarttijdLocatie");
    }

    public static void main(String[] args) {
        launch();
    }
}
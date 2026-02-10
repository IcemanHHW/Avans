package nl.kampmeijer.brp2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.kampmeijer.brp2.data.CSVReader;
import nl.kampmeijer.brp2.views.*;
import org.jetbrains.annotations.NotNull;

public class App extends Application {
    private Stage primaryStage;
    private Scene scene;

    @Override
    public void start(@NotNull Stage stage) {
        this.primaryStage = stage;
        GridPane root = new GridPane();
        scene = new Scene(root, 900, 900);
        stage.setScene(scene);
        stage.setTitle("Gemeente Monumenten Maastricht");
        showStartView();
        stage.setMaximized(true);
        stage.show();
    }

    private void showStartView() {
        System.out.println(
                CSVReader.class
                        .getClassLoader()
                        .getResource("GemeenteMonumenten.csv")
        );

        GridPane root = new GridPane();
        new StartView(root, this::showAanvraagGemeenteMonumentenView, this::showAdminView);
        scene.setRoot(root);
        primaryStage.setTitle("Gemeente Monumenten Maastricht - Start");
    }

    private void showAanvraagGemeenteMonumentenView() {
        GridPane root = new GridPane();
        new AanvraagGemeenteMonumentView(root, this::showStartView);
        scene.setRoot(root);
        primaryStage.setTitle("Gemeente Monumenten Maastricht - Nieuwe Aanvraag");
    }

    private void showAdminView() {
        GridPane root = new GridPane();
        new AdminView(root,
                this::showCategorieView,
                this::showOnderdeelView,
                this::showLocatieView,
                this::showBinnenLocatieOnderdeelView,
                this::showBuitenLocatieOnderdeelView,
                this::showAanvraagGemeenteMonumentOverviewView,
                this::showStartView
        );
        scene.setRoot(root);
        primaryStage.setTitle("Gemeente Monumenten Maastricht - Admin");
    }

    private void showAanvraagGemeenteMonumentOverviewView() {
        GridPane root = new GridPane();
        new AanvraagGemeenteMonumentOverviewView(root, this::showAdminView);
        scene.setRoot(root);
        primaryStage.setTitle("Gemeente Monumenten Maastricht - Alle Aanvragen");
    }

    private void showCategorieView() {
        GridPane root = new GridPane();
        new CategorieView(root, this::showAdminView);
        scene.setRoot(root);
        primaryStage.setTitle("Gemeente Monumenten Maastricht - Categorieën");
    }

    private void showLocatieView() {
        GridPane root = new GridPane();
        new LocatieView(root, this::showAdminView);
        scene.setRoot(root);
        primaryStage.setTitle("Gemeente Monumenten Maastricht - Locaties");
    }

    private void showOnderdeelView() {
        GridPane root = new GridPane();
        new OnderdeelView(root, this::showAdminView);
        scene.setRoot(root);
        primaryStage.setTitle("Gemeente Monumenten Maastricht - Onderdelen");
    }

    private void showBinnenLocatieOnderdeelView() {
        GridPane root = new GridPane();
        new BinnenLocatieOnderdeelView(root, this::showAdminView);
        scene.setRoot(root);
        primaryStage.setTitle("Gemeente Monumenten Maastricht - BinnenLocatiesOnderdelen");
    }

    private void showBuitenLocatieOnderdeelView() {
        GridPane root = new GridPane();
        new CategorieView(root, this::showAdminView);
        scene.setRoot(root);
        primaryStage.setTitle("Gemeente Monumenten Maastricht - BuitenLocatiesOnderdelen");
    }

    public static void main(String[] args) {
        launch();
    }
}
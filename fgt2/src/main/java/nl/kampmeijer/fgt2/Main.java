package nl.kampmeijer.fgt2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.kampmeijer.fgt2.views.WoontorenView;

public class Main extends Application {
    private Stage primaryStage;
    private Scene scene;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        GridPane root = new GridPane();
        scene = new Scene(root, 900, 900);
        stage.setScene(scene);
        stage.setTitle("Meldingen Gevallen Gesteente");
        showStartView();
        stage.setMaximized(true);
        stage.show();
    }

    private void showStartView() {
        GridPane root = new GridPane();
        //new StartScherm(root, this::showInschrijvenScherm, this::showAdminScherm);
        scene.setRoot(root);
        primaryStage.setTitle("Meldingen Gevallen Gesteente - Start");
    }

    private void showAdminView() {
        GridPane root = new GridPane();
//        new AdminScherm(root,
//                this::showBuurtbewonerScherm,
//                this::showSoortScherm,
//                this::showVariantScherm,
//                this::showDatumScherm,
//                this::showStarttijdScherm,
//                this::showLocatieScherm,
//                this::showMaxPersonenScherm,
//                this::showSoortVariantScherm,
//                this::showSoortMaxPersonenScherm,
//                this::showSoortDatumStarttijdLocatieScherm,
//                this::showAdminInschrijvingBuurtbewonerScherm,
//                this::showStartScherm
//        );
        scene.setRoot(root);
        primaryStage.setTitle("Meldingen Gevallen Gesteente - Admin");
    }

    private void showWoontorenView() {
        GridPane root = new GridPane();
        new WoontorenView(root, this::showAdminView);
        scene.setRoot(root);
        primaryStage.setTitle("Meldingen Gevallen Gesteente - Woontoren");
    }

    public static void main(String[] args) {
        launch();
    }
}

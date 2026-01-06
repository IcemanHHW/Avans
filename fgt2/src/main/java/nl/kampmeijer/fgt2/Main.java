package nl.kampmeijer.fgt2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.kampmeijer.fgt2.views.AdminView;
import nl.kampmeijer.fgt2.views.BouwbedrijfView;
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
        showAdminView();
        stage.setMaximized(true);
        stage.show();
    }

    private void showAdminView() {
        GridPane root = new GridPane();
        new AdminView(root,
                this::showBouwbedrijfView,
                this::showWoontorenView
        );
        scene.setRoot(root);
        primaryStage.setTitle("Meldingen Gevallen Gesteente - Admin");
    }

    private void showBouwbedrijfView() {
        GridPane root = new GridPane();
        new BouwbedrijfView(root, this::showAdminView);
        scene.setRoot(root);
        primaryStage.setTitle("Meldingen Gevallen Gesteente - Bouwbedrijf");
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

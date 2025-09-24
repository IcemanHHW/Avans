package nl.kampmeijer.fxoef0403;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        GridPane root = new GridPane();
        Scene scene = new Scene(root, 500, 500);
        new Oef0403(root);
        stage.setScene(scene);
        stage.setTitle("Oef0403");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

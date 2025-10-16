package nl.kampmeijer.fxoef0404;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        GridPane root = new GridPane();
        Scene scene = new Scene(root, 500, 500);
        new Oef0404(root);
        stage.setScene(scene);
        stage.setTitle("Oef0404");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

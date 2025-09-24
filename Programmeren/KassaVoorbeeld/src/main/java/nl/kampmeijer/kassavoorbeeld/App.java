package nl.kampmeijer.kassavoorbeeld;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        FlowPane root = new FlowPane();
        Scene scene = new Scene(root, 600, 400);
        new Kassa(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
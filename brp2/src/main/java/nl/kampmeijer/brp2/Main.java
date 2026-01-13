package nl.kampmeijer.brp2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    private Scene scene;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        GridPane root = new GridPane();
        scene = new Scene(root, 900, 900);
        stage.setScene(scene);
        stage.setTitle("Gemeente Monumenten Maastricht");
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

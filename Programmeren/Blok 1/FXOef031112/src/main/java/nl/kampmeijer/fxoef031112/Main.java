package nl.kampmeijer.fxoef031112;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override

    public void start (Stage primaryStage) {
        FlowPane root = new FlowPane();
        Scene scene = new Scene(root, 600, 600);

        new Oef031112(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("FXOef031112");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

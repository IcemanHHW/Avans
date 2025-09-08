package nl.kampmeijer.fxvb0202;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class FXVb0202 extends Application {
    @Override

    public void start (Stage primaryStage) {
        FlowPane root = new FlowPane();
        Scene scene = new Scene(root, 300, 250);

        new Vb0202(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("nl.kampmeijer.fxvb0202.Vb0202");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

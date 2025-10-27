package nl.kampmeijer.brp1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.kampmeijer.brp1.schermen.SoortScherm;
import org.jetbrains.annotations.NotNull;

public class Main extends Application {

    @Override
    public void start(@NotNull Stage stage) {
        GridPane root = new GridPane();
        Scene scene = new Scene(root, 500, 500);
        new SoortScherm(root);
        stage.setScene(scene);
        stage.setTitle("Taarten bakken in Lottum");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}

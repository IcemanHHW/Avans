package com.mycompany.movieapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 500);
        new Scherm(root);
        stage.setScene(scene);
        stage.show();               
    }

    public static void main(String[] args) {
        launch();
    }

}
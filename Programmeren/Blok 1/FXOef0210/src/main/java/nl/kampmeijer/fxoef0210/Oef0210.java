package nl.kampmeijer.fxoef0210;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class Oef0210 {
    private final Button button;
    private final Button button2;
    private final Button button3;
    private final TextField textField;
    private final TextField textField2;
    private final TextField textField3;

    public Oef0210(FlowPane p) {
        button = new Button("Button");
        button2 = new Button("Button2");
        button3 = new Button("Button3");
        textField = new TextField();
        textField2 = new TextField();
        textField3 = new TextField();

        button.setOnAction(e -> {
            textField.setText("Hello");
        });

        button2.setOnAction(e -> {
            textField2.setText("World");
        });

        button3.setOnAction(e -> {
            textField3.setText("Bananana");
        });

        p.getChildren().addAll(button, button2, button3, textField, textField2, textField3);
    }
}

package nl.kampmeijer.fxoef0209;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class Oef0209 {
    private final Button button;
    private final Button button2;
    private final TextField textField;
    private final TextField textField2;

    public Oef0209(FlowPane p) {
        button = new Button("Button");
        button2 = new Button("Button2");
        textField = new TextField();
        textField2 = new TextField();

        button.setOnAction(e -> {
            textField.setText("Maarten");
        });

        button2.setOnAction(e -> {
            textField2.setText("Kampmeijer");
        });

        p.getChildren().addAll(button, button2, textField, textField2);
    }
}

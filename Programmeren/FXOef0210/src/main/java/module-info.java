module nl.kampmeijer.fxoef0210 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens nl.kampmeijer.fxoef0210 to javafx.fxml;
    exports nl.kampmeijer.fxoef0210;
}
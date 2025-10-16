module nl.kampmeijer.fxoef0306 {
    requires javafx.controls;
    requires javafx.fxml;


    opens nl.kampmeijer.fxoef0306 to javafx.fxml;
    exports nl.kampmeijer.fxoef0306;
}
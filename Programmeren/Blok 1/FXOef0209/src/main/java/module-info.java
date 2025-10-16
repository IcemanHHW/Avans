module nl.kampmeijer.fxoef0209 {
    requires javafx.controls;
    requires javafx.fxml;


    opens nl.kampmeijer.fxoef0209 to javafx.fxml;
    exports nl.kampmeijer.fxoef0209;
}
module nl.kampmeijer.fxoef0404 {
    requires javafx.controls;
    requires javafx.fxml;


    opens nl.kampmeijer.fxoef0404 to javafx.fxml;
    exports nl.kampmeijer.fxoef0404;
}
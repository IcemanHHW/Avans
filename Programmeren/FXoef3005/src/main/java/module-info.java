module nl.kampmeijer.fxoef3005 {
    requires javafx.controls;
    requires javafx.fxml;


    opens nl.kampmeijer.fxoef3005 to javafx.fxml;
    exports nl.kampmeijer.fxoef3005;
}
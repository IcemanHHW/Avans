module nl.kampmeijer.fxvb0202 {
    requires javafx.controls;
    requires javafx.fxml;


    opens nl.kampmeijer.fxvb0202 to javafx.fxml;
    exports nl.kampmeijer.fxvb0202;
}
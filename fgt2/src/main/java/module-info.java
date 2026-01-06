module nl.kampmeijer.fgt2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens nl.kampmeijer.fgt2 to javafx.fxml;
    exports nl.kampmeijer.fgt2;
}
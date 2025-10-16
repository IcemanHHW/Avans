module nl.kampmeijer.brp1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens nl.kampmeijer.brp1 to javafx.fxml;
    exports nl.kampmeijer.brp1;
}
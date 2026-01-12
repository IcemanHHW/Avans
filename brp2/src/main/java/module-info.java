module nl.kampmeijer.brp2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens nl.kampmeijer.brp2 to javafx.fxml;
    exports nl.kampmeijer.brp2;
    exports nl.kampmeijer.brp2.models;
    opens nl.kampmeijer.brp2.models to javafx.fxml;
}
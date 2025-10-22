module nl.kampmeijer.fgt1_fysio {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.commons.dbcp2;
    requires javafx.graphics;


    opens nl.kampmeijer.fgt1_fysio to javafx.fxml;
    exports nl.kampmeijer.fgt1_fysio;
    exports nl.kampmeijer.fgt1_fysio.classes;
    opens nl.kampmeijer.fgt1_fysio.classes to javafx.fxml;
    exports nl.kampmeijer.fgt1_fysio.schermen;
    opens nl.kampmeijer.fgt1_fysio.schermen to javafx.fxml;
}
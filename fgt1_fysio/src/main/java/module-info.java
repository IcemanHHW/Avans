module nl.kampmeijer.fgt1_fysio {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.commons.dbcp2;


    opens nl.kampmeijer.fgt1_fysio to javafx.fxml;
    exports nl.kampmeijer.fgt1_fysio;
}
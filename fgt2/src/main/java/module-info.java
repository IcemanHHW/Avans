module nl.kampmeijer.fgt2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.commons.dbcp2;


    opens nl.kampmeijer.fgt2 to javafx.fxml;
    exports nl.kampmeijer.fgt2;
}
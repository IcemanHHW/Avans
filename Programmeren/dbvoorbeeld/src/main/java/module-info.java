module nl.kampmeijer.dbvoorbeeld {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.commons.dbcp2;


    opens nl.kampmeijer.dbvoorbeeld to javafx.fxml;
    exports nl.kampmeijer.dbvoorbeeld;
}
module nl.kampmeijer.brp1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.commons.dbcp2;
    requires javafx.graphics;


    opens nl.kampmeijer.brp1 to javafx.fxml;
    exports nl.kampmeijer.brp1;
    exports nl.kampmeijer.brp1.models;
    opens nl.kampmeijer.brp1.models to javafx.fxml;
    exports nl.kampmeijer.brp1.database;
    opens nl.kampmeijer.brp1.database to javafx.fxml;
}
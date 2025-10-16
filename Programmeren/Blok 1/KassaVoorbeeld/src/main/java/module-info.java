module nl.kampmeijer.kassavoorbeeld {
    requires javafx.controls;
    requires javafx.fxml;


    opens nl.kampmeijer.kassavoorbeeld to javafx.fxml;
    exports nl.kampmeijer.kassavoorbeeld;
}
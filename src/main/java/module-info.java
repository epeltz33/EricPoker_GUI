module com.poker.ericpoker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.poker.ericpoker to javafx.fxml;
    exports com.poker.ericpoker;
}
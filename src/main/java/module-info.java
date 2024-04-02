module com.poker.ericpoker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.poker.ericpoker to javafx.fxml;
    exports com.poker.ericpoker;
}
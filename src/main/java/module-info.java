module com.poker.ericpoker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;

    // Opens packages to javafx.fxml which is necessary for FXML based applications
    opens com.poker.ericpoker to javafx.fxml;

    // Open the reports package to javafx.base to allow access to ReportRow via reflection
    opens reports to javafx.base;

    // Exports the com.poker.ericpoker package, adjust if other packages need to be exported
    exports com.poker.ericpoker;
}

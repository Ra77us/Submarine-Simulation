module com.example.simulation {
    requires javafx.controls;
    requires javafx.fxml;
    requires jFuzzyLogic;


    opens com.example.simulation to javafx.fxml;
    exports com.example.simulation;
}
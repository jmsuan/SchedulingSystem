/**
 * Defines the Scheduling System module with its dependencies and exports.
 * <p>
 * This module encompasses all the core functionalities for the Scheduling System application. It interacts with JavaFX
 * for UI, requires SQL connectivity for database interactions, and uses the MySQL connector driver for JDBC operations.
 * </p>
 *
 * @author Jonathan Lane
 * @moduleGraph
 */
module lanej.schedulingsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens lanej.schedulingsystem to javafx.fxml;
    exports lanej.schedulingsystem;
    exports lanej.schedulingsystem.helper;
    exports lanej.schedulingsystem.model;
    opens lanej.schedulingsystem.controller to javafx.fxml;
    exports lanej.schedulingsystem.controller;
}
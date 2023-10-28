module lanej.schedulingsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens lanej.schedulingsystem to javafx.fxml;
    exports lanej.schedulingsystem;
}
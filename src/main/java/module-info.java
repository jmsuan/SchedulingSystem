module lanej.schedulingsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens lanej.schedulingsystem to javafx.fxml;
    exports lanej.schedulingsystem;
}
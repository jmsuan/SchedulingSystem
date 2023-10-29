package lanej.schedulingsystem;

import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.stage.Stage;
import lanej.schedulingsystem.dao.JDBC;
import lanej.schedulingsystem.helper.SceneType;
import lanej.schedulingsystem.helper.ScreenUtility;

import java.io.IOException;

public class SchedulingApplication extends Application {
    public static SceneType loginScene = new SceneType(
            "Scheduling Login",
            "view/login-screen.fxml",
            370.0,
            470.0);
    public static SceneType customersAppointmentsScene = new SceneType(
            "Schedule Manager",
            "view/customers-appointments.fxml",
            640.0,
            300.0,
            1075.0,
            580.0);
    @Override
    public void init() {

    }
    @Override
    public void start(Stage stage) throws IOException {
        // Set application icon
        // Attribution not required, but sourced here: https://uxwing.com/timetable-icon/
        Image iconImage = new Image(String.valueOf(SchedulingApplication.class.getResource("icon.png")));
        stage.getIcons().add(iconImage);

        // Set stage to the first scene (the login screen)
        ScreenUtility.changeStageScene(stage, loginScene);
    }

    public static void main(String[] args) {
        JDBC.openConnection();
        // Locale.setDefault(new Locale("fr"));
        launch();
        JDBC.closeConnection();
    }
}
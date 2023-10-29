package lanej.schedulingsystem;

import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.stage.Stage;
import lanej.schedulingsystem.dao.JDBC;
import lanej.schedulingsystem.helper.SceneType;
import lanej.schedulingsystem.helper.ScreenUtility;

import java.io.IOException;

public class SchedulingApplication extends Application {
    SceneType loginScreen = new SceneType(
            "Scheduling Login",
            "view/login-screen.fxml",
            350.0,
            430.0);
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
        ScreenUtility.changeStageScene(stage, loginScreen);
    }

    public static void main(String[] args) {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}
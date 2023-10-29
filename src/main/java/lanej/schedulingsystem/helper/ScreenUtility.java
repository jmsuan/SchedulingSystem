package lanej.schedulingsystem.helper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lanej.schedulingsystem.SchedulingApplication;

import java.io.IOException;

public abstract class ScreenUtility {
    public static void changeStageScene(Stage stage, SceneType sceneType) throws IOException {
        // Load the sceneType's FXML into a new scene object
        FXMLLoader fxmlLoader = new FXMLLoader(SchedulingApplication.class.getResource(sceneType.getFilePath()));
        Scene scene = new Scene(fxmlLoader.load());

        // Set stage attributes
        stage.setTitle(sceneType.getTitle());
        stage.setScene(scene);
        stage.setMinWidth(sceneType.getMinWidth());
        stage.setMinHeight(sceneType.getMinHeight());
        stage.setWidth(sceneType.getWidth());
        stage.setHeight(sceneType.getHeight());

        // Check if stage would be inaccessible if centered on screen,
        // adding 50.0px to height accommodate taskbar (although this isn't an exact height)
        if ((stage.getHeight() + 50.0) < Screen.getPrimary().getBounds().getHeight() &&
                stage.getWidth() < Screen.getPrimary().getBounds().getWidth()) {
            // Center the Stage on primary display
            stage.setX((Screen.getPrimary().getBounds().getWidth() - stage.getWidth()) / 2.0);
            stage.setY((Screen.getPrimary().getBounds().getHeight() - stage.getHeight()) / 2.0);
        }

        stage.show();
    }
    public static void changeStageScene(ActionEvent actionEvent, SceneType scene) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        changeStageScene(stage, scene);
    }
    public static void alert(String alertMessage) {
        Alert alert = new Alert(AlertType.ERROR, alertMessage);
        alert.showAndWait();
    }
}

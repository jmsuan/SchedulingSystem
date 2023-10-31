package lanej.schedulingsystem.helper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lanej.schedulingsystem.SchedulingApplication;

import java.io.IOException;

public abstract class ScreenUtility {
    public static void changeStageScene(Stage stage, SceneType sceneType) throws IOException {
        // If going to loginScene, clear loggedInUser
        if (sceneType.equals(SchedulingApplication.loginScene) && SchedulingApplication.loggedInUser != null) {
            SchedulingApplication.loggedInUser = null;
        }

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

    public static void changeStageScene(ActionEvent actionEvent, SceneType scene) {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        try {
            changeStageScene(stage, scene);
        } catch (IOException e) {
            alert("Error when changing stage to " + scene + " scene!\nMessage: " + e.getMessage());
        }
    }

    private static Boolean alert(AlertType type, String message) {
        Alert alert = new Alert(type, message);
        System.out.println(type + " Alert" + ": " + message);
        Boolean result = null;
        alert.showAndWait();
        if (alert.getResult() != null) {
            if (alert.getResult().equals(ButtonType.OK)) {
                result = true;
            } else if (alert.getResult().equals(ButtonType.CANCEL)) {
                result = false;
            }
        }
        return result;
    }

    public static void alert(String alertMessage) {
        alert(AlertType.ERROR, alertMessage);
    }

    public static void showInformation(String informationMessage) {
        alert(AlertType.INFORMATION, informationMessage);
    }

    public static Boolean showConfirmation(String confirmationMessage) {
        return alert(AlertType.CONFIRMATION, confirmationMessage);
    }

    public static Boolean showWarning(String warningMessage) {
        return alert(AlertType.WARNING, warningMessage);
    }
}

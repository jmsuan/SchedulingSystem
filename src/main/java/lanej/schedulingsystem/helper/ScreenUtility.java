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

/**
 * Provides utility methods for managing and changing JavaFX scenes.
 * This class simplifies scene transitions and provides alert handling in the application.
 */
public abstract class ScreenUtility {
    /**
     * Changes the scene of the provided stage to the specified SceneType.
     *
     * @param stage the stage for which the scene will be changed.
     * @param sceneType the scene type that defines the new scene details.
     * @throws IOException if there's an issue loading the FXML.
     */
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

    /**
     * Changes the scene of the stage related to the action event to the specified SceneType. Uses the actionEvent as
     * a method of obtaining the parent Stage.
     *
     * @param actionEvent the event triggering the change.
     * @param scene the scene type that defines the new scene details.
     */
    public static void changeStageScene(ActionEvent actionEvent, SceneType scene) {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        try {
            changeStageScene(stage, scene);
        } catch (IOException e) {
            alert("Error when changing stage to " + scene + " scene!\nMessage: " + e.getMessage());
        }
    }

    /**
     * Displays an alert with the provided type and message.
     *
     * @param type the type of the alert (ERROR, WARNING, etc.).
     * @param message the message to be displayed in the alert.
     * @return a Boolean indicating the user's response to the alert, if any.
     */
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

    /**
     * Displays an error alert with the given message.
     *
     * @param alertMessage the error message.
     */
    public static void alert(String alertMessage) {
        alert(AlertType.ERROR, alertMessage);
    }

    /**
     * Displays an information alert with the given message.
     *
     * @param informationMessage the informational message.
     */
    public static void showInformation(String informationMessage) {
        alert(AlertType.INFORMATION, informationMessage);
    }

    /**
     * Displays a confirmation alert and waits for the user's response.
     *
     * @param confirmationMessage the message asking for confirmation.
     * @return a Boolean indicating the user's response to the alert.
     */
    public static Boolean showConfirmation(String confirmationMessage) {
        return alert(AlertType.CONFIRMATION, confirmationMessage);
    }

    /**
     * Displays a warning alert and waits for the user's response.
     *
     * @param warningMessage the warning message.
     * @return a Boolean indicating the user's response to the alert.
     */
    public static Boolean showWarning(String warningMessage) {
        return alert(AlertType.WARNING, warningMessage);
    }
}

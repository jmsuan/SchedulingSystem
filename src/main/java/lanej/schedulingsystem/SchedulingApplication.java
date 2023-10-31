package lanej.schedulingsystem;

import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.stage.Stage;
import lanej.schedulingsystem.dao.JDBC;
import lanej.schedulingsystem.helper.SceneType;
import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

/**
 * Main application entry point for the Scheduling Application.
 * This class initializes and manages the primary stage and scenes
 * for the application, and holds all the scene information in one place for
 * convenience when switching scenes.
 * <p>
 * Keeps track of the application's resources, user authentication state,
 * and logging mechanisms.
 * </p>
 *
 * @author Jonathan Lane
 */
public class SchedulingApplication extends Application {
    /**
     * The login window.
     */
    public static SceneType loginScene = new SceneType(
            "Scheduling Login",
            "view/login-screen.fxml",
            370.0,
            470.0);
    /**
     * The main window for viewing and managing customers and appointments (has two TableViews).
     */
    public static SceneType customersAppointmentsScene = new SceneType(
            "Schedule Manager",
            "view/customers-appointments.fxml",
            880.0,
            350.0,
            1075.0,
            580.0);
    /**
     * The scene window used to add or modify customers in the application and database.
     */
    public static SceneType customerForm = new SceneType(
            "Add/Modify Customer",
            "view/customer-form.fxml",
            340.0,
            630.0);
    /**
     * The scene window used to add or modify appointments in the application and database.
     */
    public static SceneType appointmentForm = new SceneType(
            "Add/Modify Appointment",
            "view/appointment-form.fxml",
            720.0,
            530.0);
    /**
     * The scene window used to display a variety of reports/insights about the data within the application.
     */
    public static SceneType reportsView = new SceneType(
            "All Reports",
            "view/report-view.fxml",
            700.0,
            570.0);
    /**
     * The user currently signed in.
     */
    public static User loggedInUser = null;
    /**
     * Used for logging user authentication attempts to login_activity.txt.
     */
    public static PrintWriter activityLogger;

    /**
     * Sets up the app's initial window title, utilizing the lang resource bundle. to translate as needed.
     * This was only necessary because the ScreenUtility.changeStageScene method doesn't need the translating
     * functionality, otherwise I would have included it in there.
     */
    @Override
    public void init() {
        loginScene.setScreenTitle(ResourceBundle.getBundle("bundle/lang").getString(
                "loginScreenTitleLabel"));
    }

    /**
     * Opens the app and displays the login window. I used a Lambda here to close the PrintWriter that writes to
     * login_activity.txt when the window closes. It's concise and readable.
     *
     * @param stage The main window of the app.
     * @throws IOException If there's a problem setting up the window.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Set application icon
        // Attribution not required, but sourced here: https://uxwing.com/timetable-icon/
        Image iconImage = new Image(String.valueOf(SchedulingApplication.class.getResource("icon.png")));
        stage.getIcons().add(iconImage);

        stage.setOnCloseRequest(closeEvent -> activityLogger.close());

        // Set stage to the first scene (the login screen)
        ScreenUtility.changeStageScene(stage, loginScene);
    }

    /**
     * Opens the app, connects to the database, and displays the main window. Closes the database connection after
     * the stage is closed.
     *
     * @param args The command-line arguments, which aren't used here.
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        //Locale.setDefault(new Locale("fr"));
        launch();
        JDBC.closeConnection();
    }
}
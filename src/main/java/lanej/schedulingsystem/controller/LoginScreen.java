package lanej.schedulingsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lanej.schedulingsystem.SchedulingApplication;
import lanej.schedulingsystem.dao.UserDAO;
import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class LoginScreen implements Initializable {
    ResourceBundle langBundle = ResourceBundle.getBundle("bundle/lang");
    public TextField usernameField;
    public PasswordField passwordField;
    public Label titleLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    public Button loginButton;
    public Label locationLabel;


    /**
     * @param url The URL used to initialize the controller.
     * @param resourceBundle The ResourceBundle used to initialize the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate scene text with appropriate values
        titleLabel.setText(langBundle.getString("loginScreenTitleLabel"));
        usernameLabel.setText(langBundle.getString("loginScreenUsernameLabel"));
        passwordLabel.setText(langBundle.getString("loginScreenPasswordLabel"));
        loginButton.setText(langBundle.getString("loginScreenLoginButton"));
        ZoneId userZone = ZoneId.systemDefault();
        locationLabel.setText(langBundle.getString("loginScreenLocationLabel") + " " + userZone);

        // Set input fields to empty
        usernameField.setText("");
        passwordField.setText("");

        // Initialize PrintWriter
        try {
            FileWriter activityFileWriter = new FileWriter("login_activity.txt", true);
            SchedulingApplication.activityLogger = new PrintWriter(activityFileWriter);
        } catch (IOException e) {
            System.err.println("Error when attempting to create activityLogger! - " + e.getMessage());
        }
    }

    public void attemptLogin(ActionEvent actionEvent) {
        if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            ScreenUtility.alert(langBundle.getString("loginScreenAlertMissingCredentials"));
            return;
        }
        User submittedUserInfo = new User(null, usernameField.getText(), passwordField.getText());
        // Note: I would normally NOT store passwords in cleartext in this fashion.
        SchedulingApplication.activityLogger.print("Attempted log in with username \"" + submittedUserInfo.userName() +
                "\", password \""  + submittedUserInfo.password() + "\" at " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " (Local). ");
        if (UserDAO.checkForValidUser(submittedUserInfo)) {
            // After authentication, get user info (includes ID)
            SchedulingApplication.activityLogger.println("Authentication successful.");
            SchedulingApplication.loggedInUser = UserDAO.getUserByUsernameAndPassword(usernameField.getText(),
                    passwordField.getText());
            System.out.println("Successfully signed in as " + SchedulingApplication.loggedInUser);
            SchedulingApplication.activityLogger.close();
            ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.customersAppointmentsScene);
        } else {
            SchedulingApplication.activityLogger.println("Failed authentication.");
            ScreenUtility.alert(langBundle.getString("loginScreenAlertBadCredentials"));
        }
    }
}

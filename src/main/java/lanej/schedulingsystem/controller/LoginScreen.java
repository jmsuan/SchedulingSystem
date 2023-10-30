package lanej.schedulingsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import lanej.schedulingsystem.SchedulingApplication;
import lanej.schedulingsystem.dao.UserDAO;
import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.User;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
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
     * @param url
     * @param resourceBundle
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
    }

    public void attemptLogin(ActionEvent actionEvent) throws IOException {
        if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            ScreenUtility.alert(langBundle.getString("loginScreenAlertMissingCredentials"));
            return;
        }
        User submittedUserInfo = new User(null, usernameField.getText(), passwordField.getText());
        if (UserDAO.checkForValidUser(submittedUserInfo)) {
            ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.customersAppointmentsScene);
        } else {
            ScreenUtility.alert(langBundle.getString("loginScreenAlertBadCredentials"));
        }
    }
}

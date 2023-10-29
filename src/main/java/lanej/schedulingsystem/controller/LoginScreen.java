package lanej.schedulingsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import lanej.schedulingsystem.SchedulingApplication;
import lanej.schedulingsystem.helper.ScreenUtility;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class LoginScreen implements Initializable {
    ResourceBundle langBundle = ResourceBundle.getBundle("bundle/lang");
    public Label titleLabel;
    public Label nameLabel;
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
        nameLabel.setText(langBundle.getString("loginScreenNameLabel"));
        passwordLabel.setText(langBundle.getString("loginScreenPasswordLabel"));
        loginButton.setText(langBundle.getString("loginScreenLoginButton"));
        ZoneId userZone = ZoneId.systemDefault();
        locationLabel.setText(langBundle.getString("loginScreenLocationLabel") + " " + userZone);
    }

    public void attemptLogin(ActionEvent actionEvent) throws IOException {
        // TODO: Authenticate user
        ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.customersAppointmentsScene);
        // ScreenUtility.alert(langBundle.getString("loginScreenAlertBadCredentials"));
    }
}

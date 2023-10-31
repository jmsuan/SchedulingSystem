package lanej.schedulingsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lanej.schedulingsystem.model.Appointment;

import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentForm implements Initializable {
    public static Appointment appointmentToModify = null;
    public Label titleLabel;
    public TextField idField;
    public TextField titleField;
    public TextArea descriptionArea;
    public TextField locationField;
    public TextField typeField;
    public Label startLabel;
    public DatePicker startDate;
    public Spinner startHour;
    public Spinner startMinute;
    public ComboBox startTimePeriod;
    public Label endLabel;
    public DatePicker endDate;
    public Spinner endHour;
    public Spinner endMinute;
    public ComboBox endTimePeriod;
    public ComboBox customerBox;
    public ComboBox userBox;
    public ComboBox contactBox;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void startDateSelected(ActionEvent actionEvent) {
    }

    public void submitButtonPressed(ActionEvent actionEvent) {
    }

    public void cancelButtonPressed(ActionEvent actionEvent) {
    }
}

package lanej.schedulingsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lanej.schedulingsystem.SchedulingApplication;
import lanej.schedulingsystem.dao.AppointmentDAO;
import lanej.schedulingsystem.dao.ContactDAO;
import lanej.schedulingsystem.dao.CustomerDAO;
import lanej.schedulingsystem.dao.UserDAO;
import lanej.schedulingsystem.helper.TimeUtility;
import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.Appointment;
import lanej.schedulingsystem.model.Contact;
import lanej.schedulingsystem.model.Customer;
import lanej.schedulingsystem.model.User;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class AppointmentForm implements Initializable {
    public static Appointment appointmentToModify;
    public Label titleLabel;
    public TextField idField;
    public TextField titleField;
    public TextArea descriptionArea;
    public TextField locationField;
    public TextField typeField;
    public Label startLabel;
    public DatePicker startDate;
    public Spinner<Integer> startHour;
    public Spinner<Integer> startMinute;
    public ComboBox<String> startTimePeriod;
    public Label endLabel;
    public DatePicker endDate;
    public Spinner<Integer> endHour;
    public Spinner<Integer> endMinute;
    public ComboBox<String> endTimePeriod;
    public ComboBox<Customer> customerBox;
    public ComboBox<User> userBox;
    public ComboBox<Contact> contactBox;

    public void startDateSelected() {
        endDate.setValue(startDate.getValue());
    }

    public void submitButtonPressed(ActionEvent actionEvent) {
        // TODO : validate inputs
        LocalDateTime start = LocalDateTime.of(startDate.getValue(), TimeUtility.createLocalTime(
                startHour.getValue(), startMinute.getValue(), startTimePeriod.getValue()));
        LocalDateTime end = LocalDateTime.of(endDate.getValue(), TimeUtility.createLocalTime(
                endHour.getValue(), endMinute.getValue(), endTimePeriod.getValue()));
        if (appointmentToModify != null) { // Appointment already exists
            appointmentToModify.setTitle(titleField.getText());
            appointmentToModify.setDescription(descriptionArea.getText());
            appointmentToModify.setLocation(locationField.getText());
            appointmentToModify.setType(typeField.getText());
            appointmentToModify.setStart(start);
            appointmentToModify.setEnd(end);
            appointmentToModify.setCustomer(customerBox.getValue());
            appointmentToModify.setUser(userBox.getValue());
            appointmentToModify.setContact(contactBox.getValue());
            AppointmentDAO.update(appointmentToModify);
        } else { // We are creating a new appointment
            AppointmentDAO.insert(new Appointment(
                    null,
                    titleField.getText(),
                    descriptionArea.getText(),
                    locationField.getText(),
                    typeField.getText(),
                    start,
                    end,
                    customerBox.getValue(),
                    userBox.getValue(),
                    contactBox.getValue()
            ));
        }
        appointmentToModify = null;
        ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.customersAppointmentsScene);
    }

    public void cancelButtonPressed(ActionEvent actionEvent) {
        if (ScreenUtility.showConfirmation("Are you sure you want to cancel?\n" +
                "All new information entered in the form will be lost.")) {
            appointmentToModify = null;
            ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.customersAppointmentsScene);
        }
    }

    /**
     * @param url The URL used to initialize the controller.
     * @param resourceBundle The ResourceBundle used to initialize the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate time selectors
        startLabel.setText("Start Date and Time (" + ZoneId.systemDefault().getDisplayName(TextStyle.SHORT,
                Locale.getDefault()) + "):");
        startHour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 8));
        startMinute.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0, 5));
        startHour.getValueFactory().setWrapAround(true);
        startMinute.getValueFactory().setWrapAround(true);
        startTimePeriod.getItems().add("AM");
        startTimePeriod.getItems().add("PM");
        endLabel.setText("End Date and Time (" + ZoneId.systemDefault().getDisplayName(TextStyle.SHORT,
                Locale.getDefault()) + "):");
        endHour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 10));
        endMinute.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0, 5));
        endHour.getValueFactory().setWrapAround(true);
        endMinute.getValueFactory().setWrapAround(true);
        endTimePeriod.getItems().add("AM");
        endTimePeriod.getItems().add("PM");

        // Populate ComboBoxes
        customerBox.getItems().addAll(CustomerDAO.getAllCustomers());
        userBox.getItems().addAll(UserDAO.getAllUsers());
        contactBox.getItems().addAll(ContactDAO.getAllContacts());

        if (appointmentToModify != null) {
            titleLabel.setText("Modify Appointment");
            idField.setText(String.valueOf(appointmentToModify.getId()));
            titleField.setText(appointmentToModify.getTitle());
            descriptionArea.setText(appointmentToModify.getDescription());
            locationField.setText(appointmentToModify.getLocation());
            typeField.setText(appointmentToModify.getType());
            startDate.setValue(appointmentToModify.getStart().toLocalDate());
            startHour.getValueFactory().setValue(appointmentToModify.getStart().getHour());
            startMinute.getValueFactory().setValue(appointmentToModify.getStart().getMinute());
            endDate.setValue(appointmentToModify.getEnd().toLocalDate());
            endHour.getValueFactory().setValue(appointmentToModify.getEnd().getHour());
            endMinute.getValueFactory().setValue(appointmentToModify.getEnd().getMinute());
            customerBox.setValue(appointmentToModify.getCustomer());
            userBox.setValue(appointmentToModify.getUser());
            contactBox.setValue(appointmentToModify.getContact());
        } else {
            idField.setText("(Automatically Generated)");
            titleLabel.setText("Add Appointment");
        }
    }
}

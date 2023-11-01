package lanej.schedulingsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lanej.schedulingsystem.SchedulingApplication;
import lanej.schedulingsystem.dao.AppointmentDAO;
import lanej.schedulingsystem.dao.ContactDAO;
import lanej.schedulingsystem.dao.CustomerDAO;
import lanej.schedulingsystem.dao.UserDAO;
import lanej.schedulingsystem.helper.ConverterUtility;
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

/**
 * Controller for the appointment form screen used to add or modify appointments.
 *
 * @author Jonathan Lane
 * @version 1.0
 */
public class AppointmentForm implements Initializable {
    /** Appointment object used to transfer information from user selection in previous scenes. */
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

    /**
     * Sets the end date to be the same as the start date when the start date is selected.
     */
    public void startDateSelected() {
        endDate.setValue(startDate.getValue());
    }

    /**
     * Event handler for when the submit button is pressed. Validates the user's input, and checks if the appointment
     * time isn't fully within business hours. Saves the input into either an appointment update or new appointment.
     *
     * @param actionEvent the event triggered by pressing the button.
     */
    public void submitButtonPressed(ActionEvent actionEvent) {
        // Check for missing fields
        if (titleField.getText().isBlank() || descriptionArea.getText().isBlank() ||
                locationField.getText().isBlank() || typeField.getText().isBlank() ||
                contactBox.getValue() == null || userBox.getValue() == null || customerBox.getValue() == null ||
                startHour.getValue() == null || startMinute.getValue() == null || startDate.getValue() == null ||
                endHour.getValue() == null || endMinute.getValue() == null || endDate.getValue() == null) {
            ScreenUtility.alert(
                    "One or more fields are empty. Please fill in all information before proceeding.");
            return;
        }

        // Check if time is within working hours
        LocalDateTime start = LocalDateTime.of(startDate.getValue(), TimeUtility.createLocalTime(
                startHour.getValue(), startMinute.getValue(), startTimePeriod.getValue()));
        LocalDateTime end = LocalDateTime.of(endDate.getValue(), TimeUtility.createLocalTime(
                endHour.getValue(), endMinute.getValue(), endTimePeriod.getValue()));
        if (start.isAfter(end)) {
            ScreenUtility.alert("The chosen appointment time is invalid! The start time must be before " +
                    "the end time. Please try again.");
            return;
        }
        if (!TimeUtility.detectIfWithinWorkHours(start, end)) {
            ScreenUtility.alert("The chosen appointment time is outside of work hours! Please correct the " +
                    "time or reschedule the appointment.");
            return;
        }

        // Check if any appointments for the customer would overlap
        for (Appointment appointment : ConverterUtility.getAllAppointmentsOfCustomer(customerBox.getValue())) {
            if (appointmentToModify != null) {
                if (appointment.getAppointmentId() == appointmentToModify.getAppointmentId()) {
                    continue;
                }
            }
            if (TimeUtility.detectOverlap(appointment.getStart(), appointment.getEnd(), start, end)) {
                ScreenUtility.alert("This customer has an appointment overlapping the specified time " +
                        "window. Please correct the customer selection, time or reschedule the appointment.");
                return;
            }
        }

        // Add or update appointment because input passed all checks
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

    /**
     * Event handler for when the cancel button is pressed. Takes the user back to the main customer/appointment list
     * screen after a confirmation.
     *
     * @param actionEvent the event triggered by pressing the button.
     */
    public void cancelButtonPressed(ActionEvent actionEvent) {
        if (ScreenUtility.showConfirmation("Are you sure you want to cancel?\n" +
                "All new information entered in the form will be lost.")) {
            appointmentToModify = null;
            ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.customersAppointmentsScene);
        }
    }

    /**
     * Initialization method called when the form is loaded. Sets time spinners to be reasonably accessible, and if we
     * are modifying an existing appointment, populate the form fields.
     *
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
        startTimePeriod.setValue("AM");
        endLabel.setText("End Date and Time (" + ZoneId.systemDefault().getDisplayName(TextStyle.SHORT,
                Locale.getDefault()) + "):");
        endHour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 10));
        endMinute.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0, 5));
        endHour.getValueFactory().setWrapAround(true);
        endMinute.getValueFactory().setWrapAround(true);
        endTimePeriod.getItems().add("AM");
        endTimePeriod.getItems().add("PM");
        endTimePeriod.setValue("AM");

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
            startHour.getValueFactory().setValue(TimeUtility.shortHour(appointmentToModify.getStart().toLocalTime()));
            startMinute.getValueFactory().setValue(appointmentToModify.getStart().getMinute());
            startTimePeriod.setValue(TimeUtility.amOrPm(appointmentToModify.getStart().toLocalTime()));
            endDate.setValue(appointmentToModify.getEnd().toLocalDate());
            endHour.getValueFactory().setValue(TimeUtility.shortHour(appointmentToModify.getEnd().toLocalTime()));
            endMinute.getValueFactory().setValue(appointmentToModify.getEnd().getMinute());
            endTimePeriod.setValue(TimeUtility.amOrPm(appointmentToModify.getEnd().toLocalTime()));
            customerBox.setValue(appointmentToModify.getCustomer());
            userBox.setValue(appointmentToModify.getUser());
            contactBox.setValue(appointmentToModify.getContact());
        } else {
            idField.setText("(Automatically Generated)");
            titleLabel.setText("Add Appointment");
        }
    }
}

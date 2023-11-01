package lanej.schedulingsystem.controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import lanej.schedulingsystem.SchedulingApplication;
import lanej.schedulingsystem.dao.AppointmentDAO;
import lanej.schedulingsystem.dao.ContactDAO;
import lanej.schedulingsystem.dao.CustomerDAO;
import lanej.schedulingsystem.helper.ConverterUtility;
import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.Appointment;
import lanej.schedulingsystem.model.Contact;
import lanej.schedulingsystem.model.Customer;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

/**
 * Represents the ReportView controller responsible for displaying various report-based functionalities.
 *
 * @author Jonathan Lane
 * @version 1.0
 */
public class ReportView implements Initializable {
    public TableView<Appointment> appointmentTable;
    public TableColumn<Appointment, Integer> appointmentIdColumn;
    public TableColumn<Appointment, String> appointmentTitleColumn;
    public TableColumn<Appointment, String> appointmentDescriptionColumn;
    public TableColumn<Appointment, String> appointmentTypeColumn;
    public TableColumn<Appointment, LocalDateTime> appointmentStartColumn;
    public TableColumn<Appointment, LocalDateTime> appointmentEndColumn;
    public TableColumn<Appointment, Customer> appointmentCustomerColumn;
    public ComboBox<Month> monthBox;
    public ComboBox<String> typeBox;
    public Text filterTotal;
    public ComboBox<Contact> contactBox;
    public ToggleGroup countChoice;
    public Text customerCount;
    public Text allCustomerCount;
    public RadioButton withAppointmentsChoice;
    public RadioButton noAppointmentsChoice;

    /**
     * Navigates the user back to the previous screen.
     *
     * @param actionEvent the event triggered by the button click
     */
    public void goBackButton(ActionEvent actionEvent) {
        ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.customersAppointmentsScene);
    }

    /**
     * Updates the report based on the selected month and type from the ComboBoxes.
     * <p>
     * This method filters and counts the appointments based on the selected criteria.
     * </p>
     */
    public void updateReport1() {
        // Get list of appointments with month
        List<Appointment> appointmentsWithMonth = new ArrayList<>();
        for (Appointment appointment : AppointmentDAO.getAllAppointments()) {
            if (appointment.getStart().getMonth() == monthBox.getValue() ||
                    appointment.getEnd().getMonth() == monthBox.getValue()) {
                appointmentsWithMonth.add(appointment);
            }
        }

        // Get list of appointments with type
        List<Appointment> appointmentsWithType = new ArrayList<>();
        for (Appointment appointment : AppointmentDAO.getAllAppointments()) {
            if (appointment.getType().equals(typeBox.getValue())) {
                appointmentsWithType.add(appointment);
            }
        }

        // Check if both are selected
        if (monthBox.getValue() != null && typeBox.getValue() != null) {
            // Count number of appointments in month by checking type
            int count = 0;
            for (Appointment appointment : appointmentsWithMonth) {
                if (appointment.getType().equals(typeBox.getValue())) {
                    count++;
                }
            }
            filterTotal.setText(((Integer)count).toString());
        } else if (monthBox.getValue() != null) { // Month selected
            filterTotal.setText(((Integer)appointmentsWithMonth.size()).toString());
        } else if (typeBox.getValue() != null) { // Type selected
            filterTotal.setText(((Integer)appointmentsWithType.size()).toString());
        } else { // Somehow none are selected
            filterTotal.setText("0");
        }
    }

    /**
     * Updates the TableView to display appointments based on the selected contact.
     * <p>
     * This method formats the displayed data appropriately and filters the appointments for a specific contact.
     * </p>
     */
    public void updateReport2() {
        // Populating Appointment TableView
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
        appointmentCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("Customer"));
        // Set format for start and end times
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // Cell factory input lambda
        Callback<TableColumn<Appointment, LocalDateTime>, TableCell<Appointment, LocalDateTime>> timeImplementation =
                column -> new TableCell<>() {
                    @Override
                    protected void updateItem(LocalDateTime date, boolean empty) {
                        super.updateItem(date, empty);
                        if (empty || date == null) {
                            setText(null);
                        } else {
                            setText(timeFormat.format(date) + " " + ZoneId.systemDefault().getDisplayName(
                                    TextStyle.SHORT,
                                    Locale.getDefault()));
                        }
                    }
                };

        // Set start and end time formatting
        appointmentStartColumn.setCellFactory(timeImplementation);
        appointmentEndColumn.setCellFactory(timeImplementation);

        // Set Customer_ID formatting
        appointmentCustomerColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Customer customer, boolean empty) {
                super.updateItem(customer, empty);
                if (empty || customer == null) {
                    setText(null);
                } else {
                    setText(((Integer)customer.getCustomerId()).toString());
                }
            }
        });

        // Finish populating Table
        ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
        appointments.sort(Comparator.comparingInt(Appointment::getAppointmentId));
        FilteredList<Appointment> appointmentFilteredList = new FilteredList<>(appointments);
        appointmentTable.setItems(appointmentFilteredList);
        appointmentTable.getSortOrder().add(appointmentIdColumn);
        appointmentTable.sort();

        // Filter to only show the one Contact's Appointments
        appointmentFilteredList.setPredicate(item -> {
            if (contactBox.getValue() == null) {
                return true;
            } else {
                return item.getContact().getContactId() == contactBox.getValue().getContactId();
            }
        });
    }

    /**
     * Updates the report displaying count of customers based on their appointment status.
     * <p>
     * The report shows counts for customers with and without appointments.
     * </p>
     */
    public void updateReport3() {
        // Get all customer count
        Integer countAll = CustomerDAO.getAllCustomers().size();
        allCustomerCount.setText(String.valueOf(countAll));

        // Find which customers have appointments
        HashSet<Customer> customersWithAppointments = new HashSet<>();
        for (Appointment appointment : AppointmentDAO.getAllAppointments()) {
            customersWithAppointments.add(appointment.getCustomer());
        }

        // Find values
        Integer countWithAppointments = customersWithAppointments.size();
        Integer countWithoutAppointments = countAll - countWithAppointments;

        // Set values
        if (withAppointmentsChoice.isSelected()) {
            customerCount.setText(String.valueOf(countWithAppointments));
        } else if (noAppointmentsChoice.isSelected()) {
            customerCount.setText(String.valueOf(countWithoutAppointments));
        }
    }

    /**
     * Initializes the controller by setting up the ComboBox values and default report values.
     *
     * @param url The URL used to initialize the controller.
     * @param resourceBundle The ResourceBundle used to initialize the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate Report One ComboBoxes
        monthBox.getItems().addAll(Month.values());
        typeBox.getItems().addAll(ConverterUtility.getAllAppointmentTypes());

        // Populate Report Two ComboBox
        contactBox.getItems().addAll(ContactDAO.getAllContacts());

        // Populate Report Three
        updateReport3();
    }
}

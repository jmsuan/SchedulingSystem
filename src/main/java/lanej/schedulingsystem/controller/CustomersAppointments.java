package lanej.schedulingsystem.controller;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import lanej.schedulingsystem.SchedulingApplication;
import lanej.schedulingsystem.dao.AppointmentDAO;
import lanej.schedulingsystem.dao.CustomerDAO;
import lanej.schedulingsystem.helper.ConverterUtility;
import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.helper.TimeUtility;
import lanej.schedulingsystem.model.Appointment;
import lanej.schedulingsystem.model.Customer;
import lanej.schedulingsystem.model.TableSearchable;
import lanej.schedulingsystem.model.User;

import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CustomersAppointments implements Initializable {
    public TableView<Customer> customerTable;
    public TableColumn<Customer, Integer> customerIdColumn;
    public TableColumn<Customer, String> customerNameColumn;
    public TableColumn<Customer, String> customerDivisionColumn;
    public TextField customerSearchField;
    public TableView<Appointment> appointmentTable;
    public TableColumn<Appointment, Integer> appointmentIdColumn;
    public TableColumn<Appointment, String> appointmentTitleColumn;
    public TableColumn<Appointment, String> appointmentDescriptionColumn;
    public TableColumn<Appointment, String> appointmentLocationColumn;
    public TableColumn<Appointment, String> appointmentContactColumn;
    public TableColumn<Appointment, String> appointmentTypeColumn;
    public TableColumn<Appointment, LocalDateTime> appointmentStartColumn;
    public TableColumn<Appointment, LocalDateTime> appointmentEndColumn;
    public TableColumn<Appointment, Customer> appointmentCustomerColumn;
    public TableColumn<Appointment, User> appointmentUserColumn;
    public TextField appointmentSearchField;
    public ToggleGroup scheduleRadioGroup;
    public FilteredList<Appointment> appointmentFilteredList;
    public RadioButton allToggle;
    public RadioButton weekToggle;
    public RadioButton monthToggle;
    public Label noticeLabel;

    private ChangeListener<Object> createTableSearchListener(FilteredList<? extends TableSearchable> filteredList) {
        return (observableValue, oldValue, newValue) -> {
            scheduleRadioGroup.selectToggle(allToggle);
            String searchText = newValue.toString().toLowerCase();

            // Checks if each item matches condition
            filteredList.setPredicate(item -> {
                if (searchText.isEmpty()) {
                    return true; // Nothing in search field. Show all
                }
                if (searchText.matches("\\d+")) { // searchText starts with a number, check id column.
                    return item.getId() == Integer.parseInt(searchText);
                } else { // searchText is a text search (for title or name)
                    return item.getName().toLowerCase().contains(searchText); // Returns false if not met
                }
            });

            if (filteredList.isEmpty() && !searchText.isEmpty()) {
                ScreenUtility.showWarning("No items were found for the provided search criteria!\n" +
                        "Search for a valid ID number or name/title.");
            }
        };
    }

    public void addCustomerButton(ActionEvent actionEvent) {
        CustomerForm.customerToModify = null;
        ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.customerForm);
    }

    public void updateCustomerButton(ActionEvent actionEvent) {
        if (!customerTable.getSelectionModel().isEmpty()) {
            CustomerForm.customerToModify = customerTable.getSelectionModel().getSelectedItem();
            ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.customerForm);
        } else {
            ScreenUtility.showWarning("You must select a customer from the table.");
        }
    }

    public void deleteCustomerButton(ActionEvent actionEvent) {
        if (!customerTable.getSelectionModel().isEmpty()) {
            if (ScreenUtility.showConfirmation(
                    "Are you sure you want to delete this customer?\n" +
                            "Deleting this customer will delete all appointments associated with the customer.")) {
                Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
                // Delete all Appointments with customer
                for (Appointment appointment : ConverterUtility.getAllAppointmentsOfCustomer(selectedCustomer)) {
                    AppointmentDAO.delete(appointment);
                }
                CustomerDAO.delete(selectedCustomer);
                ScreenUtility.showInformation(
                        "Customer and any associated appointments have been deleted.");
                // Refresh tables
                ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.customersAppointmentsScene);
            }
        } else {
            ScreenUtility.showWarning("You must select a customer from the table.");
        }
    }

    public void addAppointmentButton(ActionEvent actionEvent) {
        AppointmentForm.appointmentToModify = null;
        ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.appointmentForm);
    }

    public void updateAppointmentButton(ActionEvent actionEvent) {
        if (!appointmentTable.getSelectionModel().isEmpty()) {
            AppointmentForm.appointmentToModify = appointmentTable.getSelectionModel().getSelectedItem();
            ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.appointmentForm);
        } else {
            ScreenUtility.showWarning("You must select an appointment from the table.");
        }
    }

    public void deleteAppointmentButton(ActionEvent actionEvent) {
        if (!appointmentTable.getSelectionModel().isEmpty()) {
            if (ScreenUtility.showConfirmation("Are you sure you want to delete this appointment?")) {
                Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
                AppointmentDAO.delete(selectedAppointment);
                ScreenUtility.showInformation(
                        "Appointment with ID " + selectedAppointment.getAppointmentId() +
                                " and type \"" + selectedAppointment.getType() +
                                "\" successfully deleted.");
                // Refresh tables
                ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.customersAppointmentsScene);
            }
        } else {
            ScreenUtility.showWarning("You must select an appointment from the table.");
        }
    }

    public void logoutButton(ActionEvent actionEvent) {
        if (ScreenUtility.showConfirmation("Are you sure you want to log out?")) {
            ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.loginScene);
            SchedulingApplication.loggedInUser = null;
        }
    }

    /**
     * @param url The URL used to initialize the controller.
     * @param resourceBundle The ResourceBundle used to initialize the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate Customer TableView
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        ObservableList<Customer> customers = CustomerDAO.getAllCustomers();
        customers.sort(Comparator.comparingInt(Customer::getCustomerId));
        FilteredList<Customer> customerFilteredList = new FilteredList<>(customers);
        customerTable.setItems(customerFilteredList);
        customerTable.getSortOrder().add(customerIdColumn);
        customerTable.sort();

        // Start populating Appointment TableView
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
        appointmentContactColumn.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
        appointmentCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("Customer"));
        appointmentUserColumn.setCellValueFactory(new PropertyValueFactory<>("User"));

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

        // Finish populating Appointment TableView
        ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
        appointments.sort(Comparator.comparingInt(Appointment::getAppointmentId));
        appointmentFilteredList = new FilteredList<>(appointments);
        appointmentTable.setItems(appointmentFilteredList);
        appointmentTable.getSortOrder().add(appointmentIdColumn);
        appointmentTable.sort();

        // Filter tables by search criteria
        customerSearchField.textProperty().addListener(createTableSearchListener(customerFilteredList));
        appointmentSearchField.textProperty().addListener(createTableSearchListener(appointmentFilteredList));

        // Test to see if there are any appointments coming up soon for the user
        List<Appointment> userAppointments = ConverterUtility.getAllAppointmentsOfUser(
                SchedulingApplication.loggedInUser);
        LocalDateTime nowPlus15 = LocalDateTime.now().plusMinutes(15);
        for (Appointment appointment : userAppointments) {
            if (TimeUtility.detectOverlap(
                    LocalDateTime.now(), nowPlus15,
                    appointment.getStart(), appointment.getEnd())) {
                ScreenUtility.showInformation("You have an upcoming appointment! Here are the details" +
                        ":\n" +
                        "\n" +
                        "Appointment ID: " + appointment.getAppointmentId() + "\n" +
                        "Start Date/Time: " + timeFormat.format(appointment.getStart()) + " " +
                        ZoneId.systemDefault().getDisplayName(TextStyle.SHORT, Locale.getDefault()) + "\n" +
                        "End Date/Time: " + timeFormat.format(appointment.getEnd()) + " " +
                        ZoneId.systemDefault().getDisplayName(TextStyle.SHORT, Locale.getDefault()));
                noticeLabel.setText("");
                break;
            }
        }

    }

    public void weekFilterSelected() {
        appointmentSearchField.setText("");
        appointmentFilteredList.setPredicate(item -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startOfWeek = LocalDateTime.of(
                    LocalDate.from(now.with(DayOfWeek.SUNDAY)), LocalTime.of(0,0)).minusWeeks(1);
            LocalDateTime endOfWeek = startOfWeek.plusWeeks(1);
            return TimeUtility.detectOverlap(startOfWeek, endOfWeek, item.getStart(), item.getEnd());
        });
        weekToggle.setSelected(true);
    }

    public void monthFilterSelected() {
        appointmentSearchField.setText("");
        appointmentFilteredList.setPredicate(item -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0);
            LocalDateTime endOfMonth = startOfMonth.plusMonths(1);

            return TimeUtility.detectOverlap(startOfMonth, endOfMonth, item.getStart(), item.getEnd());
        });
        monthToggle.setSelected(true);
    }

    public void allFilterSelected() {
        appointmentFilteredList.setPredicate(null);
        allToggle.setSelected(true);
    }

    public void reportsButton(ActionEvent actionEvent) {
        ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.reportsView);
    }
}

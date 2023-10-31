package lanej.schedulingsystem.controller;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lanej.schedulingsystem.SchedulingApplication;
import lanej.schedulingsystem.dao.AppointmentDAO;
import lanej.schedulingsystem.dao.CustomerDAO;
import lanej.schedulingsystem.helper.ConverterUtility;
import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.Appointment;
import lanej.schedulingsystem.model.Customer;
import lanej.schedulingsystem.model.TableSearchable;
import lanej.schedulingsystem.model.User;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Comparator;
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

    private ChangeListener<Object> createTableSearchListener(FilteredList<? extends TableSearchable> filteredList) {
        return (observableValue, oldValue, newValue) -> {
            String searchText = newValue.toString().toLowerCase();

            // Checks if each item matches condition
            filteredList.setPredicate(item -> {
                if (searchText.isEmpty()) {
                    return true;
                }
                if (searchText.matches("\\d+")) {
                    return item.getId() == Integer.parseInt(searchText);
                }
                else return item.getName().toLowerCase().contains(searchText); // Returns false if not met
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
                        "Customer and all associated appointments have been deleted.");
                // Refresh tables
                ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.customersAppointmentsScene);
            }
        } else {
            ScreenUtility.showWarning("You must select a customer from the table.");
        }
    }

    public void addAppointmentButton(ActionEvent actionEvent) {
        AppointmentForm.appointmentToModify = null;
        ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.customerForm);
    }

    public void updateAppointmentButton(ActionEvent actionEvent) {
        if (!appointmentTable.getSelectionModel().isEmpty()) {
            AppointmentForm.appointmentToModify = appointmentTable.getSelectionModel().getSelectedItem();
            ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.customerForm);
        } else {
            ScreenUtility.showWarning("You must select an appointment from the table.");
        }
    }

    public void deleteAppointmentButton(ActionEvent actionEvent) {
        // TODO : write out the info that's needed in the alerts
        if (!appointmentTable.getSelectionModel().isEmpty()) {
            if (ScreenUtility.showConfirmation("Are you sure you want to delete this appointment?")) {
                Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
                AppointmentDAO.delete(selectedAppointment);
                ScreenUtility.showInformation("Appointment successfully deleted.");
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

        // Populate Appointment TableView
        // TODO : Fix the timestamp display
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
        ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
        appointments.sort(Comparator.comparingInt(Appointment::getAppointmentId));
        FilteredList<Appointment> appointmentFilteredList = new FilteredList<>(appointments);
        appointmentTable.setItems(appointmentFilteredList);
        appointmentTable.getSortOrder().add(appointmentIdColumn);
        appointmentTable.sort();

        // Filter tables by search criteria
        customerSearchField.textProperty().addListener(createTableSearchListener(customerFilteredList));
        appointmentSearchField.textProperty().addListener(createTableSearchListener(appointmentFilteredList));
    }
}

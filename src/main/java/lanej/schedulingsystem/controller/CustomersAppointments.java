package lanej.schedulingsystem.controller;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import lanej.schedulingsystem.SchedulingApplication;
import lanej.schedulingsystem.dao.AppointmentDAO;
import lanej.schedulingsystem.dao.CustomerDAO;
import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.Appointment;
import lanej.schedulingsystem.model.Customer;
import lanej.schedulingsystem.model.TableSearchable;
import lanej.schedulingsystem.model.User;

import java.io.IOException;
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
    private FilteredList<Customer> customerFilteredList;
    private FilteredList<Appointment> appointmentFilteredList;

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
                        "Search for a valid ID number or name.");
            }
        };
    }
    public void addCustomerButton(ActionEvent actionEvent) {
    }

    public void updateCustomerButton(ActionEvent actionEvent) {
    }

    public void deleteCustomerButton(ActionEvent actionEvent) {
    }

    public void addAppointmentButton(ActionEvent actionEvent) {
    }

    public void updateAppointmentButton(ActionEvent actionEvent) {
    }

    public void deleteAppointmentButton(ActionEvent actionEvent) {
    }

    public void logoutButton(ActionEvent actionEvent) throws IOException {
        ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.loginScene);
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
        customerFilteredList = new FilteredList<>(customers);
        customerTable.setItems(customerFilteredList);
        customerTable.getSortOrder().add(customerIdColumn);
        customerTable.sort();

        // TODO: Populate Appointment TableView
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
        appointmentFilteredList = new FilteredList<>(appointments);
        appointmentTable.setItems(appointmentFilteredList);
        appointmentTable.getSortOrder().add(appointmentIdColumn);
        appointmentTable.sort();

        // Filter tables by search criteria
        customerSearchField.textProperty().addListener(createTableSearchListener(customerFilteredList));
        appointmentSearchField.textProperty().addListener(createTableSearchListener(appointmentFilteredList));

    }
}

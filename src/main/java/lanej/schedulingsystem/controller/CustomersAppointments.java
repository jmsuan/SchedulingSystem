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
import lanej.schedulingsystem.dao.CustomerDAO;
import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.Customer;
import lanej.schedulingsystem.model.TableSearchable;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class CustomersAppointments implements Initializable {
    public TableView<Customer> customerTable;
    public TableColumn<Customer, Integer> customerIdColumn;
    public TableColumn<Customer, Integer> customerNameColumn;
    public TableColumn<Customer, Integer> customerDivisionColumn;
    public TextField customerSearchField;
    public TableView appointmentTable;
    public TableColumn appointmentIdColumn;
    public TableColumn appointmentTitleColumn;
    public TableColumn appointmentDescriptionColumn;
    public TableColumn appointmentLocationColumn;
    public TableColumn appointmentContactColumn;
    public TableColumn appointmentTypeColumn;
    public TableColumn appointmentStartColumn;
    public TableColumn appointmentEndColumn;
    public TableColumn appointmentCustomerColumn;
    public TableColumn appointmentUserColumn;
    public TextField appointmentSearchField;
    public ToggleGroup scheduleRadioGroup;
    private FilteredList<Customer> customerFilteredList;

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

        // Filter tables by search criteria
        customerSearchField.textProperty().addListener(createTableSearchListener(customerFilteredList));

    }
}

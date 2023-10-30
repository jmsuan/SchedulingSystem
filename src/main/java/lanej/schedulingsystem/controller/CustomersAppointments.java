package lanej.schedulingsystem.controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lanej.schedulingsystem.SchedulingApplication;
import lanej.schedulingsystem.dao.CustomerDAO;
import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.Customer;

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
    private FilteredList<Customer> customerFilteredList;

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
    }
}

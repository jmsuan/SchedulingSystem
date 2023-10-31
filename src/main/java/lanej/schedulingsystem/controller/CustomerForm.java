package lanej.schedulingsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lanej.schedulingsystem.SchedulingApplication;
import lanej.schedulingsystem.dao.CountryDAO;
import lanej.schedulingsystem.dao.CustomerDAO;
import lanej.schedulingsystem.dao.FirstLevelDivisionDAO;
import lanej.schedulingsystem.helper.ConverterUtility;
import lanej.schedulingsystem.helper.ScreenUtility;
import lanej.schedulingsystem.model.Country;
import lanej.schedulingsystem.model.Customer;
import lanej.schedulingsystem.model.FirstLevelDivision;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerForm implements Initializable {
    public static Customer customerToModify;
    public TextField idField;
    public TextField nameField;
    public TextField addressField;
    public ComboBox<Country> countryBox;
    public ComboBox<FirstLevelDivision> divisionBox;
    public TextField postalField;
    public TextField phoneField;
    public Label titleLabel;

    public void submitButtonPressed(ActionEvent actionEvent) {
        if (nameField.getText().isBlank() || addressField.getText().isBlank() || postalField.getText().isBlank() ||
            phoneField.getText().isBlank() || divisionBox.getValue() == null) {
            ScreenUtility.alert(
                    "One or more fields are empty. Please fill in all information before proceeding.");
            return;
        }
        if (customerToModify != null) { // Customer already exists
            customerToModify.setCustomerName(nameField.getText());
            customerToModify.setAddress(addressField.getText());
            customerToModify.setPostalCode(postalField.getText());
            customerToModify.setPhone(phoneField.getText());
            customerToModify.setDivision(divisionBox.getValue());
            CustomerDAO.update(customerToModify);
        } else { // We are creating a new customer
            CustomerDAO.insert(new Customer(
                    null,
                    nameField.getText(),
                    addressField.getText(),
                    postalField.getText(),
                    phoneField.getText(),
                    divisionBox.getValue()
            ));
        }
        customerToModify = null;
        ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.customersAppointmentsScene);
    }

    /**
     * @param url The URL used to initialize the controller.
     * @param resourceBundle The ResourceBundle used to initialize the controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (customerToModify != null) {
            titleLabel.setText("Modify Customer");
            idField.setText(((Integer)customerToModify.getId()).toString());
            nameField.setText(customerToModify.getCustomerName());
            addressField.setText(customerToModify.getAddress());
            countryBox.setValue(customerToModify.getDivision().getCountry());
            divisionBox.setValue(customerToModify.getDivision());
            postalField.setText(customerToModify.getPostalCode());
            phoneField.setText(customerToModify.getPhone());
            populateDivisions();
        } else {
            idField.setText("(Automatically Generated)");
            titleLabel.setText("Add Customer");
            divisionBox.getItems().addAll(FirstLevelDivisionDAO.getAllDivisions());
        }
        countryBox.getItems().addAll(CountryDAO.getAllCountries());
    }

    public void populateDivisions() { // Called when countryBox has an action
        divisionBox.getItems().removeAll(divisionBox.getItems());
        divisionBox.getItems().addAll(ConverterUtility.getAllDivisionsOfCountry(countryBox.getValue()));
    }

    public void cancelButtonPressed(ActionEvent actionEvent) {
        if (ScreenUtility.showConfirmation("Are you sure you want to cancel?\n" +
                "All new information entered in the form will be lost.")) {
            customerToModify = null;
            ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.customersAppointmentsScene);
        }
    }
}

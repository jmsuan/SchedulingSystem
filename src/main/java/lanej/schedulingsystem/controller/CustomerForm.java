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

import java.io.IOException;
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

    public void submitButtonPressed(ActionEvent actionEvent) throws IOException {
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
        ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.customersAppointmentsScene);
        customerToModify = null;
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
        } else {
            idField.setText("(Automatically Generated)");
            titleLabel.setText("Add Customer");
        }
        countryBox.getItems().addAll(CountryDAO.getAllCountries());
        divisionBox.getItems().addAll(FirstLevelDivisionDAO.getAllDivisions());
    }

    public void populateDivisions() {
        divisionBox.getItems().removeAll(divisionBox.getItems());
        divisionBox.getItems().addAll(ConverterUtility.getAllDivisionsOfCountry(countryBox.getValue()));
    }

    public void cancelButtonPressed(ActionEvent actionEvent) throws IOException {
        if (ScreenUtility.showConfirmation("Are you sure you want to cancel?\n" +
                "All new information entered in the form will be lost.")) {
            ScreenUtility.changeStageScene(actionEvent, SchedulingApplication.customersAppointmentsScene);
            customerToModify = null;
        }
    }
}

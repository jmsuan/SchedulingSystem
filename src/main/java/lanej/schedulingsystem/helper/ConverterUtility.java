package lanej.schedulingsystem.helper;

import lanej.schedulingsystem.dao.AppointmentDAO;
import lanej.schedulingsystem.dao.FirstLevelDivisionDAO;
import lanej.schedulingsystem.model.Appointment;
import lanej.schedulingsystem.model.Country;
import lanej.schedulingsystem.model.Customer;
import lanej.schedulingsystem.model.FirstLevelDivision;

import java.util.ArrayList;
import java.util.List;

public abstract class ConverterUtility {
    public static List<FirstLevelDivision> getAllDivisionsOfCountry(Country country) {
        List<FirstLevelDivision> countryDivisions = new ArrayList<>();
        List<FirstLevelDivision> allDivisions = FirstLevelDivisionDAO.getAllDivisions();
        for (FirstLevelDivision division : allDivisions) {
            if (division.getCountry().equals(country)) {
                countryDivisions.add(division);
            }
        }
        return countryDivisions;
    }

    public static List<Appointment> getAllAppointmentsOfCustomer(Customer customer) {
        List<Appointment> appointmentsWithCustomer = new ArrayList<>();
        List<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        for (Appointment appointment : allAppointments) {
            if (appointment.getCustomer().equals(customer)) {
                appointmentsWithCustomer.add(appointment);
            }
        }
        return appointmentsWithCustomer;
    }
}

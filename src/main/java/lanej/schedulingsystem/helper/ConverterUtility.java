package lanej.schedulingsystem.helper;

import lanej.schedulingsystem.dao.AppointmentDAO;
import lanej.schedulingsystem.dao.FirstLevelDivisionDAO;
import lanej.schedulingsystem.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Provides utility methods to fetch and filter specific sets of data
 * from DAOs or from models like Appointments, FirstLevelDivisions, etc.
 */
public abstract class ConverterUtility {
    /**
     * Fetches all first-level divisions of a specific country.
     *
     * @param country The country whose divisions are to be fetched.
     * @return List of FirstLevelDivision that belong to the given country.
     */
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

    /**
     * Gets all appointments associated with a specific customer.
     *
     * @param customer The customer whose appointments are to be retrieved.
     * @return List of Appointments associated with the given customer.
     */
    public static List<Appointment> getAllAppointmentsOfCustomer(Customer customer) {
        List<Appointment> appointmentsWithCustomer = new ArrayList<>();
        List<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        for (Appointment appointment : allAppointments) {
            if (appointment.getCustomer().getCustomerId() == customer.getCustomerId()) {
                appointmentsWithCustomer.add(appointment);
            }
        }
        return appointmentsWithCustomer;
    }

    /**
     * Fetches all appointments associated with a specific user.
     *
     * @param user The user whose appointments are to be fetched.
     * @return List of Appointments associated with the given user.
     */
    public static List<Appointment> getAllAppointmentsOfUser(User user) {
        List<Appointment> appointmentsWithUser = new ArrayList<>();
        List<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        for (Appointment appointment : allAppointments) {
            if (Objects.equals(appointment.getUser().userId(), user.userId())) {
                appointmentsWithUser.add(appointment);
            }
        }
        return appointmentsWithUser;
    }

    /**
     * Fetches all unique types of appointments.
     *
     * @return List of unique String appointment types.
     */
    public static List<String> getAllAppointmentTypes() {
        List<String> appointmentTypes = new ArrayList<>();
        List<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        for (Appointment appointment : allAppointments) {
            if (!appointmentTypes.contains(appointment.getType())) {
                appointmentTypes.add(appointment.getType());
            }
        }
        return appointmentTypes;
    }
}

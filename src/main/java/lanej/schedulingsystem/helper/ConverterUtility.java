package lanej.schedulingsystem.helper;

import lanej.schedulingsystem.dao.AppointmentDAO;
import lanej.schedulingsystem.dao.FirstLevelDivisionDAO;
import lanej.schedulingsystem.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            if (appointment.getCustomer().getCustomerId() == customer.getCustomerId()) {
                appointmentsWithCustomer.add(appointment);
            }
        }
        return appointmentsWithCustomer;
    }

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
}

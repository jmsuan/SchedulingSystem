package lanej.schedulingsystem.helper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Provides utility methods for time-based operations and checks.
 * This class handles various time manipulations, calculations, and verifications.
 */
public abstract class TimeUtility {
    /**
     * Creates a LocalTime instance from given hour, minute, and time period (AM/PM).
     *
     * @param hour        the hour, from 1 to 12.
     * @param minute      the minute, from 0 to 59.
     * @param timePeriod  "AM" or "PM".
     * @return LocalTime instance corresponding to the input parameters.
     */
    public static LocalTime createLocalTime(int hour, int minute, String timePeriod) {
        assert (1 <= hour && hour <= 12);
        assert (0 <= minute && minute <= 59);
        assert (timePeriod.equals("AM") || timePeriod.equals("PM"));
        if (timePeriod.equals("AM")) {
            if (hour == 12) {
                hour = 0;
            }
        } else {
            if (hour != 12) {
                hour += 12;
            }
        }
        return LocalTime.of(hour, minute);
    }

    /**
     * Returns the 12-hour format of the given LocalTime.
     *
     * @param time the LocalTime instance.
     * @return hour value in 12-hour format.
     */
    public static int shortHour(LocalTime time) {
        int hour = time.getHour();
        if (hour == 0) {
            return 12;
        } else if (hour > 12) {
            return hour - 12;
        } else {
            return hour;
        }
    }

    /**
     * Returns "AM" or "PM" based on the given LocalTime.
     *
     * @param time the LocalTime instance.
     * @return "AM" or "PM" string.
     */
    public static String amOrPm(LocalTime time) {
        return (time.getHour() >= 12) ? "PM" : "AM";
    }

    /**
     * Detects if two time intervals overlap.
     *
     * @param dateTimeOneStart start time of first interval.
     * @param dateTimeOneEnd   end time of first interval.
     * @param dateTimeTwoStart start time of second interval.
     * @param dateTimeTwoEnd   end time of second interval.
     * @return true if the intervals overlap, false otherwise.
     */
    public static boolean detectOverlap(
            LocalDateTime dateTimeOneStart,
            LocalDateTime dateTimeOneEnd,
            LocalDateTime dateTimeTwoStart,
            LocalDateTime dateTimeTwoEnd) {
        return dateTimeOneStart.isBefore(dateTimeTwoEnd) && dateTimeOneEnd.isAfter(dateTimeTwoStart);
    }

    /**
     * Checks if the given time interval falls within regular working hours.
     * The working hours are between 8:00 AM to 10:00 PM in Eastern Time.
     *
     * @param start start time of the interval.
     * @param end   end time of the interval.
     * @return true if the interval is within working hours, false otherwise.
     */
    public static boolean detectIfWithinWorkHours(LocalDateTime start, LocalDateTime end) {
        ZoneId localZone = ZoneId.systemDefault();
        ZoneId etZone = ZoneId.of("America/New_York");

        ZonedDateTime startInEt = start.atZone(localZone).withZoneSameInstant(etZone);
        ZonedDateTime endInEt = end.atZone(localZone).withZoneSameInstant(etZone);

        return  startInEt.isAfter(startInEt.withHour(7).withMinute(59)) &&
                startInEt.isBefore(startInEt.withHour(22).withMinute(1)) &&
                endInEt.isAfter(startInEt.withHour(7).withMinute(59)) &&
                endInEt.isBefore(startInEt.withHour(22).withMinute(1)) &&
                startInEt.isBefore(endInEt);
    }
}

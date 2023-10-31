package lanej.schedulingsystem.helper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class TimeUtility {
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

    public static String amOrPm(LocalTime time) {
        return (time.getHour() >= 12) ? "PM" : "AM";
    }

    public static boolean detectOverlap(
            LocalDateTime dateTimeOneStart,
            LocalDateTime dateTimeOneEnd,
            LocalDateTime dateTimeTwoStart,
            LocalDateTime dateTimeTwoEnd) {
        return dateTimeOneStart.isBefore(dateTimeTwoEnd) && dateTimeOneEnd.isAfter(dateTimeTwoStart);
    }

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

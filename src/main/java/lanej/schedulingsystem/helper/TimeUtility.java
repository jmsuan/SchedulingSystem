package lanej.schedulingsystem.helper;

import java.time.LocalTime;

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
}

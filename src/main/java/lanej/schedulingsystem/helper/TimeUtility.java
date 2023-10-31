package lanej.schedulingsystem.helper;

import java.time.LocalTime;

public abstract class TimeUtility {
    public static LocalTime createLocalTime(int hour, int minute, String timePeriod) {
        return LocalTime.now();
    }
}

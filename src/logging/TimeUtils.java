package logging;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static String getFormattedDate() {
        LocalDate date = LocalDate.now();
        return dateFormatter.format(date);
    }

    public static String getFormattedTime() {
        LocalTime time = LocalTime.now();
        return timeFormatter.format(time);
    }
}

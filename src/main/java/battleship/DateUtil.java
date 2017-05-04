package battleship;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Created by Auger on 02/05/2017.
 * Defining global Date Methods
 */
public class DateUtil {

    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public static String date;

    public static String getDateNow() {
        LocalDateTime now = LocalDateTime.now();
        date = dtf.format(now);
        return date;
    }

    public static String getDateNowPlusHours(int h) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime date2 = now.plusHours(h);
        date = dtf.format(date2);
        return date;
    }

}

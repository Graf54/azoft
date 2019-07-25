package my.test.azoft.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static Date getDate(String date, String time) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date + " " + time);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static Date getDate(String datetimeLocal) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(datetimeLocal);
        } catch (ParseException e) {
            return new Date();
        }
    }
}

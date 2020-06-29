package by.itra.pikachy.api.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Component
public class GetDate {

    private static final String LOCAL_TIME_ZONE = "Europe/Minsk";
    private static final String DATE_FORMAT = "HH:mm dd.MM.yyyy";


    public static SimpleDateFormat getLocalDate() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        format.setTimeZone(TimeZone.getTimeZone(DATE_FORMAT));
        return format;
    }
}
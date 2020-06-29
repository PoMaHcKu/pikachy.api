package by.itra.pikachy.api.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class GetDate {

    private static final String LOCAL_TIME_ZONE = "Europe/Minsk";

    public static LocalDateTime getLocalDate() {
        return LocalDateTime.now(ZoneId.of(LOCAL_TIME_ZONE));
    }
}
package boosterm.backend.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Converter {

    public static LocalDateTime toLocalDateTime(Date date, String timeZone) {
        return date.toInstant().atZone(ZoneId.of(timeZone)).toLocalDateTime();
    }

    public static String toString(LocalDateTime date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }

}

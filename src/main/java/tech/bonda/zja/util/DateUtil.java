package tech.bonda.zja.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class DateUtil {

    public static LocalDateTime transform(String dateTimeWithZone) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd[' 'HH:mm:ss][ z]")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();

        TemporalAccessor temporalAccessor = formatter.parseBest(dateTimeWithZone, ZonedDateTime::from, LocalDate::from);

        if (temporalAccessor instanceof ZonedDateTime) {
            return ((ZonedDateTime) temporalAccessor).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        } else {
            return ((LocalDate) temporalAccessor).atStartOfDay();
        }
    }

    public static boolean isAfter(LocalDateTime usageStartTime, String startTime) {
        var time = DateUtil.transform(startTime);
        return usageStartTime.isAfter(time) || usageStartTime.isEqual(time);
    }

    public static boolean isBefore(LocalDateTime usageStartTime, String endTime) {
        var time = DateUtil.transform(endTime);
        return usageStartTime.isBefore(time) || usageStartTime.isEqual(time);
    }
}
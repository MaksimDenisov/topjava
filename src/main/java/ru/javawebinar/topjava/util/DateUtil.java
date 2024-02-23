package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(formatter);
    }
}

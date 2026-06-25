package com.rohr.portfolio_api.v1.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateUtils {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatDate(LocalDateTime value) {
        if (value == null) {
            return null;
        }
        return value.format(dateTimeFormatter);
    }

    public static String formatDate(LocalDate value) {
        if (value == null) {
            return null;
        }
        return value.format(dateFormatter);
    }
}

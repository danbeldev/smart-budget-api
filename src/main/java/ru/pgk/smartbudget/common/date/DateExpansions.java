package ru.pgk.smartbudget.common.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateExpansions {

    public static String toDefaultDateFormat(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);
    }
}

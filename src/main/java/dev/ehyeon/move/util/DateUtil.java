package dev.ehyeon.move.util;

import java.time.LocalDate;

public class DateUtil {

    public static LocalDate yearToLocalDateTime(int year) {
        return LocalDate.of(year, 1, 1);
    }
}

package com.veljkobogdan.quizzardapp.data.database.converter;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @TypeConverter
    public static String fromLocalDate(LocalDate date) {
        return date == null ? null : date.format(formatter);
    }

    @TypeConverter
    public static LocalDate toLocalDate(String date) {
        return date == null ? null : LocalDate.from(formatter.parse(date));
    }
}

package com.veljkobogdan.quizzardapp.data.database.converter;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @TypeConverter
    public static String fromLocalDateTime(LocalDateTime date) {
        return date == null ? null : date.format(formatter);
    }

    @TypeConverter
    public static LocalDateTime toLocalDateTime(String date) {
        return date == null ? null : LocalDateTime.from(formatter.parse(date));
    }
}

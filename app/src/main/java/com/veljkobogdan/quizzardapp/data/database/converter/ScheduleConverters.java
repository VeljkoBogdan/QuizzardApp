package com.veljkobogdan.quizzardapp.data.database.converter;

import androidx.room.TypeConverter;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class ScheduleConverters {

    @TypeConverter
    public static DayOfWeek fromInt(int day) {
        return DayOfWeek.of(day);
    }

    @TypeConverter
    public static int dayOfWeekToInt(DayOfWeek day) {
        return day.getValue(); // 1 = Monday, ..., 7 = Sunday
    }

    @TypeConverter
    public static LocalTime fromTime(String time) {
        return time == null ? null : LocalTime.parse(time);
    }

    @TypeConverter
    public static String localTimeToString(LocalTime time) {
        return time == null ? null : time.toString();
    }
}


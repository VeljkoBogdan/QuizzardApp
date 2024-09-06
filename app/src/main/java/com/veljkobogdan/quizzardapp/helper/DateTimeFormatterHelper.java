package com.veljkobogdan.quizzardapp.helper;

import android.annotation.SuppressLint;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeFormatterHelper {

    @SuppressLint("NewApi")
    public static DateTimeFormatter getCalendarFormat() {
        return DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
    }
}

package com.veljkobogdan.quizzardapp.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.veljkobogdan.quizzardapp.database.RoomDB;
import com.veljkobogdan.quizzardapp.entity.CalendarInsert;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class CalendarInsertRepository {

    @SuppressLint("NewApi")
    public static List<CalendarInsert> getCalendarInsertsForDay(LocalDate date, Context context) {
        try {
            return RoomDB.getInstance(context).calendarDAO().getCalendarInsertsForDay(date.toString());
        } catch (Exception e) {
            Log.e("RUNTIME_ERROR", Objects.requireNonNull(e.getMessage()));
            return null;
        }
    }
}

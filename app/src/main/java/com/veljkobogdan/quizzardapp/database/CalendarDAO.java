package com.veljkobogdan.quizzardapp.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.veljkobogdan.quizzardapp.entity.CalendarInsert;

import java.util.List;

@Dao
public interface CalendarDAO {

    @Insert(onConflict = REPLACE)
    void insert(CalendarInsert calendarInsert);

    @Query("SELECT * FROM calendar_insert")
    List<CalendarInsert> getAll();

    @Query("UPDATE calendar_insert SET title = :title, description = :description, date = :date, time = :time WHERE id = :id")
    void update(int id, String title, String description, String date, String time);

    @Delete
    void delete(CalendarInsert calendarInsert);

    @Query("SELECT * FROM calendar_insert WHERE date = :date")
    List<CalendarInsert> getCalendarInsertsForDay(String date);
}

package com.veljkobogdan.quizzardapp.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.veljkobogdan.quizzardapp.data.database.entities.CalendarEntry;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface CalendarEntryDao {
    @Query("SELECT * FROM calendarentry")
    LiveData<List<CalendarEntry>> getCalendarEntries();

    @Insert
    void insertAll(CalendarEntry... calendarEntries);

    @Insert
    void insert(CalendarEntry calendarEntry);

    @Update
    void update(CalendarEntry calendarEntry);

    @Delete
    void delete(CalendarEntry calendarEntry);

    @Query("SELECT * FROM calendarentry WHERE localDate = :day")
    LiveData<List<CalendarEntry>> getEntriesForDay(LocalDate day);
}

package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class CalendarEntry {
    @PrimaryKey(autoGenerate = true)
    public long calendarId;

    @ColumnInfo
    // Required
    public LocalDate localDate;
    @ColumnInfo
    // Optional
    public LocalDateTime localDateTime;

    public CalendarEntry() {}

    @Ignore
    public CalendarEntry(LocalDate localDate, LocalDateTime localDateTime) {
        this.localDate = localDate;
        this.localDateTime = localDateTime;
    }
}

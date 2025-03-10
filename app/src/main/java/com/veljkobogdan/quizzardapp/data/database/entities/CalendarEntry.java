package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.annotation.Nullable;
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
    public String title;
    @ColumnInfo
    // Required
    public LocalDate localDate;
    @ColumnInfo
    @Nullable
    // Optional
    public LocalDateTime localDateTime;

    public CalendarEntry() {}

    @Ignore
    public CalendarEntry(String title, LocalDate localDate, @Nullable LocalDateTime localDateTime) {
        this.title = title;
        this.localDate = localDate;
        this.localDateTime = localDateTime;
    }
}

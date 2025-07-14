package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity(
        tableName = "schedule",
        foreignKeys = @ForeignKey(
                entity = Subject.class,
                parentColumns = "subjectId",
                childColumns = "subjectId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Schedule {
    @PrimaryKey(autoGenerate = true)
    public long scheduleId;

    public long subjectId;

    @NonNull
    public DayOfWeek dayOfWeek;

    @NonNull
    public LocalTime startTime;

    @NonNull
    public LocalTime endTime;
}


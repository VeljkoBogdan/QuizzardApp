package com.veljkobogdan.quizzardapp.data.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity(tableName = "schedule")
public class Schedule {
    @PrimaryKey(autoGenerate = true)
    public long scheduleId;

    public String name;
}

